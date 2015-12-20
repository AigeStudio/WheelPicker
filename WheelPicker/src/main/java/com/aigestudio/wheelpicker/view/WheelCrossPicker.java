package com.aigestudio.wheelpicker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.aigestudio.wheelpicker.core.AbstractWheelDecor;
import com.aigestudio.wheelpicker.core.AbstractWheelPicker;

import java.util.HashMap;
import java.util.List;

/**
 * @author AigeStudio 2015-12-13
 */
public abstract class WheelCrossPicker extends AbstractWheelPicker
        implements IWheelCrossPicker, Runnable {
    private static final int TIME_REFRESH = 16;

    /**
     * 滚轮方向常量值
     * Constant of WheelView's direction
     */
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    protected ICrossOrientation mOrientation;
    protected Rect rectCurDecor;
    protected Rect rectCurItem;
    protected Rect rectLast, rectNext;

    protected int unit;
    protected int unitDeltaTotal;
    protected int unitDeltaMin, unitDeltaMax;
    protected int unitDisplayMin, unitDisplayMax;

    public WheelCrossPicker(Context context) {
        super(context);
    }

    public WheelCrossPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void instantiation() {
        super.instantiation();
        mOrientation = new CrossVerImpl();

        rectCurDecor = new Rect();
        rectCurItem = new Rect();

        rectLast = new Rect();
        rectNext = new Rect();
    }

    @Override
    public void setOrientation(int orientation) {
        mOrientation = orientation == HORIZONTAL ? new CrossHorImpl() : new CrossVerImpl();
        computeWheelSizes();
        requestLayout();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        mOrientation.computeCurItemRect(rectCurItem, itemSpace, w, h, maxTextWidth, maxTextHeight,
                wheelCenterX, wheelCenterY, getPaddingLeft(), getPaddingTop(), getPaddingRight(),
                getPaddingBottom());
        mOrientation.computeRectPadding(rectLast, rectNext, rectCurItem, getPaddingLeft(),
                getPaddingTop(), getPaddingRight(), getPaddingBottom());
        rectCurDecor.set(rectCurItem);
        if (!ignorePadding) {
            mOrientation.removePadding(rectCurDecor, getPaddingLeft(), getPaddingTop(),
                    getPaddingRight(), getPaddingBottom());
        }
    }

    @Override
    protected void drawBackground(Canvas canvas) {
//        mPaint.setColor(Color.RED);
//        rectCurItem.set(rectCurItem.left,rectCurItem.top,rectCurItem.right-10,rectCurItem.bottom);
//        canvas.drawRect(rectCurItem,mPaint);
    }

    @Override
    protected void drawForeground(Canvas canvas) {
        if (null != mWheelDecor) {
            canvas.save();
            canvas.clipRect(rectCurDecor);
            mWheelDecor.drawDecor(canvas, rectLast, rectNext, mPaint);
            canvas.restore();
        }
    }

    @Override
    protected void onTouchDown(MotionEvent event) {

    }

    @Override
    protected void onTouchMove(MotionEvent event) {
        onWheelScrollStateChanged(SCROLL_STATE_DRAGGING);
        onWheelScrolling(disTotalMoveX + diSingleMoveX, disTotalMoveY + diSingleMoveY);
        invalidate();
    }

    @Override
    protected void onTouchUp(MotionEvent event) {
        mOrientation.fling(mScroller, mTracker, unitDeltaTotal, unitDeltaMin, unitDeltaMax,
                unitDisplayMax);
        onWheelScrollStateChanged(SCROLL_STATE_SCROLLING);
        mHandler.post(this);
    }

    @Override
    public void run() {
        if (mScroller.isFinished()) {
            onWheelScrollStateChanged(SCROLL_STATE_IDLE);
            correctLocation();
            confirmData();
        }
        if (mScroller.computeScrollOffset()) {
            disTotalMoveX = mScroller.getCurrX();
            disTotalMoveY = mScroller.getCurrY();
            unitDeltaTotal = mOrientation.getUnitDeltaTotal(mScroller);
            onWheelScrolling(disTotalMoveX, disTotalMoveY);
            postInvalidate();
            mHandler.postDelayed(this, TIME_REFRESH);
        }
    }

    private void confirmData() {
        if (state != SCROLL_STATE_IDLE) {
            return;
        }
        int curIndex = itemIndex - unitDeltaTotal / unit;
        curIndex = Math.max(0, curIndex);
        curIndex = Math.min(data.size() - 1, curIndex);
        String curData = data.get(curIndex);
        if (!this.curData.equals(curData)) {
            this.curData = curData;
            onWheelSelected(curIndex, curData);
        }
    }

    private void correctLocation() {
        int remainder = Math.abs(unitDeltaTotal % unit);
        if (remainder != 0) {
            if (remainder >= unit / 2.0F) {
                correctScroll(remainder - unit, unit - remainder);
            } else {
                correctScroll(remainder, -remainder);
            }
            postInvalidate();
            mHandler.postDelayed(this, TIME_REFRESH);
        }
    }

    private void correctScroll(int endBack, int endForward) {
        if (unitDeltaTotal < 0) {
            mOrientation.startScroll(mScroller, unitDeltaTotal, endBack);
        } else {
            mOrientation.startScroll(mScroller, unitDeltaTotal, endForward);
        }
        onWheelScrollStateChanged(SCROLL_STATE_SCROLLING);
    }

    public void checkScrollState() {
        if (unitDeltaTotal > unitDeltaMax) {
            mOrientation.startScroll(mScroller, unitDeltaTotal, unitDeltaMax - unitDeltaTotal);
        }
        if (unitDeltaTotal < unitDeltaMin) {
            mOrientation.startScroll(mScroller, unitDeltaTotal, unitDeltaMin - unitDeltaTotal);
        }
        mHandler.post(this);
    }

    @Override
    public void setCurrentTextColor(int color) {
        super.setCurrentTextColor(color);
        invalidate(rectCurItem);
    }

    @Override
    public void setWheelDecor(boolean ignorePadding, AbstractWheelDecor decor) {
        super.setWheelDecor(ignorePadding, decor);
        invalidate(rectCurItem);
    }

    @Override
    public void setTextSize(int size) {
        super.setTextSize(size);
        clearCache();
    }

    @Override
    public void setItemSpace(int space) {
        super.setItemSpace(space);
        clearCache();
    }

    @Override
    public void setItemCount(int count) {
        super.setItemCount(count);
        clearCache();
    }

    @Override
    public void setData(List<String> data) {
        super.setData(data);
        clearCache();
    }

    @Override
    public void clearCache() {

    }
}