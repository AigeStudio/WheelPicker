package com.aigestudio.wheelpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
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
public class WheelPicker extends View {
    // 滚轮选择器滚动状态标识值
    public static final int
            SCROLL_STATE_IDLE = 0,// 滚动停止时
            SCROLL_STATE_DRAGGING = 1,// 拖拽时
            SCROLL_STATE_SCROLLING = 2;// 滚动时
//    // 滚轮选择器当前滚动方向标识值
//    private static final int
//            SCROLL_DIRECTION_LEFT = 0,// 向左滚动时
//            SCROLL_DIRECTION_UP = 1,// 向上滚动时
//            SCROLL_DIRECTION_RIGHT = 2,// 向右滚动时
//            SCROLL_DIRECTION_DOWN = 3;// 向下滚动时

    private final Handler mHandler = new Handler();
    private Paint mPaint;
    private Scroller mScroller;
    private VelocityTracker mTracker;
    private OnItemSelectListener mOnItemSelectListener;
    private OnWheelChangeListener mOnWheelChangeListener;

    private List mData;// 数据源

    private int mVisibleItemCount = 7;// 滚轮选择器中可见的Item数量
    private int mDrawnItemCount;
    private int mItemTextSize;// Item文本大小
    private int mTextMaxWidth, mTextMaxHeight;
    private int mItemWidth, mItemHeight;
    private int mCurrentItemPosition = 0;
    private int mTextDrawnOffset;// 文本绘制坐标偏移
    private int mItemPositionOffset;// Item位置偏移
    private int mMinimumVelocity = 50, mMaximumVelocity = 8000;
    private int mMinimumFlingDistance, mMaximumFlingDistance;
    private int mTouchSlop = 8;
    //    private int mCurrentScrollDirection = -1;
    private int mItemRockSplit;

    private int mItemCenterX, mItemCenterY;// Item中心坐标
    private int mScrollY;
    private int mLastY;

    private int mDrawnCenterY;

    private boolean isCyclic = true;

    public WheelPicker(Context context) {
        this(context, null);
    }

    public WheelPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mData = Arrays.asList(getResources().getStringArray(R.array.WheelArrayDefault));
        mItemTextSize = 32;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            ViewConfiguration conf = ViewConfiguration.get(getContext());
            mTouchSlop = conf.getScaledTouchSlop();
            mMinimumVelocity = conf.getScaledMinimumFlingVelocity();
            mMaximumVelocity = conf.getScaledMaximumFlingVelocity();
        }
        // 确保滚轮选择器可见Item数量为奇数
        if (mVisibleItemCount % 2 == 0)
            mVisibleItemCount += 1;

        mDrawnItemCount = isCyclic ? mVisibleItemCount + 2 : mVisibleItemCount;

        // 根据滚轮选择器可见Item数量计算Item位置偏移
        mItemPositionOffset = (mVisibleItemCount / 2) + 1;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mPaint.setColor(0xFFFFFFFF);
        mPaint.setTextSize(mItemTextSize);
        mPaint.setTextAlign(Paint.Align.CENTER);

        for (Object obj : mData) {
            String text = String.valueOf(obj);

            int width = (int) mPaint.measureText(text);
            mTextMaxWidth = Math.max(mTextMaxWidth, width);

            Paint.FontMetrics metrics = mPaint.getFontMetrics();
            int height = (int) (metrics.bottom - metrics.top);
            mTextMaxHeight = Math.max(mTextMaxHeight, height);
        }
        mScroller = new Scroller(getContext());
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        mItemCenterX = getWidth() / 2;
        mItemHeight = getHeight() / mVisibleItemCount;
        mItemRockSplit = mItemHeight / 2;
//        if (isCyclic) {
//            mScrollY = (1 - (mCurrentItemPosition - (mVisibleItemCount - 1) / 2)) * mItemHeight;
//            mMinimumFlingDistance = Integer.MIN_VALUE;
//            mMaximumFlingDistance = Integer.MAX_VALUE;
//        } else {
//            mScrollY = (mCurrentItemPosition + (mVisibleItemCount - 1) / 2) * mItemHeight;
//            mMaximumFlingDistance = mItemHeight * mVisibleItemCount / 2 - mItemRockSplit;
//            mMinimumFlingDistance = mMaximumFlingDistance - mItemHeight * mData.size() + mItemHeight;
//        }
        mTextDrawnOffset = (int) (mItemHeight / 2 - ((mPaint.ascent() + mPaint.descent()) / 2));

        mDrawnCenterY = (int) (getHeight() / 2 - ((mPaint.ascent() + mPaint.descent()) / 2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int position = -mScrollY / mItemHeight;
        for (int i = position - 3, j = -3; i < position-3 + 7; i++, j++) {
            int newPos = i % mData.size();
            newPos = newPos < 0 ? (newPos + mData.size()) : newPos;
            String data = String.valueOf(mData.get(newPos));
            canvas.drawText(data, mItemCenterX, mDrawnCenterY + (j * mItemHeight) + mScrollY % mItemHeight, mPaint);
        }
//        Log.i("WheelPicker", mScrollY + ":");
//        int start = -mScrollY / mItemHeight;
//        for (int pos = start, index = isCyclic ? -1 : 0; pos < start + mDrawnItemCount; pos++, index++) {
//            float centerY = index * mItemHeight + mTextDrawnOffset + mScrollY % mItemHeight;
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
//            if (!TextUtils.isEmpty(data)) canvas.drawText(data, mItemCenterX, centerY, mPaint);
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
                mScrollY += (event.getY() - mLastY);
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
//                    mScroller.fling(0, mScrollY, 0, (int) mTracker.getYVelocity(), 0, 0,
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
//                    mScroller.startScroll(0, mScrollY, 0,
//                            computeDistanceToEndPoint(mScrollY % mItemHeight));
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

//    private int computeDistanceToEndPoint(int remainder) {
//        if (Math.abs(remainder) > mItemRockSplit)
//            if (mScrollY < 0)
//                return -mItemHeight - remainder;
//            else
//                return mItemHeight - remainder;
//        else
//            return -remainder;
//    }

//    @Override
//    public void run() {
//        if (mScroller.isFinished()) {
//            int result = (mScrollY / mItemHeight - mItemPositionOffset) % mData.size();
//            result = result < 0 ? result + mData.size() : result;
//            Log.i("WheelPicker", result + ":" + mData.get(result) + ":" + mScrollY);
//        }
//        if (mScroller.computeScrollOffset()) {
//            mScrollY = mScroller.getCurrY();
//            postInvalidate();
//            mHandler.postDelayed(this, 16);
//        }
//    }
}