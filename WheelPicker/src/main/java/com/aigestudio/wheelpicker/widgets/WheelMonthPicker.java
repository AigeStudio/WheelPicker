package com.aigestudio.wheelpicker.widgets;

import android.content.Context;
import android.util.AttributeSet;

import com.aigestudio.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WheelMonthPicker extends WheelPicker implements IWheelMonthPicker {
    private static final List<Integer> MONTHS = new ArrayList<>();

    static {
        for (int i = 1; i <= 12; i++)
            MONTHS.add(i);
    }

    private int mSelectedMonth;

    public WheelMonthPicker(Context context) {
        this(context, null);
    }

    public WheelMonthPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        super.setData(MONTHS);

        Calendar calendar = Calendar.getInstance();
        mSelectedMonth = calendar.get(Calendar.MONTH);

        updateSelectedMonth();
    }

    private void updateSelectedMonth() {
        if (mSelectedMonth < 1 || mSelectedMonth > 12)
            throw new ArrayIndexOutOfBoundsException("Selected year of WheelYearPicker must" +
                    "greater than or equal to 1 and less than or equal to 12");
        int position = mSelectedMonth - 1;
        setSelectedItemPosition(position);
    }

    @Override
    public void setData(List data) {
        throw new UnsupportedOperationException("You can't set a data source for" +
                "WheelMonthPicker");
    }

    @Override
    public int getSelectedMonth() {
        return mSelectedMonth;
    }

    @Override
    public void setSelectedMonth(int month) {
        mSelectedMonth = month;
        updateSelectedMonth();
    }

    @Override
    public int getCurrentMonth() {
        return MONTHS.get(getCurrentItemPosition());
    }
}