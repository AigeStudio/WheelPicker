package com.aigestudio.wheelpicker.view;

import android.content.Context;
import android.util.AttributeSet;

import java.util.List;

/**
 * 滚轮对外提供实际功能的类
 * Function interface implement of WheelView
 *
 * @author AigeStudio 2015-12-03
 * @version 1.0.0 preview
 */
public class WheelPicker extends WheelView implements IWheelPicker {
    /**
     * 滚轮方向枚举类
     * 目前为止WheelPicker仅仅支持垂直{@link #VERTICAL}方向的滚轮滚动
     * 水平{@link #HORIZONTAL}方向的滚轮滚动将在以后的版本中支持
     * Enum of WheelView's direction
     * So far, WheelPicker only support vertical{@link #VERTICAL} scrolling
     * We will support horizontal{@link #HORIZONTAL} scrolling in a later version
     *
     * @author AigeStudio 2015-12-03
     * @version 1.0.0 preview
     */
    public enum Direction {
        HORIZONTAL(0), VERTICAL(1);

        private int id;

        Direction(int id) {
            this.id = id;
        }

        static Direction fromID(int id) {
            for (Direction direction : values()) {
                if (direction.id == id) return direction;
            }
            throw new IllegalArgumentException("Can not resolve id " + id + " in Direction");
        }
    }

    /**
     * 滚轮样式枚举类
     * 目前为止WheelPicker仅仅支持直向{@link #STRAIGHT}和弯曲{@link #CURVED}两种样式
     * Enum of WheelView's direction
     * So far, WheelPicker only support straight{@link #STRAIGHT} and curved{@link #CURVED} style
     *
     * @author AigeStudio 2015-12-03
     * @version 1.0.0 preview
     */
    public enum Style {
        STRAIGHT(0), CURVED(1);

        private int id;

        Style(int id) {
            this.id = id;
        }

        static Style fromID(int id) {
            for (Style style : values()) {
                if (style.id == id) return style;
            }
            throw new IllegalArgumentException("Can not resolve id " + id + " in Style");
        }
    }

    public WheelPicker(Context context) {
        super(context);
    }

    public WheelPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WheelPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setStyle(Style style) {
        this.style = style;
        wheel = WheelFactory.createWheelStyle(style, this);
        requestLayout();
    }

    @Override
    public void setData(List<String> data) {
        this.data = data;
        wheel.computeTextSize();
        wheel.computeWheel();
        requestLayout();
    }

    @Override
    public void setTextColor(int color) {
        wheel.paint.setColor(color);
        invalidate();
    }

    @Override
    public void setItemIndex(int index) {
        this.itemIndex = index;
        invalidate();
    }

    @Override
    public void setTextSize(int size) {
        wheel.paint.setTextSize(size);
        wheel.computeTextSize();
        wheel.computeWheel();
        requestLayout();
    }

    @Override
    public String getCurrentItemText() {
        return wheel.textCurrentItem;
    }

    @Override
    public void setCurrentItemBackgroundDecor(boolean ignorePadding, AbstractWheelDecor decor) {
        decorBg = decor;
        if (this.ignorePadding != ignorePadding) {
            this.ignorePadding = ignorePadding;
            wheel.computeCurrentDecorArea();
        }
        invalidate();
    }

    @Override
    public void setCurrentItemForegroundDecor(boolean ignorePadding, AbstractWheelDecor decor) {
        decorFg = decor;
        if (this.ignorePadding != ignorePadding) {
            this.ignorePadding = ignorePadding;
            wheel.computeCurrentDecorArea();
        }
        invalidate();
    }
}