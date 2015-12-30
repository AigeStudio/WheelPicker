package com.aigestudio.wheelpicker.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.HashMap;

public class WheelCurvedPicker extends WheelCrossPicker {
    private final HashMap<Integer, Integer> SPACE = new HashMap<>();
    private final HashMap<Integer, Integer> DEPTH = new HashMap<>();

    private final Camera camera = new Camera();
    private final Matrix matrixRotate = new Matrix(), matrixDepth = new Matrix();

    private int radius;
    private int degreeSingleDelta;
    private int degreeIndex, degreeUnitDelta;

    public WheelCurvedPicker(Context context) {
        super(context);
    }

    public WheelCurvedPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void computeWheelSizes() {
        super.computeWheelSizes();

        radius = mOrientation.computeRadius(itemCount, itemSpace, maxTextWidth, maxTextHeight);

        unit = (int) (180 * 1.0F / (itemCount + 1));

        wheelContentWidth = mOrientation.getCurvedWidth(radius, maxTextWidth, maxTextHeight);
        wheelContentHeight = mOrientation.getCurvedHeight(radius, maxTextWidth, maxTextHeight);

        unitDisplayMin = -90;
        unitDisplayMax = 90;

        unitDeltaMin = -unit * (data.size() - itemIndex - 1);
        unitDeltaMax = unit * itemIndex;
    }

    @Override
    protected void drawItems(Canvas canvas) {
        for (int i = -itemIndex; i < data.size() - itemIndex; i++) {
            int curUnit = unit * i;
            curUnit += (unitDeltaTotal + degreeSingleDelta);
            if (curUnit > unitDisplayMax || curUnit < unitDisplayMin) continue;

            int space = computeSpace(curUnit);
            if (space == 0) curUnit = 1;
            int depth = computeDepth(curUnit);

            camera.save();
            mOrientation.rotateCamera(camera, curUnit);
            camera.getMatrix(matrixRotate);
            camera.restore();
            mOrientation.matrixToCenter(matrixRotate, space, wheelCenterX, wheelCenterY);
            camera.save();
            camera.translate(0, 0, depth);
            camera.getMatrix(matrixDepth);
            camera.restore();
            mOrientation.matrixToCenter(matrixDepth, space, wheelCenterX, wheelCenterY);
            matrixRotate.postConcat(matrixDepth);

            canvas.save();
            canvas.concat(matrixRotate);
            canvas.clipRect(rectCurItem, Region.Op.DIFFERENCE);
            mTextPaint.setColor(textColor);
            mTextPaint.setAlpha(255 - 255 * Math.abs(curUnit) / unitDisplayMax);
            mOrientation.draw(canvas, mTextPaint, data.get(i + itemIndex), space, wheelCenterX,
                    wheelCenterTextY);
            canvas.restore();

            canvas.save();
            canvas.clipRect(rectCurItem);
            mTextPaint.setColor(curTextColor);
            mOrientation.draw(canvas, mTextPaint, data.get(i + itemIndex), space, wheelCenterX,
                    wheelCenterTextY);
            canvas.restore();
        }
    }

    private int computeSpace(int degree) {
        int space;
        if (SPACE.containsKey(degree)) {
            space = SPACE.get(degree);
        } else {
            space = (int) (Math.sin(Math.toRadians(degree)) * radius);
            SPACE.put(degree, space);
        }
        return space;
    }

    private int computeDepth(int degree) {
        int depth;
        if (DEPTH.containsKey(degree)) {
            depth = DEPTH.get(degree);
        } else {
            depth = (int) (radius - Math.cos(Math.toRadians(degree)) * radius);
            DEPTH.put(degree, depth);
        }
        return depth;
    }

    @Override
    protected void onTouchMove(MotionEvent event) {
        degreeUnitDelta = mOrientation.computeDegreeSingleDelta(diSingleMoveX, diSingleMoveY, radius);
        int curDis = mOrientation.obtainCurrentDis(diSingleMoveX, diSingleMoveY);
        if (Math.abs(curDis) >= radius) {
            if (curDis >= 0)
                degreeIndex++;
            else
                degreeIndex--;
            diSingleMoveX = 0;
            diSingleMoveY = 0;
            degreeUnitDelta = 0;
        }
        degreeSingleDelta = (degreeIndex * 80) + degreeUnitDelta;
//        Log.d("AigeStudio", degreeSingleDelta + ":" + degreeUnitDelta + ":" + degreeIndex + ":" + diSingleMoveY + ":" + radius);
//        degreeSingleDelta = mOrientation.computeDegreeSingleDelta(diSingleMoveX, diSingleMoveY, radius);
        super.onTouchMove(event);
    }

    @Override
    protected void onTouchUp(MotionEvent event) {
        unitDeltaTotal += degreeSingleDelta;
        degreeSingleDelta = 0;
        degreeUnitDelta = 0;
        degreeIndex = 0;
        super.onTouchUp(event);
    }

    @Override
    public void clearCache() {
        SPACE.clear();
        DEPTH.clear();
        mOrientation.clearCache();
    }
}