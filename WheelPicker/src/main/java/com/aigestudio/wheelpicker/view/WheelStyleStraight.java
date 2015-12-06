package com.aigestudio.wheelpicker.view;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * 滚轮默认直的样式实现
 * Default style implement of WheelView in Straight
 *
 * @author AigeStudio 2015-12-03
 * @version 1.0.0 preview
 */
class WheelStyleStraight extends AbstractWheelStyle {

    WheelStyleStraight(WheelView view) {
        super(view);
    }

    @Override
    void computeWheel() {
        width = direction.computeStraightWidth(view.itemCount, view.itemSpace, maxTextWidth,
                maxTextHeight);
        height = direction.computeStraightHeight(view.itemCount, view.itemSpace, maxTextWidth,
                maxTextHeight);
        unit = direction.computeUnitStraight(width, height, view.itemCount);
        unitDisplayMin = -Math.max(width, height) / 2;
        unitDisplayMax = -unitDisplayMin;
    }

    @Override
    void onDraw(Canvas canvas) {
        for (int i = -view.itemIndex; i < view.data.size() - view.itemIndex; i++) {
            int curUnit = unit * i;
            curUnit += (unitTotalMove + distanceSingleMove);
            if (curUnit > unitDisplayMax || curUnit < unitDisplayMin) {
                continue;
            }
            canvas.save();
            if (view.isTextTransGradient)
                paint.setAlpha(255 - 255 * Math.abs(curUnit) / unitDisplayMax);
            canvas.drawText(view.data.get(i + view.itemIndex), centerX,
                    centerTextY + curUnit, paint);
            canvas.restore();
        }
    }

    @Override
    void onTouchEventMove(MotionEvent event) {
        if (direction.isValidArea(event, view.getWidth(), view.getHeight())) return;
        distanceSingleMove += (direction.getCurrentPoint(event) - lastPoint);
        if (Math.abs(distanceSingleMove) < WheelCons.TOUCH_DISTANCE_MINIMUM) return;
        if (Math.abs(distanceSingleMove) >= width) return;
        lastPoint = direction.getCurrentPoint(event);
    }

    @Override
    void onTouchEventUp(MotionEvent event) {
        if (Math.abs(distanceSingleMove) < WheelCons.TOUCH_DISTANCE_MINIMUM) return;
        isScrollingTerminal = true;
        unitTotalMove += distanceSingleMove;
        distanceSingleMove = 0;
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