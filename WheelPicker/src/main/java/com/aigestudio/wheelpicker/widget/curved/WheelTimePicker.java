package com.aigestudio.wheelpicker.widget.curved;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.aigestudio.wheelpicker.R;
import com.aigestudio.wheelpicker.core.AbstractWheelDecor;
import com.aigestudio.wheelpicker.core.AbstractWheelPicker;
import com.aigestudio.wheelpicker.core.IWheelPicker;
import com.aigestudio.wheelpicker.view.WheelCrossPicker;
import com.aigestudio.wheelpicker.widget.IDigital;

import java.util.List;

/**
 * 基于WheelPicker的时间选择控件
 * TimePicker base on WheelPicker
 *
 * @author AigeStudio 2015-12-12
 * @version 1.0.0 beta
 */
public class WheelTimePicker extends LinearLayout implements IWheelPicker, IDigital {
    protected WheelHourPicker pickerHour;
    protected WheelMinutePicker pickerMinute;

    protected AbstractWheelPicker.OnWheelChangeListener listener;

    protected String hour, minute;
    protected int labelColor = 0xFF000000;
    protected int stateHour, stateMinute;

    protected float labelTextSize;

    public WheelTimePicker(Context context) {
        super(context);
        init();
    }

    public WheelTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);

        int padding = getResources().getDimensionPixelSize(R.dimen.WheelPadding);
        int padding2x = padding * 2;

        labelTextSize = padding;

        LayoutParams llParams = new LayoutParams(-2, -2);

        pickerHour = new WheelHourPicker(getContext());
        pickerMinute = new WheelMinutePicker(getContext());
        pickerHour.setPadding(0, padding, padding2x, padding);
        pickerMinute.setPadding(0, padding, padding2x, padding);
        addLabel(pickerHour, "时");
        addLabel(pickerMinute, "分");

        addView(pickerHour, llParams);
        addView(pickerMinute, llParams);

        initListener(pickerHour, 0);
        initListener(pickerMinute, 1);
    }

    private void addLabel(WheelCrossPicker picker, final String label) {
        picker.setWheelDecor(true, new AbstractWheelDecor() {
            @Override
            public void drawDecor(Canvas canvas, Rect rectLast, Rect rectNext, Paint paint) {
                paint.setColor(labelColor);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(labelTextSize * 1.5F);
                canvas.drawText(label, rectNext.centerX(),
                        rectNext.centerY() - (paint.ascent() + paint.descent()) / 2.0F, paint);
            }
        });
    }

    private void initListener(final WheelCrossPicker picker, final int type) {
        picker.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolling(float deltaX, float deltaY) {
                if (null != listener) listener.onWheelScrolling(deltaX, deltaY);
            }

            @Override
            public void onWheelSelected(int index, String data) {
                if (type == 0) hour = data;
                if (type == 1) minute = data;
                if (isValidDate()) {
                    if (null != listener)
                        listener.onWheelSelected(-1, hour + ":" + minute);
                }
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
                if (type == 0) stateHour = state;
                if (type == 1) stateMinute = state;
                if (null != listener) checkState(listener);
            }
        });
    }

    public void setLabelColor(int labelColor) {
        this.labelColor = labelColor;
        invalidate();
    }

    public void setLabelTextSize(float labelTextSize) {
        this.labelTextSize = labelTextSize;
        invalidate();
    }

    @Override
    public void setData(List<String> data) {
        throw new RuntimeException("Set data will not allow here!");
    }

    public void setCurrentTime(int hour, int minute) {
        pickerHour.setCurrentHour(hour);
        pickerMinute.setCurrentMinute(minute);
    }

    @Override
    public void setOnWheelChangeListener(AbstractWheelPicker.OnWheelChangeListener listener) {
        this.listener = listener;
    }

    private void checkState(AbstractWheelPicker.OnWheelChangeListener listener) {
        if (stateHour == AbstractWheelPicker.SCROLL_STATE_IDLE &&
                stateMinute == AbstractWheelPicker.SCROLL_STATE_IDLE) {
            listener.onWheelScrollStateChanged(AbstractWheelPicker.SCROLL_STATE_IDLE);
        }
        if (stateHour == AbstractWheelPicker.SCROLL_STATE_SCROLLING ||
                stateMinute == AbstractWheelPicker.SCROLL_STATE_SCROLLING) {
            listener.onWheelScrollStateChanged(AbstractWheelPicker.SCROLL_STATE_SCROLLING);
        }
        if (stateHour + stateMinute == 1) {
            listener.onWheelScrollStateChanged(AbstractWheelPicker.SCROLL_STATE_DRAGGING);
        }
    }

    private boolean isValidDate() {
        return !TextUtils.isEmpty(hour) && !TextUtils.isEmpty(minute);
    }

    @Override
    public void setItemIndex(int index) {
        pickerHour.setItemIndex(index);
        pickerMinute.setItemIndex(index);
    }

    @Override
    public void setItemSpace(int space) {
        pickerHour.setItemSpace(space);
        pickerMinute.setItemSpace(space);
    }

    @Override
    public void setItemCount(int count) {
        pickerHour.setItemCount(count);
        pickerMinute.setItemCount(count);
    }

    @Override
    public void setTextColor(int color) {
        pickerHour.setTextColor(color);
        pickerMinute.setTextColor(color);
    }

    @Override
    public void setTextSize(int size) {
        pickerHour.setTextSize(size);
        pickerMinute.setTextSize(size);
    }

    @Override
    public void clearCache() {
        pickerHour.clearCache();
        pickerMinute.clearCache();
    }

    @Override
    public void setCurrentTextColor(int color) {
        pickerHour.setCurrentTextColor(color);
        pickerMinute.setCurrentTextColor(color);
    }

    @Override
    public void setWheelDecor(boolean ignorePadding, AbstractWheelDecor decor) {
        pickerHour.setWheelDecor(ignorePadding, decor);
        pickerMinute.setWheelDecor(ignorePadding, decor);
    }

    @Override
    public void setDigitType(int type) {
        pickerHour.setDigitType(type);
        pickerMinute.setDigitType(type);
    }
}