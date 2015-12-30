package com.aigestudio.wheelpicker.view;

import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.VelocityTracker;

import com.aigestudio.wheelpicker.core.WheelScroller;

import java.util.HashMap;

class CrossHorImpl implements ICrossOrientation {
    private final HashMap<Integer, Integer> DEGREE = new HashMap<>();

    @Override
    public int getUnitDeltaTotal(WheelScroller scroller) {
        return scroller.getCurrX();
    }

    @Override
    public void startScroll(WheelScroller scroller, int start, int distance) {
        scroller.startScroll(start, 0, distance, 0, 300);
    }

    @Override
    public int computeRadius(int count, int space, int width, int height) {
        return (int) (((count + 1) * width + (count - 1) * space) / Math.PI);
    }

    @Override
    public int getCurvedWidth(int radius, int width, int height) {
        return radius * 2;
    }

    @Override
    public int getCurvedHeight(int radius, int width, int height) {
        return height;
    }

    @Override
    public int getStraightWidth(int count, int space, int width, int height) {
        return width * count + (count - 1) * space;
    }

    @Override
    public int getStraightHeight(int count, int space, int width, int height) {
        return height;
    }

    @Override
    public int getStraightUnit(int space, int width, int height) {
        return width + space;
    }

    @Override
    public int getDisplay(int count, int space, int width, int height) {
        return (count / 2 + 1) * (width + space);
    }

    @Override
    public void rotateCamera(Camera camera, int degree) {
        camera.rotateY(degree);
    }

    @Override
    public void matrixToCenter(Matrix matrix, int space, int x, int y) {
        matrix.preTranslate(-(x + space), -y);
        matrix.postTranslate(x + space, y);
    }

    @Override
    public void draw(Canvas canvas, TextPaint paint, String data, int space, int x, int y) {
        canvas.drawText(data, x + space, y, paint);
    }

    @Override
    public int computeDegreeSingleDelta(int moveX, int moveY, int radius) {
        int degree;
        if (DEGREE.containsKey(moveX)) {
            degree = DEGREE.get(moveX);
        } else {
            degree = (int) Math.toDegrees(Math.asin(moveX * 1.0 / radius));
            DEGREE.put(moveX, degree);
        }
        return degree;
    }

    @Override
    public void fling(WheelScroller scroller, VelocityTracker tracker, int from, int disMin, int disMax, int over) {
        scroller.fling(from, 0, (int) tracker.getXVelocity(), 0, disMin, disMax, 0, 0, over, 0);
    }

    @Override
    public int computeStraightUnit(int unit, int index, int totalMoveX, int totalMoveY, int singleMoveX, int singleMoveY) {
        return unit * index + totalMoveX + singleMoveX;
    }

    @Override
    public int getUnitDeltaTotal(int totalMoveX, int totalMoveY) {
        return totalMoveX;
    }

    @Override
    public void computeCurItemRect(Rect rect, int space, int width, int height, int textWidth, int textHeight, int x, int y, int left, int top, int right, int bottom) {
        int half = (textWidth + space) / 2;
        rect.set(x - half, 0, x + half, height);
    }

    @Override
    public void clearCache() {
        DEGREE.clear();
    }

    @Override
    public void removePadding(Rect rect, int left, int top, int right, int bottom) {
        rect.set(rect.left, rect.top + top, rect.right, rect.bottom - bottom);
    }

    @Override
    public void computeRectPadding(Rect rectLast, Rect rectNext, Rect rect, int left, int top, int right, int bottom) {
        rectLast.set(rect.left, 0, rect.right, top);
        rectNext.set(rect.left, rect.bottom - bottom, rect.right, rect.bottom);
    }

    @Override
    public int obtainCurrentDis(int moveX, int moveY) {
        return moveX;
    }
}