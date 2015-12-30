package com.aigestudio.wheelpicker.view;

import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.VelocityTracker;

import com.aigestudio.wheelpicker.core.WheelScroller;

interface ICrossOrientation {

    int getUnitDeltaTotal(WheelScroller scroller);

    void startScroll(WheelScroller scroller, int start, int distance);

    int computeRadius(int count, int space, int width, int height);

    int getCurvedWidth(int radius, int width, int height);

    int getCurvedHeight(int radius, int width, int height);

    int getStraightWidth(int count, int space, int width, int height);

    int getStraightHeight(int count, int space, int width, int height);

    int getStraightUnit(int space, int width, int height);

    int getDisplay(int count, int space, int width, int height);

    void rotateCamera(Camera camera, int degree);

    void matrixToCenter(Matrix matrix, int space, int x, int y);

    void draw(Canvas canvas, TextPaint paint, String data, int space, int x, int y);

    int computeDegreeSingleDelta(int moveX, int moveY, int radius);

    void fling(WheelScroller scroller, VelocityTracker tracker, int from, int disMin, int disMax, int over);

    int computeStraightUnit(int unit, int index, int totalMoveX, int totalMoveY, int singleMoveX, int singleMoveY);

    int getUnitDeltaTotal(int totalMoveX, int totalMoveY);

    void computeCurItemRect(Rect rect, int space, int width, int height, int textWidth, int textHeight, int x, int y, int left, int top, int right, int bottom);

    void clearCache();

    void removePadding(Rect rect, int left, int top, int right, int bottom);

    void computeRectPadding(Rect rectLast, Rect rectNext, Rect rect, int left, int top, int right, int bottom);

    int obtainCurrentDis(int moveX, int moveY);
}