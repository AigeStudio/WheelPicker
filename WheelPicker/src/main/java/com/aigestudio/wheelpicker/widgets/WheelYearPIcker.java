package com.aigestudio.wheelpicker.widgets;

import android.content.Context;
import android.util.AttributeSet;

import com.aigestudio.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author AigeStudio
 * @since 2016-07-11
 */
public class WheelYearPicker extends WheelPicker implements IWheelYearPicker {
    private final Calendar mCalendar = Calendar.getInstance();
    private final List<Integer> mDataYears = new ArrayList<>();

    private int mYearStart = 1000, mYearEnd = 3000;
    private int mSelectedYear;

    public WheelYearPicker(Context context) {
        this(context, null);
    }

    public WheelYearPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMaximumWidthText("0000");

        mSelectedYear = mCalendar.get(Calendar.YEAR);

        updateYears();
        updateSelectedYear();
    }

    private void updateYears() {
        mDataYears.clear();
        for (int i = mYearStart; i <= mYearEnd; i++)
            mDataYears.add(i);
        super.setData(mDataYears);
    }

    private void updateSelectedYear() {
        if (mSelectedYear < mYearStart || mSelectedYear > mYearEnd)
            throw new ArrayIndexOutOfBoundsException("Selected year of WheelYearPicker" +
                    "must greater than or equal to " + mYearStart + " and less than or" +
                    "equal to " + mYearEnd);
        int position = mSelectedYear - mYearStart;
        setSelectedItemPosition(position);
    }

    @Override
    public void setData(List data) {
        throw new UnsupportedOperationException("You can't set a data source for" +
                "WheelYearPicker");
    }

    @Override
    public void setYearRange(int start, int end) {
        mYearStart = start;
        mYearEnd = end;
        updateYears();
    }

    @Override
    public int getYearStart() {
        return mYearStart;
    }

    @Override
    public void setYearStart(int start) {
        mYearStart = start;
        updateYears();
    }

    @Override
    public int getYearEnd() {
        return mYearEnd;
    }

    @Override
    public void setYearEnd(int end) {
        mYearEnd = end;
        updateYears();
    }

    @Override
    public int getSelectedYear() {
        return mSelectedYear;
    }

    @Override
    public void setSelectedYear(int year) {
        mSelectedYear = year;
        updateSelectedYear();
    }

    @Override
    public int getCurrentYear() {
        return mDataYears.get(getCurrentItemPosition());
    }
}