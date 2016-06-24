package com.aigestudio.wheelpicker.demo;

import android.app.Activity;
import android.os.Bundle;

import com.aigestudio.wheelpicker.WheelView;

/**
 * @author AigeStudio 2015-12-06
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

        final WheelView picker= (WheelView) findViewById(R.id.wheel);
        picker.addData("轰趴馆");
        picker.addData("台球");
        picker.addData("密室逃脱");
        picker.addData("卡丁车");
        picker.addData("桌游");
        picker.addData("真人CS");
        picker.addData("DIY");
        picker.setCenterItem(4);
    }
}