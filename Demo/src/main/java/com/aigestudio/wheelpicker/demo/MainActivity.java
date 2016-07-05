package com.aigestudio.wheelpicker.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.aigestudio.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author AigeStudio 2015-12-06
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

        List<String> data = new ArrayList<>();
        for (int i = 1000; i < 3000; i++) {
            data.add("00000000");
        }
        final WheelPicker picker = (WheelPicker) findViewById(R.id.main_wheel);
        picker.setDebug(true);
        picker.setData(data);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                picker.setData(Arrays.asList(getResources().getStringArray(R.array.WheelArrayWeek)));
//                picker.setItemTextSize(64);
//                picker.setIndicatorSize(128);
//                picker.setIndicatorColor(0xFF3333EE);
//                picker.setIndicator(true);
//                picker.setCurtain(true);
//                picker.setCyclic(true);
//                picker.setCurrentItem(3);
//                picker.setIndicator(true);
//                picker.setCurtainColor(0x88EE3333);
//                picker.setAtmospheric(true);
//                picker.setCurrentItemTextColor(0xFF3333EE);
//                picker.setPerspective(true);
            }
        }, 3000);
    }
}