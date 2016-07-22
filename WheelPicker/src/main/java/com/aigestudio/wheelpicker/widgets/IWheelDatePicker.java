package com.aigestudio.wheelpicker.widgets;

import android.widget.TextView;

public interface IWheelDatePicker {
    void setOnDateSelectedListener(WheelDatePicker.OnDateSelectedListener listener);

    int getItemAlignYear();

    void setItemAlignYear(int align);

    int getItemAlignMonth();

    void setItemAlignMonth(int align);

    int getItemAlignDay();

    void setItemAlignDay(int align);

    WheelYearPicker getWheelYearPicker();

    WheelMonthPicker getWheelMonthPicker();

    WheelDayPicker getWheelDayPicker();

    TextView getTextViewYear();

    TextView getTextViewMonth();

    TextView getTextViewDay();
}