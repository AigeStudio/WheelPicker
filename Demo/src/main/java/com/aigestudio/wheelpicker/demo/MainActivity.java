package com.aigestudio.wheelpicker.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.aigestudio.wheelpicker.WheelPicker;

/**
 * @author AigeStudio 2015-12-06
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

        final WheelPicker picker = (WheelPicker) findViewById(R.id.main_wheel);
        picker.setDebug(true);
        picker.setCyclic(true);
        picker.setCurrentItem(3);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                picker.setHasSameWidth(true);
            }
        }, 3000);
    }
}