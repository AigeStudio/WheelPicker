package com.aigestudio.wheelpicker.core;

public interface WheelScroller {
    void abortAnimation();

    boolean computeScrollOffset();

    void extendDuration(int extend);

    void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY);

    void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY);

    void forceFinished(boolean finished);

    float getCurrVelocity();

    int getCurrX();

    int getCurrY();

    int getDuration();

    int getFinalX();

    int getFinalY();

    int getStartX();

    int getStartY();

    boolean isFinished();

    void setFinalX(int newX);

    void setFinalY(int newY);

    boolean isOverScrolled();

    void notifyHorizontalEdgeReached(int startX, int finalX, int overX);

    void notifyVerticalEdgeReached(int startY, int finalY, int overY);

    void setFriction(float friction);

    boolean springBack(int startX, int startY, int minX, int maxX, int minY, int maxY);

    void startScroll(int startX, int startY, int dx, int dy);

    void startScroll(int startX, int startY, int dx, int dy, int duration);

    int timePassed();
}