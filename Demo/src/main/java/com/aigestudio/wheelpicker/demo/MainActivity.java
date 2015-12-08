package com.aigestudio.wheelpicker.demo;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;

import com.aigestudio.wheelpicker.view.AbstractWheelDecor;
import com.aigestudio.wheelpicker.view.WheelPicker;

/**
 * @author AigeStudio 2015-12-06
 */
public class MainActivity extends Activity {
    private WheelPicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

//        List<String> data = new ArrayList<>();
//        for (int i = 0; i < 11; i++) {
//            data.add("AigeStudio");
//        }
        picker = (WheelPicker) findViewById(R.id.main_wp);
        picker.setStyle(WheelPicker.Style.CURVED);
        picker.setCurrentItemBackgroundDecor(false, new AbstractWheelDecor() {
            @Override
            public void drawDecor(Canvas canvas, Paint paint) {
                canvas.drawColor(0x33ffffff);
            }
        });
//        picker.setData(data);
//        picker.setTextColor(0xFF986325);
//        picker.setItemIndex(1);
//        picker.setTextSize(30);
    }
}