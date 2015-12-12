package com.aigestudio.wheelpicker.view;

import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.Scroller;

/**
 * 滚轮垂直方向实现
 * Implement of WheelView's vertical direction
 *
 * @author AigeStudio 2015-12-03
 * @author AigeStudio 2015-12-08
 * @version 1.0.0 beta
 */
class WheelDirectVer implements IWheelDirection {
    @Override
    public int measureWidth(int sizeA, int sizeB) {
        return Math.min(sizeA, sizeB);
    }

    @Override
    public int measureHeight(int sizeA, int sizeB) {
        return Math.max(sizeA, sizeB);
    }

    @Override
    public int getCurrentPoint(MotionEvent event) {
        return (int) event.getY();
    }

    @Override
    public boolean isValidArea(MotionEvent event, int width, int height) {
        return event.getY() <= 0 || event.getY() >= height;
    }

    @Override
    public void startScroll(Scroller scroller, int start, int distance, int duration) {
        scroller.startScroll(0, start, 0, distance, duration);
    }

    @Override
    public void startFling(Scroller scroller, VelocityTracker tracker, int start, int disMin, int disMax) {
        scroller.fling(0, start, 0, (int) tracker.getYVelocity(), 0, 0, disMin, disMax);
    }

    @Override
    public int getFinal(Scroller scroller) {
        return scroller.getFinalY();
    }

    @Override
    public int getCurrent(Scroller scroller) {
        return scroller.getCurrY();
    }

    @Override
    public int computeStraightWidth(int count, int space, int width, int height) {
        return width;
    }

    @Override
    public int computeStraightHeight(int count, int space, int width, int height) {
        return count * height + (count - 1) / 2 * space;
    }

    @Override
    public int computeCurvedWidth(int count, int space, int width, int height) {
        return width;
    }

    @Override
    public int computeCurvedHeight(int count, int space, int width, int height) {
        return WheelUtil.calculateRadius(count, height) * 2 + space * (count - 1);
    }

    @Override
    public int computeUnitStraight(int width, int height, int count) {
        return height / count;
    }

    @Override
    public int computeRadiusCurved(int width, int height) {
        return height / 2;
    }

    @Override
    public int getUnitDisplayRule(int width, int height) {
        return height / 2;
    }
}