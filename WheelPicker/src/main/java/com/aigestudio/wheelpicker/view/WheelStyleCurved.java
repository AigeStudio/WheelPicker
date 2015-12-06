package com.aigestudio.wheelpicker.view;

import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.MotionEvent;

import static com.aigestudio.wheelpicker.view.WheelCons.DEGREE_MAXIMUM;
import static com.aigestudio.wheelpicker.view.WheelCons.DEGREE_MINIMUM;

/**
 * 滚轮弯曲样式实现
 * Curve style implement of WheelView
 *
 * @author AigeStudio 2015-12-03
 * @version 1.0.0 preview
 */
class WheelStyleCurved extends AbstractWheelStyle {
    private final Camera camera = new Camera();
    private final Matrix matrixRotate = new Matrix();
    private final Matrix matrixDepth = new Matrix();

    private int degreeSingleMove;

    WheelStyleCurved(WheelView view) {
        super(view);
    }

    @Override
    void computeWheel() {
        width = direction.computeCurvedWidth(view.itemCount, view.itemSpace, maxTextWidth, maxTextHeight);
        height = direction.computeCurvedHeight(view.itemCount, view.itemSpace, maxTextWidth, maxTextHeight);
        unit = WheelUtil.calculateDegree(view.itemCount);
        radius = direction.computeRadiusCurved(width, height);
        unitDisplayMin = DEGREE_MINIMUM;
        unitDisplayMax = DEGREE_MAXIMUM;
    }

    @Override
    void onDraw(Canvas canvas) {
        for (int i = -view.itemIndex; i < view.data.size() - view.itemIndex; i++) {
            int curUnit = unit * i;
            curUnit += (unitTotalMove + degreeSingleMove);
            if (curUnit > unitDisplayMax || curUnit < unitDisplayMin) {
                continue;
            }
            int space = WheelUtil.calculateSpace(curUnit, radius);
            int depth = WheelUtil.calculateDepth(curUnit, radius);
            canvas.save();
            camera.save();
            camera.rotateX(-curUnit);
            camera.getMatrix(matrixRotate);
            camera.restore();
            matrixRotate.preTranslate(-centerX, -(centerY + space));
            matrixRotate.postTranslate(centerX, centerY + space);
            camera.save();
            camera.translate(0, 0, depth);
            camera.getMatrix(matrixDepth);
            camera.restore();
            matrixDepth.preTranslate(-centerX, -(centerY + space));
            matrixDepth.postTranslate(centerX, centerY + space);
            matrixRotate.postConcat(matrixDepth);
            canvas.concat(matrixRotate);
            if (view.isTextTransGradient)
                paint.setAlpha(255 - 255 * Math.abs(curUnit) / unitDisplayMax);
            canvas.drawText(view.data.get(i + view.itemIndex), centerX,
                    centerTextY + space, paint);
            canvas.restore();
        }
    }

    @Override
    void onTouchEventMove(MotionEvent event) {
        if (direction.isValidArea(event, view.getWidth(), view.getHeight())) return;
        distanceSingleMove += (direction.getCurrentPoint(event) - lastPoint);
        if (Math.abs(distanceSingleMove) < WheelCons.TOUCH_DISTANCE_MINIMUM) return;
        if (Math.abs(distanceSingleMove) >= radius) return;
        degreeSingleMove = WheelUtil.calculateDegree(distanceSingleMove, radius);
        lastPoint = direction.getCurrentPoint(event);
    }

    @Override
    void onTouchEventUp(MotionEvent event) {
        if (Math.abs(distanceSingleMove) < WheelCons.TOUCH_DISTANCE_MINIMUM) return;
        isScrollingTerminal = true;
        distanceSingleMove = 0;
        unitTotalMove += degreeSingleMove;
        degreeSingleMove = 0;
        if (unitTotalMove > unitMoveMax) {
            direction.startScroll(scroller, unitTotalMove, unitMoveMax - unitTotalMove);
            return;
        }
        if (unitTotalMove < unitMoveMin) {
            direction.startScroll(scroller, unitTotalMove, unitMoveMin - unitTotalMove);
            return;
        }
        tracker.computeCurrentVelocity(WheelCons.VELOCITY_TRACKER_UNITS);
        direction.startFling(scroller, tracker, unitTotalMove, unitMoveMin, unitMoveMax);
        finalUnit = direction.getFinal(scroller);
    }
}