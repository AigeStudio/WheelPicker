package com.aigestudio.wheelpicker.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.aigestudio.wheelpicker.R;
import com.aigestudio.wheelpicker.view.WheelPicker;

import java.util.Arrays;
import java.util.List;

/**
 * 基于WheelPicker的生肖选择控件
 * ZodiacPicker base on WheelPicker
 *
 * @author AigeStudio 2015-12-03
 * @author AigeStudio 2015-12-08
 *         Init初始化
 * @version 1.0.0 beta
 */
public class WheelZodiacPicker extends WheelPicker {
    public WheelZodiacPicker(Context context) {
        this(context, null);
    }

    public WheelZodiacPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelZodiacPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setData(Arrays.asList(getResources().getStringArray(R.array.WheelArrayZodiac)));
    }

    @Override
    public void setData(List<String> data) {
        throw new RuntimeException("Set data will not allow here!");
    }
}