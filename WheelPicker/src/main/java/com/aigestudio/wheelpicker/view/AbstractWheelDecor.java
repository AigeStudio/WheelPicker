package com.aigestudio.wheelpicker.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * 抽象滚轮装饰物
 * 滚轮装饰物功能将在下一个版本中启用
 * Abstract of WheelView's decoration
 * This function will be available in next version
 *
 * @author AigeStudio 2015-12-03
 *         类初始化
 * @author AigeStudio 2015-12-08
 *         修改类限定符
 *         新增方法{@link #drawDecor(Canvas, Rect, Paint)}
 * @version 1.0.0 beta
 */
public abstract class AbstractWheelDecor {
    public abstract void drawDecor(Canvas canvas, Rect rect, Paint paint);
}