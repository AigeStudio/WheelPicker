package com.aigestudio.wheelpicker.view;

import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.Scroller;

/**
 * 滚轮水平方向实现
 * Implement of WheelView's horizontal direction
 *
 * @author AigeStudio 2015-12-03
 * @author AigeStudio 2015-12-08
 * @version 1.0.0 beta
 */
class WheelDirectHor implements IWheelDirection {
    @Override
    public int measureWidth(int sizeA, int sizeB) {
        return Math.max(sizeA, sizeB);
    }

    @Override
    public int measureHeight(int sizeA, int sizeB) {
        return Math.min(sizeA, sizeB);
    }

    @Override
    public int getCurrentPoint(MotionEvent event) {
        return 0;
    }

    @Override
    public boolean isValidArea(MotionEvent event, int width, int height) {
        return false;
    }

    @Override
    public void startScroll(Scroller scroller, int start, int distance, int duration) {

    }

    @Override
    public void startFling(Scroller scroller, VelocityTracker tracker, int start, int disMin, int disMax) {

    }

    @Override
    public int getFinal(Scroller scroller) {
        return 0;
    }

    @Override
    public int getCurrent(Scroller scroller) {
        return scroller.getCurrY();
    }

    @Override
    public int computeStraightWidth(int count, int space, int width, int height) {
        return count * width + (count - 1) / 2 * space;
    }

    @Override
    public int computeStraightHeight(int count, int space, int width, int height) {
        return height;
    }

    @Override
    public int computeCurvedWidth(int count, int space, int width, int height) {
        return 0;
    }

    @Override
    public int computeCurvedHeight(int count, int space, int width, int height) {
        return 0;
    }

    @Override
    public int computeUnitStraight(int width, int height, int count) {
        return 0;
    }

    @Override
    public int computeRadiusCurved(int width, int height) {
        return 0;
    }

    @Override
    public int getUnitDisplayRule(int width, int height) {
        return width / 2;
    }
}