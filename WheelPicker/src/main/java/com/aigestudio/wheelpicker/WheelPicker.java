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
public class WheelPicker extends View {
    // 滚轮选择器滚动状态标识值
    public static final int
            SCROLL_STATE_IDLE = 0,// 滚动停止时
            SCROLL_STATE_DRAGGING = 1,// 拖拽时
            SCROLL_STATE_SCROLLING = 2;// 滚动时

    private Paint mPaint;
    private Scroller mScroller;

    private List mData;// 数据源

    private int mVisibleItemCount;// 滚轮选择器中可见的Item数量

    private int mItemTextSize;// Item文本大小
    private int mTextMaxWidth, mTextMaxHeight;
    private int mItemWidth, mItemHeight;
    private int mCurrentItemPosition;
    private int mCenterOffsetY;

    private int mItemCenterX, mItemCenterY;// Item文本绘制中心X坐标
    private int mScrollY;

    private boolean isCyclic;

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

        mScrollY = (1 - (mCurrentItemPosition - (mVisibleItemCount - 1) / 2)) * mTextMaxHeight;

        mCenterOffsetY = (int) (mTextMaxHeight / 2 - ((mPaint.ascent() + mPaint.descent()) / 2));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mItemCenterX = getWidth() / 2;
        mItemHeight = getHeight() / mVisibleItemCount;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int start = -mScrollY / mTextMaxHeight;
        for (int pos = start, index = -1; pos < start + mVisibleItemCount + 2; pos++, index++) {
            float centerY = index * mTextMaxHeight + mScrollY % mTextMaxHeight + mCenterOffsetY;
            if (isPosIntraArea(pos)) {
                canvas.drawText(String.valueOf(mData.get(pos)), mItemCenterX, centerY, mPaint);
            } else {
                if (isCyclic) {
                    int newPos = pos % mData.size();
                    newPos = newPos < 0 ? newPos + mData.size() : newPos;
                    canvas.drawText(String.valueOf(mData.get(newPos)), mItemCenterX, centerY, mPaint);
                }
            }
        }
    }

    private boolean isPosIntraArea(int position) {
        return position >= 0 && position < mData.size();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mScrollY = mScroller.getCurrY();
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }
}