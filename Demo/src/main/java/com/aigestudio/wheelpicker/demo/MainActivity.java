package com.aigestudio.wheelpicker.demo;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aigestudio.wheelpicker.core.AbstractWheelDecor;
import com.aigestudio.wheelpicker.core.AbstractWheelPicker;
import com.aigestudio.wheelpicker.view.WheelCrossPicker;
import com.aigestudio.wheelpicker.widget.curved.WheelDatePicker;
import com.aigestudio.wheelpicker.widget.curved.WheelDayPicker;
import com.aigestudio.wheelpicker.widget.curved.WheelHourPicker;
import com.aigestudio.wheelpicker.widget.curved.WheelMinutePicker;
import com.aigestudio.wheelpicker.widget.curved.WheelMonthPicker;
import com.aigestudio.wheelpicker.widget.curved.WheelTimePicker;
import com.aigestudio.wheelpicker.widget.curved.WheelYearPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AigeStudio 2015-12-06
 */
public class MainActivity extends Activity implements View.OnClickListener {
    private MainDialog dialog;
    private Button btnObtainStraight, btnObtainCurved;

    private String dataStraight, dataCurved;
    private int padding;
    private int textSize;
    private int itemSpace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

        padding = getResources().getDimensionPixelSize(R.dimen.WheelPadding);
        textSize = getResources().getDimensionPixelSize(R.dimen.TextSizeLarge);
        itemSpace = getResources().getDimensionPixelSize(R.dimen.ItemSpaceLarge);

        WheelCrossPicker straightPicker = (WheelCrossPicker) findViewById(R.id.main_wheel_straight);
        straightPicker.setItemIndex(2);
        straightPicker.setBackgroundColor(0xFFE5DEEB);
        straightPicker.setTextColor(0xFFA7A7DB);
        straightPicker.setCurrentTextColor(0xFF536D8A);
        straightPicker.setOnWheelChangeListener(new AbstractWheelPicker.SimpleWheelChangeListener() {
            @Override
            public void onWheelScrollStateChanged(int state) {
                if (state != AbstractWheelPicker.SCROLL_STATE_IDLE) {
                    btnObtainStraight.setEnabled(false);
                } else {
                    btnObtainStraight.setEnabled(true);
                }
            }

            @Override
            public void onWheelSelected(int index, String data) {
                dataStraight = data;
            }
        });
        WheelCrossPicker curvedPicker = (WheelCrossPicker) findViewById(R.id.main_wheel_curved);
        curvedPicker.setOnWheelChangeListener(new AbstractWheelPicker.SimpleWheelChangeListener() {
            @Override
            public void onWheelScrollStateChanged(int state) {
                if (state != AbstractWheelPicker.SCROLL_STATE_IDLE) {
                    btnObtainCurved.setEnabled(false);
                } else {
                    btnObtainCurved.setEnabled(true);
                }
            }

            @Override
            public void onWheelSelected(int index, String data) {
                dataCurved = data;
            }
        });

        btnObtainStraight = (Button) findViewById(R.id.main_obtain_straight_btn);
        btnObtainStraight.setOnClickListener(this);
        btnObtainCurved = (Button) findViewById(R.id.main_obtain_curved_btn);
        btnObtainCurved.setOnClickListener(this);

        findViewById(R.id.main_year_btn).setOnClickListener(this);
        findViewById(R.id.main_month_btn).setOnClickListener(this);
        findViewById(R.id.main_day_btn).setOnClickListener(this);
        findViewById(R.id.main_date_btn).setOnClickListener(this);
        findViewById(R.id.main_hour_btn).setOnClickListener(this);
        findViewById(R.id.main_minute_btn).setOnClickListener(this);
        findViewById(R.id.main_time_btn).setOnClickListener(this);

        dialog = new MainDialog(this);
    }

    @Override
    public void onClick(View v) {
        int padding5x = padding * 5;
        switch (v.getId()) {
            case R.id.main_obtain_straight_btn:
                Toast.makeText(this, dataStraight, Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_obtain_curved_btn:
                Toast.makeText(this, dataCurved, Toast.LENGTH_SHORT).show();
                break;
//            case R.id.btn_curved:
//                pickerHour.setStyle(WheelPicker.CURVED);
//                break;
            case R.id.main_year_btn:
                WheelYearPicker wheelYearPicker = new WheelYearPicker(this);
                wheelYearPicker.setPadding(padding5x, 0, padding5x, 0);
                wheelYearPicker.setTextSize(textSize);
                wheelYearPicker.setItemSpace(itemSpace);
                wheelYearPicker.setBackgroundColor(0xFFF0DF98);
                wheelYearPicker.setCurrentTextColor(0xFF0B456B);
                wheelYearPicker.setTextColor(0xFF3F96C3);
                wheelYearPicker.setYearRange(2000, 3000);
                wheelYearPicker.setCurrentYear(2100);
//                wheelDatePicker.setLabelColor(0xFF94A0B6);
                dialog.setContentView(wheelYearPicker);
                dialog.show();
                break;
            case R.id.main_month_btn:
                WheelMonthPicker wheelMonthPicker = new WheelMonthPicker(this);
                wheelMonthPicker.setPadding(padding5x, 0, padding5x, 0);
                wheelMonthPicker.setBackgroundColor(0xFFF3F0E6);
                wheelMonthPicker.setTextColor(0xFFAFB0CB);
                wheelMonthPicker.setTextSize(textSize);
                wheelMonthPicker.setItemSpace(itemSpace);
                wheelMonthPicker.setItemCount(11);
                wheelMonthPicker.setCurrentMonth(5);
                wheelMonthPicker.setCurrentTextColor(0xFF7787C5);
                dialog.setContentView(wheelMonthPicker);
                dialog.show();
                break;
            case R.id.main_day_btn:
                WheelDayPicker wheelDayPicker = new WheelDayPicker(this);
                wheelDayPicker.setPadding(padding5x, 0, padding5x, 0);
                wheelDayPicker.setBackgroundColor(0xFFDFF2D8);
                wheelDayPicker.setTextColor(0xFFC3DB51);
                wheelDayPicker.setCurrentTextColor(0xFF9BB120);
                wheelDayPicker.setTextSize(textSize);
                wheelDayPicker.setItemSpace(itemSpace);
                wheelDayPicker.setCurrentMonth(2);
                wheelDayPicker.setCurrentYear(2015);
                wheelDayPicker.setCurrentDay(28);
                wheelDayPicker.setItemCount(9);
                dialog.setContentView(wheelDayPicker);
                dialog.show();
                break;
            case R.id.main_date_btn:
                WheelDatePicker wheelDatePicker = new WheelDatePicker(this);
                wheelDatePicker.setPadding(padding, 0, padding, 0);
                wheelDatePicker.setBackgroundColor(0xFFF7B983);
                wheelDatePicker.setTextColor(0xFF7787C5);
                wheelDatePicker.setCurrentTextColor(0xFF7774B7);
                wheelDatePicker.setLabelColor(0xFF7774B7);
                wheelDatePicker.setTextSize(textSize);
                wheelDatePicker.setItemSpace(itemSpace);
                wheelDatePicker.setCurrentDate(2015, 12, 20);
                dialog.setContentView(wheelDatePicker);
                dialog.show();
                break;
            case R.id.main_hour_btn:
                WheelHourPicker wheelHourPicker = new WheelHourPicker(this);
                wheelHourPicker.setPadding(padding5x, 0, padding5x, 0);
                wheelHourPicker.setTextSize(textSize);
                wheelHourPicker.setDigitType(1);
                wheelHourPicker.setItemSpace(itemSpace);
                wheelHourPicker.setBackgroundColor(0xFFF0DF98);
                wheelHourPicker.setCurrentTextColor(0xFF0B456B);
                wheelHourPicker.setTextColor(0xFF3F96C3);
                dialog.setContentView(wheelHourPicker);
                dialog.show();
                break;
            case R.id.main_minute_btn:
                WheelMinutePicker wheelMinutePicker = new WheelMinutePicker(this);
                wheelMinutePicker.setPadding(padding5x, 0, padding5x, 0);
                wheelMinutePicker.setBackgroundColor(0xFFF3F0E6);
                wheelMinutePicker.setTextColor(0xFFAFB0CB);
                wheelMinutePicker.setDigitType(2);
                wheelMinutePicker.setCurrentTextColor(0xFF7787C5);
                wheelMinutePicker.setTextSize(textSize);
                wheelMinutePicker.setItemSpace(itemSpace);
                dialog.setContentView(wheelMinutePicker);
                dialog.show();
                break;
            case R.id.main_time_btn:
                WheelTimePicker wheelTimePicker = new WheelTimePicker(this);
                wheelTimePicker.setPadding(padding, 0, padding, 0);
                wheelTimePicker.setBackgroundColor(0xFFDFF2D8);
                wheelTimePicker.setTextColor(0xFFC3DB51);
                wheelTimePicker.setCurrentTextColor(0xFF9BB120);
                wheelTimePicker.setLabelColor(0xFF9BB120);
                wheelTimePicker.setTextSize(textSize);
                wheelTimePicker.setDigitType(2);
                wheelTimePicker.setItemSpace(itemSpace);
                dialog.setContentView(wheelTimePicker);
                dialog.show();
                break;
        }
    }
}