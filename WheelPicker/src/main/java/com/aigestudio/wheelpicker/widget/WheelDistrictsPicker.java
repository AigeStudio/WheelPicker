package com.aigestudio.wheelpicker.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.aigestudio.wheelpicker.view.WheelPicker;

/**
 * 基于WheelPicker的区县选择控件
 * 该控件会在下一个版本中启用
 * Districts picker base on WheelPicker
 * Available in next version
 *
 * @author AigeStudio 2015-12-08
 * @version 1.0.0 preview
 */
class WheelDistrictsPicker extends WheelPicker{
    public WheelDistrictsPicker(Context context) {
        super(context);
    }

    public WheelDistrictsPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WheelDistrictsPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}