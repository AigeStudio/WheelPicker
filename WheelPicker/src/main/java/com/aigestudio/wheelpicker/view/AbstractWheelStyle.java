package com.aigestudio.wheelpicker.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

/**
 * 抽象滚轮样式
 * Abstract of WheelView's style
 *
 * @author AigeStudio 2015-12-03
 * @version 1.0.0 preview
 */
abstract class AbstractWheelStyle {
    WheelView view;

    IWheelDirection direction;

    Scroller scroller;
    VelocityTracker tracker;
    Paint paint;
    Paint paintDecor;
    Rect rectDecorCurrent = new Rect();

    int centerX, centerY;
    int centerTextY;
    int lastPoint;
    int distanceSingleMove;
    int maxTextWidth, maxTextHeight;

    int unit;
    int radius;
    int width, height;
    int unitMoveMin, unitMoveMax;
    int finalUnit = -1;
    int unitDisplayMin, unitDisplayMax;
    int unitTotalMove;

    boolean isScrollingTerminal;

    String textCurrentItem;

    AbstractWheelStyle(WheelView view) {
        this.view = view;

        direction = WheelFactory.createWheelDirection(view.direction);

        scroller = new Scroller(view.getContext(), new DecelerateInterpolator());

        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(view.textColor);
        paint.setTextSize(view.textSize);

        paintDecor = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);

        computeTextSize();
        computeWheel();
    }

    void computeTextSize() {
        Rect textBounds = new Rect();
        maxTextWidth = 0;
        maxTextHeight = 0;
        if (view.hasSameSize) {
            String tmp = view.data.get(0);
            paint.getTextBounds(tmp, 0, tmp.length(), textBounds);
            maxTextWidth = textBounds.width();
            maxTextHeight = textBounds.height();
        } else if (!TextUtils.isEmpty(view.textWidthMaximum) &&
                !TextUtils.isEmpty(view.textHeightMaximum)) {
            paint.getTextBounds(view.textWidthMaximum, 0, view.textWidthMaximum.length(),
                    textBounds);
            maxTextWidth = textBounds.width();
            paint.getTextBounds(view.textHeightMaximum, 0, view.textHeightMaximum.length(),
                    textBounds);
            maxTextHeight = textBounds.height();
        } else if (view.itemIndexWidthMaximum != -1 && view.itemIndexHeightMaximum != -1) {
            String tmp = view.data.get(view.itemIndexWidthMaximum);
            paint.getTextBounds(tmp, 0, tmp.length(), textBounds);
            maxTextWidth = textBounds.width();
            tmp = view.data.get(view.itemIndexHeightMaximum);
            paint.getTextBounds(tmp, 0, tmp.length(), textBounds);
            maxTextHeight = textBounds.height();
        } else {
            for (String tmp : view.data) {
                paint.getTextBounds(tmp, 0, tmp.length(), textBounds);
                maxTextWidth = Math.max(maxTextWidth, textBounds.width());
                maxTextHeight = Math.max(maxTextHeight, textBounds.height());
            }
        }
    }

    int measureSize(int mode, int sizeExpect, int sizeActual) {
        if (mode == View.MeasureSpec.EXACTLY) return sizeExpect;
        else return Math.min(sizeExpect, sizeActual);
    }

    abstract void computeWheel();

    void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int modeWidth = View.MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = View.MeasureSpec.getMode(heightMeasureSpec);

        int sizeWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = View.MeasureSpec.getSize(heightMeasureSpec);

        int width = direction.measureWidth(maxTextWidth, this.width);
        int height = direction.measureHeight(maxTextHeight, this.height);

        width = measureSize(modeWidth, sizeWidth, width);
        height = measureSize(modeHeight, sizeHeight, height);

        width += (view.getPaddingLeft() + view.getPaddingRight());
        height += (view.getPaddingTop() + view.getPaddingBottom());

        view.setWheelSize(width, height);
    }

    void onSizeChanged(int width, int height) {
        centerX = (int) (width / 2.0F);
        centerY = (int) (height / 2.0F);
        centerTextY = (int) (height / 2.0F - (paint.ascent() + paint.descent()) / 2.0F);

        unitMoveMin = -unit * (view.data.size() - view.itemIndex - 1);
        unitMoveMax = unit * view.itemIndex;

        textCurrentItem = view.data.get(view.itemIndex);

        computeCurrentDecorArea();
    }

    void computeCurrentDecorArea() {
        int tmp = maxTextHeight / 2 + view.itemSpace / 4;
        int left = 0, top = centerY - tmp,
                right = width + view.getPaddingRight() * 2, bottom = centerY + tmp;
        if (!view.ignorePadding) {
            left = view.getPaddingLeft();
            right = width + view.getPaddingRight();
        }
        rectDecorCurrent.set(left, top, right, bottom);
    }

    void onDraw(Canvas canvas) {
        canvas.save();
        canvas.clipRect(rectDecorCurrent);
        if (null != view.decorBg) view.decorBg.drawDecor(canvas, paintDecor);
        canvas.restore();

        drawItems(canvas);

        canvas.save();
        canvas.clipRect(rectDecorCurrent);
        if (null != view.decorFg) view.decorFg.drawDecor(canvas, paintDecor);
        canvas.restore();
    }

    abstract void drawItems(Canvas canvas);

    void onTouchEvent(MotionEvent event) {
        if (null == tracker) {
            tracker = VelocityTracker.obtain();
        }
        tracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                view.interceptTouchEvent(true);
                if (!scroller.isFinished()) scroller.abortAnimation();
                lastPoint = direction.getCurrentPoint(event);
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchEventMove(event);
                break;
            case MotionEvent.ACTION_UP:
                view.interceptTouchEvent(false);
                onTouchEventUp(event);
                tracker.recycle();
                tracker = null;
                break;
        }
        view.invalidate();
    }

    abstract void onTouchEventMove(MotionEvent event);

    abstract void onTouchEventUp(MotionEvent event);

    void computeScroll() {
        if (scroller.computeScrollOffset()) {
            unitTotalMove = direction.getCurrent(scroller);
            view.invalidate();
        }
        if (unitTotalMove == finalUnit && isScrollingTerminal) {
            isScrollingTerminal = false;
            if (unitTotalMove != 0) correctDegree();
        }
        if (unitTotalMove % unit == 0) {
            int tmpIndex = view.itemIndex - unitTotalMove / unit;
            if (tmpIndex < 0) tmpIndex = 0;
            if (tmpIndex >= view.data.size()) tmpIndex = view.data.size() - 1;
            String tmpData = view.data.get(tmpIndex);
            if (!tmpData.equals(textCurrentItem)) {
                textCurrentItem = tmpData;
            }
        }
    }

    private void correctDegree() {
        int degreeRemainder = Math.abs(unitTotalMove % unit);
        if (degreeRemainder != 0) {
            if (degreeRemainder >= unit / 2.0F)
                correctScroll(degreeRemainder - unit, unit - degreeRemainder);
            else correctScroll(degreeRemainder, -degreeRemainder);
            view.invalidate();
        }
    }

    private void correctScroll(int endBack, int endForward) {
        if (unitTotalMove < 0) direction.startScroll(scroller, unitTotalMove, endBack);
        else direction.startScroll(scroller, unitTotalMove, endForward);
    }
}