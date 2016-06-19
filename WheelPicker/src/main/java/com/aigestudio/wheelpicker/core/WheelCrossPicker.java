package com.aigestudio.wheelpicker.core;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

/**
 * 垂直与水平滚动选择器抽象父类
 * 定义垂直和水平方向上滚动的选择器通用逻辑
 *
 * @author AigeStudio
 * @since 2016-06-17
 */
public abstract class WheelCrossPicker extends AbstractWheelPicker implements IWheelCrossPicker {
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

    private int mOrientation;

    public WheelCrossPicker(Context context) {
        super(context);
    }

    public WheelCrossPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int resultWidth = mWheelActualWidth;
        int resultHeight = mWheelActualHeight;

        resultWidth += (getPaddingLeft() + getPaddingRight());
        resultHeight += (getPaddingTop() + getPaddingBottom());

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
            if (mode == MeasureSpec.AT_MOST) {
                realSize = Math.min(realSize, sizeExpect);
            }
        }
        return realSize;
    }

    @Override
    protected void computeWheelSize() {
        switch (mOrientation) {
            case ORI_VER:
                mWheelActualWidth = computeWheelSizeShort();
                mWheelActualHeight = computeWheelSizeLong();
                break;
            case ORI_HOR:
                mWheelActualWidth = computeWheelSizeLong();
                mWheelActualHeight = computeWheelSizeShort();
                break;
        }
    }

    private int computeWheelSizeShort() {
        int size = 0;
        if (hasSameSize)
            size = computeWheelSizeShort(String.valueOf(mData.get(0)));
        else
            for (Object obj : mData)
                size = computeWheelSizeShort(String.valueOf(obj));
        return size;
    }

    private int computeWheelSizeShort(String text) {
        int size = 0;
        Rect bounds = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        switch (mOrientation) {
            case ORI_VER:
                size = bounds.width();
                break;
            case ORI_HOR:
                size = bounds.height();
                break;
        }
        return size;
    }

    /**
     *
     * @return
     */
    protected abstract int computeWheelSizeLong();

    @Override
    public void setOrientation(int orientation) {
        mOrientation = orientation;
    }

    @Override
    public int getOrientation() {
        return mOrientation;
    }
}