package com.aigestudio.wheelpicker.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aigestudio.wheelpicker.view.WheelPicker;
import com.aigestudio.wheelpicker.widget.WheelConstellationPicker;
import com.aigestudio.wheelpicker.widget.WheelDatePicker;
import com.aigestudio.wheelpicker.widget.WheelDayPicker;
import com.aigestudio.wheelpicker.widget.WheelMonthPicker;
import com.aigestudio.wheelpicker.widget.WheelYearPicker;
import com.aigestudio.wheelpicker.widget.WheelZodiacPicker;

/**
 * @author AigeStudio 2015-12-06
 */
public class MainActivity extends Activity implements View.OnClickListener {
    private WheelPicker picker;
    private MainDialog dialog;
    private Button btnObtain;

    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

        picker = (WheelPicker) findViewById(R.id.main_wheel_picker);
        picker.setOnWheelChangeListener(new WheelPicker.SimpleWheelChangeListener() {
            @Override
            public void onWheelScrollStateChanged(int state) {
                if (state == WheelPicker.SCROLL_STATE_IDLE) {
                    btnObtain.setEnabled(true);
                } else {
                    btnObtain.setEnabled(false);
                }
                Log.d("AigeStudio", "state:" + state);
            }

            @Override
            public void onWheelSelected(int index, String data) {
                MainActivity.this.data = data;
            }
        });
        findViewById(R.id.btn_straight).setOnClickListener(this);
        btnObtain = (Button) findViewById(R.id.btn_obtain);
        btnObtain.setOnClickListener(this);
        findViewById(R.id.btn_curved).setOnClickListener(this);

        findViewById(R.id.main_btn_date).setOnClickListener(this);
        findViewById(R.id.main_btn_year).setOnClickListener(this);
        findViewById(R.id.main_btn_month).setOnClickListener(this);
        findViewById(R.id.main_btn_day).setOnClickListener(this);
        findViewById(R.id.main_btn_zodiac).setOnClickListener(this);
        findViewById(R.id.main_btn_constellation).setOnClickListener(this);
        findViewById(R.id.main_btn_province).setOnClickListener(this);
        findViewById(R.id.main_btn_city).setOnClickListener(this);
        findViewById(R.id.main_btn_districts).setOnClickListener(this);
        findViewById(R.id.main_btn_hour).setOnClickListener(this);
        findViewById(R.id.main_btn_time).setOnClickListener(this);
        findViewById(R.id.main_btn_sex).setOnClickListener(this);

        dialog = new MainDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_straight:
                picker.setStyle(WheelPicker.STRAIGHT);
                break;
            case R.id.btn_obtain:
                Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_curved:
                picker.setStyle(WheelPicker.CURVED);
                break;
            case R.id.main_btn_date:
                WheelDatePicker wheelDatePicker = new WheelDatePicker(this);
                wheelDatePicker.setBackgroundColor(0xFFF0DF98);
                wheelDatePicker.setTextColor(0xFF3F96C3);
                wheelDatePicker.setLabelColor(0xFF94A0B6);
                dialog.setContentView(wheelDatePicker);
                dialog.show();
                break;
            case R.id.main_btn_year:
                WheelYearPicker wheelYearPicker = new WheelYearPicker(this);
                wheelYearPicker.setBackgroundColor(0xFFF3F0E6);
                wheelYearPicker.setTextColor(0xFFAFB0CB);
                wheelYearPicker.setTextSize(getResources()
                        .getDimensionPixelSize(R.dimen.TextSizeLarge));
                wheelYearPicker.setItemSpace(getResources()
                        .getDimensionPixelOffset(R.dimen.ItemSpaceLarge));
                wheelYearPicker.setItemCount(11);
                dialog.setContentView(wheelYearPicker);
                dialog.show();
                break;
            case R.id.main_btn_month:
                dialog.setContentView(new WheelMonthPicker(this));
                dialog.show();
                break;
            case R.id.main_btn_day:
                dialog.setContentView(new WheelDayPicker(this));
                dialog.show();
                break;
            case R.id.main_btn_zodiac:
                WheelZodiacPicker wheelZodiacPicker = new WheelZodiacPicker(this);
                wheelZodiacPicker.setBackgroundColor(0xFFF7B983);
                wheelZodiacPicker.setTextColor(0xFF7774B7);
                dialog.setContentView(wheelZodiacPicker);
                dialog.show();
                break;
            case R.id.main_btn_constellation:
                WheelConstellationPicker wheelConstellationPicker = new WheelConstellationPicker(this);
                wheelConstellationPicker.setBackgroundColor(0xFFE4DDCC);
                wheelConstellationPicker.setItemSpace(getResources()
                        .getDimensionPixelOffset(R.dimen.ItemSpaceLarge));
                wheelConstellationPicker.setTextColor(0xFF47474A);
                dialog.setContentView(wheelConstellationPicker);
                dialog.show();
                break;
            case R.id.main_btn_province:
                dialog.setContentView(new WheelDatePicker(this));
                dialog.show();
                break;
            case R.id.main_btn_city:
                dialog.setContentView(new WheelDatePicker(this));
                dialog.show();
                break;
            case R.id.main_btn_districts:
                dialog.setContentView(new WheelDatePicker(this));
                dialog.show();
                break;
            case R.id.main_btn_hour:
                dialog.setContentView(new WheelDatePicker(this));
                dialog.show();
                break;
            case R.id.main_btn_time:
                dialog.setContentView(new WheelDatePicker(this));
                dialog.show();
                break;
            case R.id.main_btn_sex:
                dialog.setContentView(new WheelDatePicker(this));
                dialog.show();
                break;
        }
    }
}