package com.aigestudio.wheelpicker.demo;

import android.app.Activity;
import android.graphics.Typeface;
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

//        final List<String> data = new ArrayList<>();
//        for (int i = 1000; i < 3000; i++) {
//            data.add("00000000");
//        }
//        final WheelPicker picker = (WheelPicker) findViewById(R.id.main_wheel_left);
//        picker.setDebug(true);

//        final Typeface tf = Typeface.createFromAsset(getAssets(), "yyg.ttf");
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                picker.setItemAlign(WheelPicker.ALIGN_RIGHT);
//                picker.setCurved(true);
//                picker.setTypeface(tf);
//                picker.setData(data);
//                picker.setData(Arrays.asList(getResources().getStringArray(R.array.WheelArrayWeek)));
//                picker.setItemTextSize(64);
//                picker.setIndicatorSize(4);
//                picker.setIndicatorColor(0xFF3333EE);
//                picker.setIndicator(true);
//                picker.setCurtain(true);
//                picker.setCyclic(true);
//                picker.setCurrentItem(3);
//                picker.setCurtainColor(0x88EE3333);
//                picker.setAtmospheric(true);
//                picker.setCurrentItemTextColor(0xFF3333EE);
//            }
//        }, 3000);
    }
}