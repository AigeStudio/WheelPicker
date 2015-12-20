package com.aigestudio.wheelpicker.core;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.animation.Interpolator;
import android.widget.OverScroller;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
class OverScrollerCompat implements WheelScroller {
    private OverScroller mOverScroller;

    OverScrollerCompat(Context context) {
        mOverScroller = new OverScroller(context);
    }

    OverScrollerCompat(Context context, Interpolator interpolator) {
        mOverScroller = new OverScroller(context, interpolator);
    }

    OverScrollerCompat(Context context, Interpolator interpolator, float bounceCoefficientX, float bounceCoefficientY) {
        mOverScroller = new OverScroller(context, interpolator, bounceCoefficientX, bounceCoefficientY);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    OverScrollerCompat(Context context, Interpolator interpolator, float bounceCoefficientX, float bounceCoefficientY, boolean flywheel) {
        mOverScroller = new OverScroller(context, interpolator, bounceCoefficientX, bounceCoefficientY, flywheel);
    }

    @Override
    public void abortAnimation() {
        mOverScroller.abortAnimation();
    }

    @Override
    public boolean computeScrollOffset() {
        return mOverScroller.computeScrollOffset();
    }

    @Override
    public void extendDuration(int extend) {
        throw new RuntimeException("OverScrollerCompat not support this method.");
    }

    @Override
    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
        mOverScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
    }

    @Override
    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY) {
        mOverScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, overX, overY);
    }

    @Override
    public void forceFinished(boolean finished) {
        mOverScroller.forceFinished(finished);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public float getCurrVelocity() {
        return mOverScroller.getCurrVelocity();
    }

    @Override
    public int getCurrX() {
        return mOverScroller.getCurrX();
    }

    @Override
    public int getCurrY() {
        return mOverScroller.getCurrY();
    }

    @Override
    public int getDuration() {
        throw new RuntimeException("OverScrollerCompat not support this method.");
    }

    @Override
    public int getFinalX() {
        return mOverScroller.getFinalX();
    }

    @Override
    public int getFinalY() {
        return mOverScroller.getFinalY();
    }

    @Override
    public int getStartX() {
        return mOverScroller.getStartX();
    }

    @Override
    public int getStartY() {
        return mOverScroller.getStartY();
    }

    @Override
    public boolean isFinished() {
        return mOverScroller.isFinished();
    }

    @Override
    public void setFinalX(int newX) {
        throw new RuntimeException("OverScrollerCompat not support this method.");
    }

    @Override
    public void setFinalY(int newY) {
        throw new RuntimeException("OverScrollerCompat not support this method.");
    }

    @Override
    public boolean isOverScrolled() {
        return mOverScroller.isOverScrolled();
    }

    @Override
    public void notifyHorizontalEdgeReached(int startX, int finalX, int overX) {
        mOverScroller.notifyHorizontalEdgeReached(startX, finalX, overX);
    }

    @Override
    public void notifyVerticalEdgeReached(int startY, int finalY, int overY) {
        mOverScroller.notifyVerticalEdgeReached(startY, finalY, overY);

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void setFriction(float friction) {
        mOverScroller.setFriction(friction);
    }

    @Override
    public boolean springBack(int startX, int startY, int minX, int maxX, int minY, int maxY) {
        return mOverScroller.springBack(startX, startY, minX, maxX, minY, maxY);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        mOverScroller.startScroll(startX, startY, dx, dy);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        mOverScroller.startScroll(startX, startY, dx, dy, duration);
    }

    @Override
    public int timePassed() {
        throw new RuntimeException("OverScrollerCompat not support this method.");
    }
}