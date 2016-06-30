package com.aigestudio.wheelpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
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
public class WheelPicker extends View implements IDebug, IWheelPicker, Runnable {
    private static final String TAG = WheelPicker.class.getSimpleName();

    private final Handler mHandler = new Handler();
    private Paint mPaint;
    private Scroller mScroller;
    private VelocityTracker mTracker;
    private OnItemSelectListener mOnItemSelectListener;
    private OnWheelChangeListener mOnWheelChangeListener;

    /**
     *
     */
    private List mData;// 数据源

    /**
     * 滚轮选择器中可见的Item数量和滚轮选择器将会绘制的Item数量
     * 滚轮选择器将会绘制的Item数量取决于可见Item数量，绘制的Item数量总会比可见Item数量多两个，你不必也不需要为滚
     * 轮选择器指定将会绘制的Item数量，你只需通过{@link #setVisibleItemCount(int)}为滚轮选择器指定可见的Item
     * 数量，滚轮选择器会根据可见Item数量自动计算绘制的Item数量。
     * 滚轮选择器的可见Item数量应始终为奇数，如果你为可见Item数量设置一个偶数值，滚轮选择器会将其自动加一转换为奇
     * 数，默认为情况下滚轮选择器的可见Item数量为5，绘制Item数量为7。
     *
     * @see #setVisibleItemCount(int)
     */
    private int mVisibleItemCount, mDrawnItemCount;

    /**
     * 滚轮选择器将会绘制的Item数量的一半
     * 该值主要用于偏移值的计算，该值由滚轮选择器计算赋值
     */
    private int mHalfDrawnItemCount;

    private int mItemTextSize;// Item文本大小
    private int mTextMaxWidth, mTextMaxHeight;

    /**
     * 滚轮选择器单个Item宽高
     */
    private int mItemWidth, mItemHeight;
    private int mHalfItemWidth, mHalfItemHeight;


    private int mCurrentItemPosition;

    private int mMinFlingY, mMaxFlingY;
    private int mMinimumVelocity = 50, mMaximumVelocity = 8000;

    /**
     * 滚轮选择器中心坐标
     * 滚轮选择器的中心坐标为滚轮正中心坐标，其X坐标取值为滚轮宽度的一半，而Y坐标取值为滚轮高度的一半
     */
    private int mWheelCenterX, mWheelCenterY;

    /**
     * 滚轮选择器绘制中心坐标
     */
    private int mDrawnCenterX, mDrawnCenterY;

    /**
     *
     */
    private int mScrollOffsetX, mScrollOffsetY;
    private int mLastPointY;

    private boolean isCyclic;
    private boolean isDebug;

    public WheelPicker(Context context) {
        this(context, null);
    }

    public WheelPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 从资源文件获取属性参数
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WheelPicker);
        int idData = a.getResourceId(R.styleable.WheelPicker_wheel_data, 0);
        mData = Arrays.asList(getResources()
                .getStringArray(idData == 0 ? R.array.WheelArrayDefault : idData));
        mItemTextSize = a.getDimensionPixelSize(R.styleable.WheelPicker_wheel_item_text_size,
                getResources().getDimensionPixelSize(R.dimen.WheelItemTextSize));
        mVisibleItemCount = a.getInt(R.styleable.WheelPicker_wheel_visible_item_count, 7);
        mCurrentItemPosition = a.getInt(R.styleable.WheelPicker_wheel_current_item_position, 0);
        a.recycle();

        // 可见Item改变后更新与之相关的参数
        updateVisibleItemCount();

        // 初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mPaint.setColor(0xFFFFFFFF);
        mPaint.setTextSize(mItemTextSize);
        mPaint.setTextAlign(Paint.Align.CENTER);

        // 计算文本最大宽高
        for (Object obj : mData) {
            String text = String.valueOf(obj);

            int width = (int) mPaint.measureText(text);
            mTextMaxWidth = Math.max(mTextMaxWidth, width);

            Paint.FontMetrics metrics = mPaint.getFontMetrics();
            int height = (int) (metrics.bottom - metrics.top);
            mTextMaxHeight = Math.max(mTextMaxHeight, height);
        }
        if (isDebug)
            Log.i(TAG, "Text's max size is: (" + mTextMaxWidth + "," + mTextMaxHeight + ")");

        // 初始化滚动器
        mScroller = new Scroller(getContext());

        // 初始化配置参数
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            ViewConfiguration conf = ViewConfiguration.get(getContext());
            mMinimumVelocity = conf.getScaledMinimumFlingVelocity();
            mMaximumVelocity = conf.getScaledMaximumFlingVelocity();
        }
    }

    private void updateVisibleItemCount() {
        // 确保滚轮选择器可见Item数量为奇数
        if (mVisibleItemCount % 2 == 0)
            mVisibleItemCount += 1;
        mDrawnItemCount = mVisibleItemCount + 2;
        mHalfDrawnItemCount = mDrawnItemCount / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int resultWidth = mTextMaxWidth;
        int resultHeight = mTextMaxHeight * mVisibleItemCount;

        resultWidth = measureSize(modeWidth, sizeWidth, resultWidth);
        resultHeight = measureSize(modeHeight, sizeHeight, resultHeight);

        setMeasuredDimension(resultWidth, resultHeight);
    }

    private int measureSize(int mode, int sizeExpect, int sizeActual) {
        int realSize;
        if (mode == MeasureSpec.EXACTLY) {
            realSize = sizeExpect;
        } else {
            realSize = sizeActual;
            if (mode == MeasureSpec.AT_MOST)
                realSize = Math.min(realSize, sizeExpect);
        }
        return realSize;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        mWheelCenterX = getWidth() / 2;
        mWheelCenterY = getHeight() / 2;

        mDrawnCenterX = mWheelCenterX;
        mDrawnCenterY = (int) (mWheelCenterY - ((mPaint.ascent() + mPaint.descent()) / 2));

        mItemHeight = getHeight() / mVisibleItemCount;
        mHalfItemHeight = mItemHeight / 2;

        // 初始化滑动最大坐标
        int currentItemOffset = mCurrentItemPosition * mItemHeight;
        mMinFlingY = isCyclic ? Integer.MIN_VALUE :
                -mItemHeight * (mData.size() - 1) + currentItemOffset;
        mMaxFlingY = isCyclic ? Integer.MAX_VALUE : currentItemOffset;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int drawnDataStartPos = -mScrollOffsetY / mItemHeight - mHalfDrawnItemCount;
        for (int drawnDataPos = drawnDataStartPos + mCurrentItemPosition,
             drawnOffsetPos = -mHalfDrawnItemCount;
             drawnDataPos < drawnDataStartPos + mCurrentItemPosition + mDrawnItemCount;
             drawnDataPos++, drawnOffsetPos++) {
            String data = "";
            if (isCyclic) {
                int actualPos = drawnDataPos % mData.size();
                actualPos = actualPos < 0 ? (actualPos + mData.size()) : actualPos;
                data = String.valueOf(mData.get(actualPos));
            } else {
                if (isPosInRang(drawnDataPos))
                    data = String.valueOf(mData.get(drawnDataPos));
            }
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(0xFFFFFFFF);
            int mDrawnItemCenterY = mDrawnCenterY + (drawnOffsetPos * mItemHeight);
            canvas.drawText(data, mDrawnCenterX,
                    mDrawnItemCenterY + mScrollOffsetY % mItemHeight, mPaint);

            if (isDebug) {
                mPaint.setColor(0xFFEE3333);
                int centerY = mWheelCenterY + (drawnOffsetPos * mItemHeight);
                canvas.drawLine(0, centerY, getWidth(), centerY, mPaint);
                mPaint.setColor(0xFF3333EE);
                mPaint.setStyle(Paint.Style.STROKE);
                int top = centerY - mHalfItemHeight;
                canvas.drawRect(0, top, getWidth(), top + mItemHeight, mPaint);
            }
        }
    }

    private boolean isPosInRang(int position) {
        return position >= 0 && position < mData.size();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (null == mTracker)
                    mTracker = VelocityTracker.obtain();
                else
                    mTracker.clear();
                mTracker.addMovement(event);
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();
                mLastPointY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mTracker.addMovement(event);

                // 滚动内容
                mScrollOffsetY += ((event.getY() - mLastPointY));
                mLastPointY = (int) event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mTracker.addMovement(event);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT)
                    mTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                else
                    mTracker.computeCurrentVelocity(1000);

                // 根据速度判断是该滚动还是滑动
                int velocity = (int) mTracker.getYVelocity();
                if (Math.abs(velocity) > mMinimumVelocity) {
                    mScroller.fling(0, mScrollOffsetY, 0, velocity, 0, 0, mMinFlingY, mMaxFlingY);

                    // 校正坐标
                    mScroller.setFinalY(mScroller.getFinalY() +
                            computeDistanceToEndPoint(mScroller.getFinalY() % mItemHeight));
                    if (!isCyclic) {
                        if (mScroller.getFinalY() > mMaxFlingY)
                            mScroller.setFinalY(mMaxFlingY);
                        else if (mScroller.getFinalY() < mMinFlingY)
                            mScroller.setFinalY(mMinFlingY);
                    }
                } else {
                    mScroller.startScroll(0, mScrollOffsetY, 0,
                            computeDistanceToEndPoint(mScrollOffsetY % mItemHeight));
                }
                mHandler.post(this);
                if (null != mTracker) {
                    mTracker.recycle();
                    mTracker = null;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    private int computeDistanceToEndPoint(int remainder) {
        if (Math.abs(remainder) > mHalfItemHeight)
            if (mScrollOffsetY < 0)
                return -mItemHeight - remainder;
            else
                return mItemHeight - remainder;
        else
            return -remainder;
    }

    @Override
    public void run() {
        if (mScroller.isFinished()) {
            int position = (-mScrollOffsetY / mItemHeight) % mData.size() + mCurrentItemPosition;
            position = position < 0 ? position + mData.size() : position;
            if (isDebug)
                Log.i("WheelPicker", position + ":" + mData.get(position) + ":" + mScrollOffsetY);
            if (null != mOnItemSelectListener)
                mOnItemSelectListener.onItemSelected(this, mData.get(position), position);
        }
        if (mScroller.computeScrollOffset()) {
            mScrollOffsetY = mScroller.getCurrY();
            postInvalidate();
            mHandler.postDelayed(this, 16);
        }
    }

    @Override
    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    @Override
    public void setVisibleItemCount(int count) {
        mVisibleItemCount = count;
        updateVisibleItemCount();
        requestLayout();
    }

    @Override
    public int getVisibleItemCount() {
        return mVisibleItemCount;
    }

    @Override
    public void setCyclic(boolean isCyclic) {
        this.isCyclic = isCyclic;
        requestLayout();
        invalidate();
    }

    @Override
    public boolean isCyclic() {
        return isCyclic;
    }

    @Override
    public void setOnItemSelectListener(OnItemSelectListener listener) {
        mOnItemSelectListener = listener;
    }

    @Override
    public void setCurrentItem(int position) {
        if (!isPosInRang(position))
            throw new ArrayIndexOutOfBoundsException
                    ("Position must in [0, " + mData.size() + "), but current is " + position);
        mCurrentItemPosition = position;
        requestLayout();
        invalidate();
    }

    @Override
    public int getCurrentItem() {
        return mCurrentItemPosition;
    }
}