package com.aigestudio.wheelpicker.cross;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.aigestudio.wheelpicker.R;
import com.aigestudio.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * 垂直与水平滚动选择器抽象父类
 * 定义垂直和水平方向上滚动的选择器通用逻辑
 *
 * @author AigeStudio
 * @since 2016-06-17
 */
public class WheelCrossPicker extends WheelPicker implements IWheelCrossPicker {
    // 滚轮Item对齐方式
    public static final int
            ALIGN_CENTER = 0,// 居中对齐 该对齐方式对垂直和水平滚动器均有效
            ALIGN_LEFT = 1,// 靠左对齐 该对齐方式仅对垂直滚动器有效
            ALIGN_RIGHT = 2,// 靠右对齐 该对齐方式仅对垂直滚动器有效
            ALIGN_TOP = 3,// 置顶对齐 该对齐方式仅对水平滚动器有效
            ALIGN_BOTTOM = 4;// 置底对齐 该对齐方式仅对水平滚动器有效
    // 滚轮方向常量值
    public static final int
            DIR_VER = 0,// 垂直滚动
            DIR_HOR = 1;// 水平滚动

    private WheelDirection mWheelDirection;

    private Rect mTextBounds = new Rect();

    private int mDirection;
    private int mItemSpace;
    private int mVisibleItemCount;
    private int mItemCount;
    private int mIndicatorSize;
    private int mWheelCenterX, mWheelCenterY;
    private int mFlingMin, mFlingMax;
    private int mIterationStart, mIterationEnd;

    private boolean hasIndicator;
    private boolean isCyclic;

    public WheelCrossPicker(Context context) {
        super(context);
    }

    public WheelCrossPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(AttributeSet attrs) {
        super.init(attrs);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.WheelPicker);
        mDirection = a.getInt(R.styleable.WheelPicker_wheel_direction, DIR_VER);
        mVisibleItemCount = a.getInt(R.styleable.WheelPicker_wheel_visible_item_count, 5);
        a.recycle();

        if (isCyclic) {
            mItemCount = Integer.MAX_VALUE;
        } else {
            mItemCount = mVisibleItemCount;
        }


        mWheelCenterX = mTextMaxWidth / 2;
        mWheelCenterY = (int) (mTextMaxHeight * mVisibleItemCount / 2 -
                ((mPaint.ascent() + mPaint.descent()) / 2));

        mFlingMin = -mTextMaxHeight * (mData.size() - mCurrentItemPosition - 1);
        mFlingMax = mTextMaxHeight * mCurrentItemPosition;

        mWheelDirection = WheelCrossHelper.getWheelCrossDirection(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int resultWidth = mWheelDirection.computeWidth();
        int resultHeight = mWheelDirection.computeHeight();

        resultWidth += (getPaddingLeft() + getPaddingRight());
        resultHeight += (getPaddingTop() + getPaddingBottom());

        resultWidth = measureSize(modeWidth, sizeWidth, resultWidth);
        resultHeight = measureSize(modeHeight, sizeHeight, resultHeight);

        setMeasuredDimension(resultWidth, resultHeight);
    }

    private int measureSize(int mode, int sizeExpect, int sizeActual) {
        int realSize;
        if (mode == MeasureSpec.EXACTLY)
            realSize = sizeExpect;
        else {
            realSize = sizeActual;
            if (mode == MeasureSpec.AT_MOST)
                realSize = Math.min(realSize, sizeExpect);
        }
        return realSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int offset = ((mMoveTotalY + mMoveSingleY) / mTextMaxHeight);
        Log.e("wheel", offset + "");
        mPaint.setStyle(Paint.Style.STROKE);

        List<String> draw = new ArrayList<>();

        int index = 0;
        for (int i = 0 - 2 - offset; i < 7 - 2 - offset; i++) {
            index = i;
            if (i < 0) index = mData.size() + (i % mData.size());
            draw.add(String.valueOf(mData.get(index % mData.size())));
        }

        for (int i = 0; i < 5; i++) {
//            if (i > 24) continue;
            canvas.drawRect(0, i * mTextMaxHeight, mTextMaxWidth, (i + 1) * mTextMaxHeight, mPaint);
            canvas.drawText(
                    String.valueOf(mData.get(i)),
                    mWheelCenterX,
                    (mTextMaxHeight / 2 - ((mPaint.ascent() + mPaint.descent()) / 2)) + (i * mTextMaxHeight) + mMoveTotalY + mMoveSingleY - offset * mTextMaxHeight,
                    mPaint);
        }
//        Log.e("wheel", mMoveTotalY + ":" + mMoveSingleY);
    }

    @Override
    protected void onTouchDown(MotionEvent event) {
        // Do nothing...
    }

    @Override
    protected void onTouchMove(MotionEvent event) {

        invalidate();
    }

    @Override
    protected void onTouchUp(MotionEvent event) {
//        Log.e("wheel", mTracker.getYVelocity() + "");
        mScroller.fling(0, mMoveTotalY, 0, (int) mTracker.getYVelocity(), 0, 0, -100000, 100000);
//        Log.e("wheel", mMoveTotalY + ":" + mScroller.getFinalY());
        int remainder = mScroller.getFinalY() % mTextMaxHeight;
        mScroller.setFinalY(mScroller.getFinalY() - remainder);
        mHandler.post(this);
    }

    @Override
    public void run() {
        if (mScroller.isFinished()) {
//            Log.e("Wheel", (mMoveTotalY / mTextMaxHeight) + "");
//            int remainder = mMoveTotalY % mTextMaxHeight;
//            Log.e("Wheel", remainder + "");
//            mScroller.startScroll(0, mMoveTotalY, 0, -remainder);
        }
        if (mScroller.computeScrollOffset()) {
            mMoveTotalY = mScroller.getCurrY();
            postInvalidate();
            mHandler.postDelayed(this, 16);
        }
    }

    @Override
    public int getDirection() {
        return mDirection;
    }

    @Override
    public void setDirection(int direction) {
        mDirection = direction;
    }

    @Override
    public int getItemCount() {
        return mVisibleItemCount;
    }

    @Override
    public void setItemCount(int count) {
        if (count < 2)
            throw new RuntimeException("Wheel's item count can not be less than 2!");
        mVisibleItemCount = count;
        requestLayout();
    }

    @Override
    public int getItemSpace() {
        return mItemSpace;
    }

    @Override
    public void setItemSpace(int space) {
        mItemSpace = space;
        requestLayout();
    }

    @Override
    public int getItemAlign() {
        return 0;
    }

    @Override
    public void setItemAlign(int align) {

    }

    @Override
    public void setIndicator(boolean hasIndicator) {

    }

    @Override
    public boolean hasIndicator() {
        return false;
    }

    @Override
    public int getIndicatorSize() {
        return 0;
    }

    @Override
    public void setIndicatorSize(int size) {

    }

    @Override
    public int getIndicatorColor() {
        return 0;
    }

    @Override
    public void setIndicatorColor(int color) {

    }

    @Override
    public void setCurtain(boolean hasCurtain) {

    }

    @Override
    public boolean hasCurtain() {
        return false;
    }

    @Override
    public int getCurtainColor() {
        return 0;
    }

    @Override
    public void setCurtainColor(int color) {

    }

    @Override
    public boolean isCyclic() {
        return false;
    }

    @Override
    public void setCyclic(boolean isCyclic) {

    }

    @Override
    public boolean isPerspective() {
        return false;
    }

    @Override
    public void setPerspective(boolean isPerspective) {

    }

    @Override
    public boolean isAtmospheric() {
        return false;
    }

    @Override
    public void setAtmospheric(boolean hasAtmospheric) {

    }
}