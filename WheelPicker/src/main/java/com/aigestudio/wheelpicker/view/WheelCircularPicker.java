package com.aigestudio.wheelpicker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.aigestudio.wheelpicker.core.AbstractWheelPicker;

/**
 * 圆形滚动选择器
 * 该View将会在以后的版本中启用
 * CircularPicker
 * This view will be available in later versions
 */
class WheelCircularPicker extends AbstractWheelPicker {
    public WheelCircularPicker(Context context) {
        super(context);
    }

    public WheelCircularPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void computeWheelSizes() {

    }

    @Override
    protected void drawBackground(Canvas canvas) {

    }

    @Override
    protected void drawItems(Canvas canvas) {

    }

    @Override
    protected void drawForeground(Canvas canvas) {

    }

    @Override
    protected void onTouchDown(MotionEvent event) {

    }

    @Override
    protected void onTouchMove(MotionEvent event) {

    }

    @Override
    protected void onTouchUp(MotionEvent event) {

    }

    @Override
    public void clearCache() {

    }
}