package com.aigestudio.wheelpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import java.util.Arrays;
import java.util.List;

/**
 * 抽象滚轮选择器
 *
 * @author AigeStudio 2015-12-12
 * @author AigeStudio 2016-06-17
 *         更新项目结构
 * @version 1.1.0 beta
 */
public abstract class WheelPicker extends View implements IWheelPicker, Runnable {
    // 滚轮选择器滚动状态标识值
    public static final int
            SCROLL_STATE_IDLE = 0,// 滚动停止时
            SCROLL_STATE_DRAGGING = 1,// 拖拽时
            SCROLL_STATE_SCROLLING = 2;// 滚动时

    protected final Handler mHandler = new Handler();

    protected OnItemSelectListener mItemSelectListener;
    protected OnWheelChangeListener mWheelChangeListener;

    protected Paint mPaint;
    protected Scroller mScroller;
    protected VelocityTracker mTracker;
    protected Typeface mItemTextTypeface;

    protected List mData;// 数据列表

    protected int mWheelActualWidth, mWheelActualHeight;
    protected int mCurrentItemPosition;
    protected int mItemTextSize;
    protected int mItemTextColor, mCurrentItemTextColor;
    protected int mTextMaxWidth, mTextMaxHeight;
    protected int mMinimumVelocity, mMaximumVelocity;
    protected int mTouchSlop;

    protected boolean hasSameSize;
    protected int mMoveSingleX, mMoveSingleY;
    protected int mMoveTotalX, mMoveTotalY;

    private int mLastX, mLastY;

    private boolean isMoving;
    private boolean isScroll;

    public WheelPicker(Context context) {
        this(context, null);
    }

    public WheelPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    protected void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.WheelPicker);
        int idData = a.getResourceId(R.styleable.WheelPicker_wheel_data, 0);
        if (idData == 0)
            mData = Arrays.asList(getResources().getStringArray(R.array.WheelArrayDefault));
        else
            mData = Arrays.asList(getResources().getStringArray(idData));
        mCurrentItemPosition = a.getInt(R.styleable.WheelPicker_wheel_current_item_position, 0);
        mItemTextSize = a.getDimensionPixelSize(
                R.styleable.WheelPicker_wheel_item_text_size,
                getResources().getDimensionPixelSize(R.dimen.WheelItemTextSize));
        mItemTextColor = a.getColor(R.styleable.WheelPicker_wheel_item_text_color, 0xFF888888);
        mCurrentItemTextColor = a.getColor
                (R.styleable.WheelPicker_wheel_current_item_position, 0xFF333333);
        hasSameSize = a.getBoolean(R.styleable.WheelPicker_wheel_same_size, false);
        a.recycle();

        ViewConfiguration conf = ViewConfiguration.get(getContext());
        mTouchSlop = conf.getScaledTouchSlop();
        mMinimumVelocity = conf.getScaledMinimumFlingVelocity();
        mMaximumVelocity = conf.getScaledMaximumFlingVelocity();

        mPaint = new Paint();
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(mItemTextColor);
        mPaint.setTextSize(mItemTextSize);

        mScroller = new Scroller(getContext());

        for (Object obj : mData) {
            String text = String.valueOf(obj);

            int width = (int) mPaint.measureText(text);
            mTextMaxWidth = Math.max(mTextMaxWidth, width);

            Paint.FontMetrics metrics = mPaint.getFontMetrics();
            int height = (int) (metrics.bottom - metrics.top);
            mTextMaxHeight = Math.max(mTextMaxHeight, height);
        }
    }

    @Override
    protected abstract void onMeasure(int widthMeasureSpec, int heightMeasureSpec);

    @Override
    protected abstract void onDraw(Canvas canvas);

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                if (null == mTracker)
                    mTracker = VelocityTracker.obtain();
                else
                    mTracker.clear();
                mTracker.addMovement(event);
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                onTouchDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isMoving && Math.abs(event.getY() - mLastY) < mTouchSlop) {
                    isScroll = false;
                    break;
                }
                isMoving = true;
                isScroll = true;
                mMoveSingleX += (event.getX() - mLastX);
                mMoveSingleY += (event.getY() - mLastY);
                mTracker.addMovement(event);
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                onTouchMove(event);
                break;
            case MotionEvent.ACTION_UP:
                isMoving = false;
                if (isScroll) {
                    mTracker.addMovement(event);
                    mMoveTotalX += mMoveSingleX;
                    mMoveTotalY += mMoveSingleY;
                    mMoveSingleX = 0;
                    mMoveSingleY = 0;
                    mTracker.computeCurrentVelocity(750, mMaximumVelocity);
                    onTouchUp(event);
                }
                mTracker.recycle();
                mTracker = null;
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_CANCEL:
                isMoving = false;
                getParent().requestDisallowInterceptTouchEvent(false);
                mScroller.abortAnimation();
                mTracker.recycle();
                mTracker = null;
                break;
        }
        return true;
    }

    protected abstract void onTouchDown(MotionEvent event);

    protected abstract void onTouchMove(MotionEvent event);

    protected abstract void onTouchUp(MotionEvent event);

    @Override
    public List getData() {
        return mData;
    }

    @Override
    public void setData(List data) {
        if (null == data)
            throw new RuntimeException("Wheel's data can not be null!");
        mData = data;
        requestLayout();
    }

    @Override
    public void setOnItemSelectListener(OnItemSelectListener listener) {

    }

    @Override
    public void setOnWheelChangeListener(OnItemSelectListener listener) {

    }

    @Override
    public int getCurrentItem() {
        return 0;
    }

    @Override
    public void setCurrentItem(int position) {

    }

    @Override
    public void setHasSameSize(boolean hasSameSize) {

    }

    @Override
    public boolean hasSameSize() {
        return false;
    }

    @Override
    public int getItemTextSize() {
        return 0;
    }

    @Override
    public void setItemTextSize(int dp) {

    }

    @Override
    public Typeface getItemTextTypeface() {
        return null;
    }

    @Override
    public void setItemTextTypeface(Typeface typeface) {

    }

    @Override
    public int getItemTextColor() {
        return 0;
    }

    @Override
    public void setItemTextColor(int color) {

    }

    @Override
    public int getCurrentItemTextColor() {
        return 0;
    }

    @Override
    public void setCurrentItemTextColor(int color) {

    }

    @Override
    public int getTextMaxWidth() {
        return mTextMaxWidth;
    }

    @Override
    public int getTextMaxHeight() {
        return mTextMaxHeight;
    }
}