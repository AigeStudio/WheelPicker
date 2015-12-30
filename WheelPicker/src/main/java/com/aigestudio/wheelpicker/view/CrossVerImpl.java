package com.aigestudio.wheelpicker.view;

import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.VelocityTracker;

import com.aigestudio.wheelpicker.core.WheelScroller;

import java.util.HashMap;

class CrossVerImpl implements ICrossOrientation {
    private final HashMap<Integer, Integer> DEGREE = new HashMap<>();

    @Override
    public int getUnitDeltaTotal(WheelScroller scroller) {
        return scroller.getCurrY();
    }

    @Override
    public void startScroll(WheelScroller scroller, int start, int distance) {
        scroller.startScroll(0, start, 0, distance, 300);
    }

    @Override
    public int computeRadius(int count, int space, int width, int height) {
        return (int) (((count + 1) * height + (count - 1) * space) / Math.PI);
    }

    @Override
    public int getCurvedWidth(int radius, int width, int height) {
        return width;
    }

    @Override
    public int getCurvedHeight(int radius, int width, int height) {
        return radius * 2;
    }

    @Override
    public int getStraightWidth(int count, int space, int width, int height) {
        return width;
    }

    @Override
    public int getStraightHeight(int count, int space, int width, int height) {
        return height * count + (count - 1) * space;
    }

    @Override
    public int getStraightUnit(int space, int width, int height) {
        return height + space;
    }

    @Override
    public int getDisplay(int count, int space, int width, int height) {
        return (count / 2 + 1) * (height + space);
    }

    @Override
    public void rotateCamera(Camera camera, int degree) {
        camera.rotateX(-degree);
    }

    @Override
    public void matrixToCenter(Matrix matrix, int space, int x, int y) {
        matrix.preTranslate(-x, -(y + space));
        matrix.postTranslate(x, y + space);
    }

    @Override
    public void draw(Canvas canvas, TextPaint paint, String data, int space, int x, int y) {
        canvas.drawText(data, x, y + space, paint);
    }

    @Override
    public int computeDegreeSingleDelta(int moveX, int moveY, int radius) {
        int degree;
        if (DEGREE.containsKey(moveY)) {
            degree = DEGREE.get(moveY);
        } else {
            degree = (int) Math.toDegrees(Math.asin(moveY * 1.0 / radius));
            DEGREE.put(moveY, degree);
        }
        return degree;
    }

    @Override
    public void fling(WheelScroller scroller, VelocityTracker tracker, int from, int disMin, int disMax, int over) {
        scroller.fling(0, from, 0, (int) tracker.getYVelocity(), 0, 0, disMin, disMax, 0, over);
    }

    @Override
    public int computeStraightUnit(int unit, int index, int totalMoveX, int totalMoveY, int singleMoveX, int singleMoveY) {
        return unit * index + totalMoveY + singleMoveY;
    }

    @Override
    public int getUnitDeltaTotal(int totalMoveX, int totalMoveY) {
        return totalMoveY;
    }

    @Override
    public void computeCurItemRect(Rect rect, int space, int width, int height, int textWidth, int textHeight, int x, int y, int left, int top, int right, int bottom) {
        int half = (textHeight + space) / 2;
        rect.set(0, y - half, width, y + half);
    }

    @Override
    public void clearCache() {
        DEGREE.clear();
    }

    @Override
    public void removePadding(Rect rect, int left, int top, int right, int bottom) {
        rect.set(rect.left + left, rect.top, rect.right - right, rect.bottom);
    }

    @Override
    public void computeRectPadding(Rect rectLast, Rect rectNext, Rect rect, int left, int top, int right, int bottom) {
        rectLast.set(0, rect.top, left, rect.bottom);
        rectNext.set(rect.right - right, rect.top, rect.right, rect.bottom);
    }

    @Override
    public int obtainCurrentDis(int moveX, int moveY) {
        return moveY;
    }
}