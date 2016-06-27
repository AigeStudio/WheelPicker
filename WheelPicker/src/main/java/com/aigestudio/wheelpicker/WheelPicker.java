package com.aigestudio.wheelpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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
public class WheelPicker extends View implements Runnable {
    // 滚轮选择器滚动状态标识值
    public static final int
            SCROLL_STATE_IDLE = 0,// 滚动停止时
            SCROLL_STATE_DRAGGING = 1,// 拖拽时
            SCROLL_STATE_SCROLLING = 2;// 滚动时
    // 滚轮选择器当前滚动方向标识值
    private static final int
            SCROLL_DIRECTION_LEFT = 0,// 向左滚动时
            SCROLL_DIRECTION_UP = 1,// 向上滚动时
            SCROLL_DIRECTION_RIGHT = 2,// 向右滚动时
            SCROLL_DIRECTION_DOWN = 3;// 向下滚动时

    private final Handler mHandler = new Handler();
    private Paint mPaint;
    private Scroller mScroller;
    private VelocityTracker mTracker;

    private List mData;// 数据源

    private int mVisibleItemCount = 5;// 滚轮选择器中可见的Item数量

    private int mItemTextSize;// Item文本大小
    private int mTextMaxWidth, mTextMaxHeight;
    private int mItemWidth, mItemHeight;
    private int mCurrentItemPosition;
    private int mTextDrawnOffset;// 文本绘制坐标偏移
    private int mMinimumVelocity, mMaximumVelocity;
    private int mTouchSlop;
    private int mCurrentScrollDirection = -1;
    private int mItemRockSplit;

    private int mItemCenterX, mItemCenterY;// Item中心坐标
    private int mScrollY;
    private int lastY;

    private boolean isCyclic = false;

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

        ViewConfiguration conf = ViewConfiguration.get(getContext());
        mTouchSlop = conf.getScaledTouchSlop();
        mMinimumVelocity = conf.getScaledMinimumFlingVelocity();
        mMaximumVelocity = conf.getScaledMaximumFlingVelocity();

        // 确保滚轮选择器可见Item数量为奇数
        if (mVisibleItemCount % 2 == 0)
            mVisibleItemCount += 1;

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
        mScrollY = (1 - (mCurrentItemPosition - (mVisibleItemCount - 1) / 2)) * mItemHeight;
        mTextDrawnOffset = (int) (mItemHeight / 2 - ((mPaint.ascent() + mPaint.descent()) / 2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int start = -mScrollY / mItemHeight;
        for (int pos = start, index = -1; pos < start + mVisibleItemCount + 2; pos++, index++) {
            float centerY = index * mItemHeight + mTextDrawnOffset + mScrollY % mItemHeight;
            String data = "";
            if (isPosIntraArea(pos)) {
                data = String.valueOf(mData.get(pos));
            } else {
                if (isCyclic) {
                    int newPos = pos % mData.size();
                    newPos = newPos < 0 ? (newPos + mData.size()) : newPos;
                    data = String.valueOf(mData.get(newPos));
                }
            }
            if (!TextUtils.isEmpty(data)) canvas.drawText(data, mItemCenterX, centerY, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(0, mItemHeight * index, getWidth(), mItemHeight * (1 + index), mPaint);
        }
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
                lastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mTracker.addMovement(event);

                // 判断滚动方向
                if (event.getY() > lastY)
                    mCurrentScrollDirection = SCROLL_DIRECTION_DOWN;
                else if (event.getY() < lastY)
                    mCurrentScrollDirection = SCROLL_DIRECTION_UP;

                // 滚动内容
                mScrollY += (event.getY() - lastY);
                invalidate();
                lastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                mTracker.addMovement(event);
                mTracker.computeCurrentVelocity(1000, mMaximumVelocity);

                // 根据速度判断是该滚动还是滑动
                int velocity = (int) mTracker.getYVelocity();
                if (Math.abs(velocity) >= mMinimumVelocity)
                    slideToLocation();
                else
                    scrollToLocation();
                if (null != mTracker) {
                    mTracker.recycle();
                    mTracker = null;
                }
                mHandler.post(this);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    /**
     * 滑动到指定位置
     */
    private void slideToLocation() {
        mScroller.fling(0, mScrollY, 0, (int) mTracker.getYVelocity(), 0, 0,
                Integer.MIN_VALUE, Integer.MAX_VALUE);
        int remainder = mScroller.getFinalY() % mItemHeight;
        Log.e("WheelDebug", remainder + ":" + mScroller.getFinalY() + ":" + mItemHeight);
        if (Math.abs(remainder) > mItemRockSplit) {
            if (mScrollY < 0) {
                mScroller.setFinalY(mScroller.getFinalY() - mItemHeight - remainder);
            } else {
                mScroller.setFinalY(mScroller.getFinalY() + mItemHeight - remainder);
            }
        } else {
            mScroller.setFinalY(mScroller.getFinalY() - remainder);
        }
//        if (remainder != 0) {
//            if (remainder >= mItemRockSplit) {
//                correctSlide(remainder - mItemHeight, mItemHeight - remainder);
////                mScroller.setFinalY(mScroller.getFinalY() - remainder);
//            } else {
//                correctSlide(remainder, -remainder);
////                mScroller.setFinalY(mScroller.getFinalY() - remainder);
//            }
//        }
//        if (mCurrentScrollDirection == SCROLL_DIRECTION_DOWN) {
//            if ()
//        } else if (mCurrentScrollDirection == SCROLL_DIRECTION_UP) {
//        }
    }

    /**
     * 滚动到指定位置
     */
    private void scrollToLocation() {
        int remainder = mScrollY % mItemHeight;
        if (Math.abs(remainder) > mItemRockSplit) {
            if (mScrollY < 0) {
                mScroller.startScroll(0, mScrollY, 0, -mItemHeight - remainder);
            } else {
                mScroller.startScroll(0, mScrollY, 0, mItemHeight - remainder);
            }
        } else {
            mScroller.startScroll(0, mScrollY, 0, -remainder);
        }
    }

    @Override
    public void run() {
        if (mScroller.isFinished()) {
            mCurrentScrollDirection = -1;
        }
        if (mScroller.computeScrollOffset()) {
            mScrollY = mScroller.getCurrY();
            postInvalidate();
            mHandler.postDelayed(this, 16);
        }
    }
}