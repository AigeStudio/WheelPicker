package com.aigestudio.wheelpicker.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author AigeStudio 2015-12-12
 * @author AigeStudio 2016-06-17
 *         更新项目结构
 * @version 1.1.0 beta
 */
public abstract class AbstractWheelPicker extends View implements IWheelPicker {
    // 滚轮选择器滚动状态标识值
    public static final int
            SCROLL_STATE_IDLE = 0,// 滚动停止时
            SCROLL_STATE_DRAGGING = 1,// 拖拽时
            SCROLL_STATE_SCROLLING = 2;// 滚动时

    public AbstractWheelPicker(Context context) {
        super(context);
    }

    public AbstractWheelPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}