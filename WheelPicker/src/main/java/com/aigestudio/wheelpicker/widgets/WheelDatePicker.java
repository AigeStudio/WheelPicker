package com.aigestudio.wheelpicker.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aigestudio.wheelpicker.R;
import com.aigestudio.wheelpicker.WheelPicker;

public class WheelDatePicker extends LinearLayout {
    private WheelPicker mPickerYear, mPickerMonth, mPickerDay;
    private TextView mTVYear, mTVMonth, mTVDay;

    public WheelDatePicker(Context context) {
        this(context, null);
    }

    public WheelDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);

        LayoutInflater.from(context).inflate(R.layout.view_wheel_date_picker, this);

        mPickerYear = (WheelPicker) findViewById(R.id.wheel_date_picker_year);
        mPickerMonth = (WheelPicker) findViewById(R.id.wheel_date_picker_month);
        mPickerDay = (WheelPicker) findViewById(R.id.wheel_date_picker_day);

        mTVYear = (TextView) findViewById(R.id.wheel_date_picker_year_tv);
        mTVMonth = (TextView) findViewById(R.id.wheel_date_picker_month_tv);
        mTVDay = (TextView) findViewById(R.id.wheel_date_picker_day_tv);
    }
}