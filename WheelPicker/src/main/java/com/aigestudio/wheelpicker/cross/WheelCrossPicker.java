package com.aigestudio.wheelpicker.cross;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.aigestudio.wheelpicker.AbstractWheelPicker;

/**
 * 垂直与水平滚动选择器抽象父类
 * 定义垂直和水平方向上滚动的选择器通用逻辑
 *
 * @author AigeStudio
 * @since 2016-06-17
 */
public class WheelCrossPicker extends AbstractWheelPicker implements IWheelCrossPicker {
    // 滚轮Item对齐方式
    public static final int
            ALIGN_CENTER = 0,// 居中对齐 该对齐方式对垂直和水平滚动器均有效
            ALIGN_LEFT = 1,// 靠左对齐 该对齐方式仅对垂直滚动器有效
            ALIGN_RIGHT = 2,// 靠右对齐 该对齐方式仅对垂直滚动器有效
            ALIGN_TOP = 3,// 置顶对齐 该对齐方式仅对水平滚动器有效
            ALIGN_BOTTOM = 4;// 置底对齐 该对齐方式仅对水平滚动器有效
    // 滚轮方向常量值
    public static final int
            ORI_VER = 0,// 垂直滚动
            ORI_HOR = 1;// 水平滚动

    private WheelDirection mWheelDirection;

    private Rect mTextBounds = new Rect();

    private int mDirection;
    private int mItemSpace;
    private int mItemCount;
    private int mIndicatorSize;

    private boolean hasIndicator;

    public WheelCrossPicker(Context context) {
        super(context);
    }

    public WheelCrossPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(AttributeSet attrs) {
        super.init(attrs);
        mWheelDirection = WheelCrossHelper.getWheelCrossDirection(mDirection);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int resultWidth = mWheelDirection.computeWidth(mPaint, mData, mItemCount, hasSameSize);
        int resultHeight = mWheelDirection.computeHeight(mPaint, mData, mItemCount, hasSameSize);

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
        for (int i = 0; i < mData.size(); i++) {
            canvas.drawText(String.valueOf(mData.get(i)), 0, 10 * i, mPaint);
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
        return mItemCount;
    }

    @Override
    public void setItemCount(int count) {
        if (count < 2)
            throw new RuntimeException("Wheel's item count can not be less than 2!");
        mItemCount = count;
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