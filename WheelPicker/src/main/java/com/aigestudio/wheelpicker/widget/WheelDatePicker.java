package com.aigestudio.wheelpicker.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.aigestudio.wheelpicker.R;
import com.aigestudio.wheelpicker.view.AbstractWheelDecor;
import com.aigestudio.wheelpicker.view.IWheelPicker;
import com.aigestudio.wheelpicker.view.WheelPicker;

import java.util.List;

/**
 * 基于WheelPicker的日期选择控件
 * DatePicker base on WheelPicker
 *
 * @author AigeStudio 2015-12-03
 * @author AigeStudio 2015-12-08
 *         Init初始化
 * @version 1.0.0 beta
 */
public class WheelDatePicker extends LinearLayout implements IWheelPicker {
    protected WheelYearPicker pickerYear;
    protected WheelMonthPicker pickerMonth;
    protected WheelDayPicker pickerDay;

    protected final Rect rectText = new Rect();

    protected String year, month, day;
    protected int labelColor = 0xFF000000;
    protected int stateYear, stateMonth, stateDay;

    public WheelDatePicker(Context context) {
        this(context, null);
    }

    public WheelDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);

        int padding = getResources().getDimensionPixelSize(R.dimen.WheelPadding1x);
        int padding2x = padding * 2;

        LayoutParams llParams = new LayoutParams(-2, -2);

        pickerYear = new WheelYearPicker(context);
        pickerMonth = new WheelMonthPicker(context);
        pickerDay = new WheelDayPicker(context);
        pickerYear.setPadding(padding2x, padding, padding2x, padding);
        pickerMonth.setPadding(padding2x, padding, padding2x, padding);
        pickerDay.setPadding(padding2x, padding, padding2x, padding);
        addLabel(pickerYear, "年");
        addLabel(pickerMonth, "月");
        addLabel(pickerDay, "日");

        addView(pickerYear, llParams);
        addView(pickerMonth, llParams);
        addView(pickerDay, llParams);
    }

    private void addLabel(WheelPicker picker, final String label) {
        picker.setCurrentItemForegroundDecor(true, new AbstractWheelDecor() {
            @Override
            public void drawDecor(Canvas canvas, Rect rect, Paint paint) {
                paint.setColor(labelColor);
                paint.setTextSize(getResources()
                        .getDimensionPixelSize(R.dimen.WheelPadding1x) * 1.5F);
                paint.getTextBounds(label, 0, 1, rectText);
                int width = rectText.width();
                canvas.drawText(label, rect.right - width / 2,
                        rect.centerY() - (paint.ascent() + paint.descent()) / 2.0F, paint);
            }
        });
    }

    public void setLabelColor(int labelColor) {
        this.labelColor = labelColor;
        invalidate();
    }

    @Override
    public void setStyle(int style) {
        pickerYear.setStyle(style);
        pickerMonth.setStyle(style);
        pickerDay.setStyle(style);
    }

    @Override
    public void setData(List<String> data) {
        throw new RuntimeException("Set data will not allow here!");
    }

    public void setCurrentDate(int year, int month, int day) {
        pickerYear.setCurrentYear(year);
        pickerMonth.setCurrentMonth(month);
        pickerDay.setCurrentYearAndMonth(year, month);
        pickerDay.setCurrentDay(day);
    }

    @Override
    public void setTextColor(int color) {
        pickerYear.setTextColor(color);
        pickerMonth.setTextColor(color);
        pickerDay.setTextColor(color);
    }

    @Override
    public void setItemIndex(int index) {
        pickerYear.setItemIndex(index);
        pickerMonth.setItemIndex(index);
        pickerDay.setItemIndex(index);
    }

    @Override
    public void setTextSize(int size) {
        pickerYear.setTextSize(size);
        pickerMonth.setTextSize(size);
        pickerDay.setTextSize(size);
    }

    @Override
    public void setOnWheelChangeListener(final WheelPicker.OnWheelChangeListener listener) {
        pickerYear.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolling() {
                listener.onWheelScrolling();
            }

            @Override
            public void onWheelSelected(int index, String data) {
                year = data;
                if (isValidDate()) {
                    pickerDay.setCurrentYearAndMonth(Integer.valueOf(year), Integer.valueOf(month));
                    listener.onWheelSelected(-1, year + "-" + month + "-" + day);
                }
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
                stateYear = state;
                checkState(listener);
            }
        });
        pickerMonth.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolling() {
                listener.onWheelScrolling();
            }

            @Override
            public void onWheelSelected(int index, String data) {
                month = data;
                if (isValidDate()) {
                    pickerDay.setCurrentYearAndMonth(Integer.valueOf(year), Integer.valueOf(month));
                    listener.onWheelSelected(-1, year + "-" + month + "-" + day);
                }
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
                stateMonth = state;
                checkState(listener);
            }
        });
        pickerDay.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolling() {
                listener.onWheelScrolling();
            }

            @Override
            public void onWheelSelected(int index, String data) {
                day = data;
                if (isValidDate()) {
                    listener.onWheelSelected(-1, year + "-" + month + "-" + day);
                }
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
                stateDay = state;
                checkState(listener);
            }
        });
    }

    private void checkState(WheelPicker.OnWheelChangeListener listener) {
        if (stateYear == WheelPicker.SCROLL_STATE_IDLE &&
                stateMonth == WheelPicker.SCROLL_STATE_IDLE &&
                stateDay == WheelPicker.SCROLL_STATE_IDLE) {
            listener.onWheelScrollStateChanged(WheelPicker.SCROLL_STATE_IDLE);
        }
        if (stateYear == WheelPicker.SCROLL_STATE_SCROLLING ||
                stateMonth == WheelPicker.SCROLL_STATE_SCROLLING ||
                stateDay == WheelPicker.SCROLL_STATE_SCROLLING) {
            listener.onWheelScrollStateChanged(WheelPicker.SCROLL_STATE_SCROLLING);
        }
        if (stateYear + stateMonth + stateDay == 1) {
            listener.onWheelScrollStateChanged(WheelPicker.SCROLL_STATE_DRAGGING);
        }
    }

    private boolean isValidDate() {
        return !TextUtils.isEmpty(year) && !TextUtils.isEmpty(month) && !TextUtils.isEmpty(day);
    }

    @Override
    public void setCurrentItemBackgroundDecor(boolean ignorePadding, AbstractWheelDecor decor) {
        setDecorBackgroundYear(ignorePadding, decor);
        setDecorBackgroundMonth(ignorePadding, decor);
        setDecorBackgroundDay(ignorePadding, decor);
    }

    @Override
    public void setCurrentItemForegroundDecor(boolean ignorePadding, AbstractWheelDecor decor) {
        setDecorForegroundYear(ignorePadding, decor);
        setDecorForegroundMonth(ignorePadding, decor);
        setDecorForegroundDay(ignorePadding, decor);
    }

    public void setDecorForegroundYear(boolean ignorePadding, AbstractWheelDecor decor) {
        pickerYear.setCurrentItemForegroundDecor(ignorePadding, decor);
    }

    public void setDecorForegroundMonth(boolean ignorePadding, AbstractWheelDecor decor) {
        pickerMonth.setCurrentItemForegroundDecor(ignorePadding, decor);
    }

    public void setDecorForegroundDay(boolean ignorePadding, AbstractWheelDecor decor) {
        pickerDay.setCurrentItemForegroundDecor(ignorePadding, decor);
    }

    public void setDecorBackgroundYear(boolean ignorePadding, AbstractWheelDecor decor) {
        pickerYear.setCurrentItemBackgroundDecor(ignorePadding, decor);
    }

    public void setDecorBackgroundMonth(boolean ignorePadding, AbstractWheelDecor decor) {
        pickerMonth.setCurrentItemBackgroundDecor(ignorePadding, decor);
    }

    public void setDecorBackgroundDay(boolean ignorePadding, AbstractWheelDecor decor) {
        pickerDay.setCurrentItemBackgroundDecor(ignorePadding, decor);
    }

    @Override
    public void setItemSpace(int space) {
        pickerYear.setItemSpace(space);
        pickerMonth.setItemSpace(space);
        pickerDay.setItemSpace(space);
    }

    @Override
    public void setItemCount(int count) {
        pickerYear.setItemCount(count);
        pickerMonth.setItemCount(count);
        pickerDay.setItemCount(count);
    }
}