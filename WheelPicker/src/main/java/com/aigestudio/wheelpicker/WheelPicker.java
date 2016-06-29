package com.aigestudio.wheelpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
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
public class WheelPicker extends View implements IDebug, IWheelPicker {
    private static final String TAG = WheelPicker.class.getSimpleName();

    private final Handler mHandler = new Handler();
    private Paint mPaint;
    private Scroller mScroller;
    private VelocityTracker mTracker;
    private OnItemSelectListener mOnItemSelectListener;
    private OnWheelChangeListener mOnWheelChangeListener;

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


    private int mCurrentItemPosition = 0;
    private int mTextDrawnOffset;// 文本绘制坐标偏移
    private int mItemPositionOffset;// Item位置偏移
    private int mMinimumVelocity = 50, mMaximumVelocity = 8000;
    private int mMinimumFlingDistance, mMaximumFlingDistance;
    private int mTouchSlop = 8;
    //    private int mCurrentScrollDirection = -1;
    private int mItemRockSplit;

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
    private int mLastY;

    private boolean isCyclic = true;
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

        mItemRockSplit = mItemHeight / 2;
        mTextDrawnOffset = (int) (mItemHeight / 2 - ((mPaint.ascent() + mPaint.descent()) / 2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int drawnDataStartPos = -mScrollOffsetY / mItemHeight - mHalfDrawnItemCount;
        for (int drawnDataPos = drawnDataStartPos, drawnOffsetPos = -mHalfDrawnItemCount;
             drawnDataPos < drawnDataStartPos + mDrawnItemCount; drawnDataPos++, drawnOffsetPos++) {
            int actualPos = drawnDataPos % mData.size();
            actualPos = actualPos < 0 ? (actualPos + mData.size()) : actualPos;
            String data = String.valueOf(mData.get(actualPos));
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(0xFFFFFFFF);
            int mDrawnItemCenterY = mDrawnCenterY + (drawnOffsetPos * mItemHeight);
            canvas.drawText(data, mDrawnCenterX, mDrawnItemCenterY + mScrollOffsetY % mItemHeight, mPaint);

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
//        Log.i("WheelPicker", mScrollOffsetY + ":");
//        int start = -mScrollOffsetY / mItemHeight;
//        for (int pos = start, index = isCyclic ? -1 : 0; pos < start + mDrawnItemCount; pos++, index++) {
//            float centerY = index * mItemHeight + mTextDrawnOffset + mScrollOffsetY % mItemHeight;
//            String data = "";
//            if (isPosIntraArea(pos)) {
//                data = String.valueOf(mData.get(pos));
//            } else {
//                if (isCyclic) {
//                    int newPos = pos % mData.size();
//                    newPos = newPos < 0 ? (newPos + mData.size()) : newPos;
//                    data = String.valueOf(mData.get(newPos));
//                }
//            }
//            if (!TextUtils.isEmpty(data)) canvas.drawText(data, mDrawnCenterX, centerY, mPaint);
//            mPaint.setStyle(Paint.Style.STROKE);
//            canvas.drawRect(0, mItemHeight * index, getWidth(), mItemHeight * (1 + index), mPaint);
//        }
    }

    private boolean isPosIntraArea(int position) {
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
                mLastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mTracker.addMovement(event);

//                // 判断滚动方向
//                if (event.getY() > mLastY)
//                    mCurrentScrollDirection = SCROLL_DIRECTION_DOWN;
//                else if (event.getY() < mLastY)
//                    mCurrentScrollDirection = SCROLL_DIRECTION_UP;

                // 滚动内容
                mScrollOffsetY += (event.getY() - mLastY);
                invalidate();
                mLastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
//                mTracker.addMovement(event);
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT)
//                    mTracker.computeCurrentVelocity(1000, mMaximumVelocity);
//                else
//                    mTracker.computeCurrentVelocity(1000);
//
//                // 根据速度判断是该滚动还是滑动
//                int velocity = (int) mTracker.getYVelocity();
//                if (Math.abs(velocity) >= mMinimumVelocity) {
//                    mScroller.fling(0, mScrollOffsetY, 0, (int) mTracker.getYVelocity(), 0, 0,
////                            mMinimumFlingDistance, mMaximumFlingDistance);
//                            Integer.MIN_VALUE, Integer.MAX_VALUE);
//                    if (isCyclic) {
//                        mScroller.setFinalY(mScroller.getFinalY() +
//                                computeDistanceToEndPoint(mScroller.getFinalY() % mItemHeight));
//                    } else {
//                        if (mScroller.getFinalY() > mMaximumFlingDistance)
//                            mScroller.setFinalY(mMaximumFlingDistance);
//                        else if (mScroller.getFinalY() < mMinimumFlingDistance)
//                            mScroller.setFinalY(mMinimumFlingDistance);
//                    }
//                } else {
//                    mScroller.startScroll(0, mScrollOffsetY, 0,
//                            computeDistanceToEndPoint(mScrollOffsetY % mItemHeight));
//                }
//                if (null != mTracker) {
//                    mTracker.recycle();
//                    mTracker = null;
//                }
//                mHandler.post(this);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
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

//    private int computeDistanceToEndPoint(int remainder) {
//        if (Math.abs(remainder) > mItemRockSplit)
//            if (mScrollOffsetY < 0)
//                return -mItemHeight - remainder;
//            else
//                return mItemHeight - remainder;
//        else
//            return -remainder;
//    }

//    @Override
//    public void run() {
//        if (mScroller.isFinished()) {
//            int result = (mScrollOffsetY / mItemHeight - mItemPositionOffset) % mData.size();
//            result = result < 0 ? result + mData.size() : result;
//            Log.i("WheelPicker", result + ":" + mData.get(result) + ":" + mScrollOffsetY);
//        }
//        if (mScroller.computeScrollOffset()) {
//            mScrollOffsetY = mScroller.getCurrY();
//            postInvalidate();
//            mHandler.postDelayed(this, 16);
//        }
//    }
}