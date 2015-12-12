package com.aigestudio.wheelpicker.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.aigestudio.wheelpicker.view.WheelPicker;

/**
 * 基于WheelPicker的省份选择控件
 * 该控件会在下一个版本中启用
 * Provinces picker base on WheelPicker
 * Available in next version
 *
 * @author AigeStudio 2015-12-08
 * @version 1.0.0 preview
 */
class WheelProvincePicker extends WheelPicker{
    public WheelProvincePicker(Context context) {
        super(context);
    }

    public WheelProvincePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WheelProvincePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}