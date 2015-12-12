package com.aigestudio.wheelpicker.view;

import android.content.Context;
import android.util.AttributeSet;

import java.util.List;

/**
 * 滚轮对外提供实际功能的类
 * Function interface implement of WheelView
 *
 * @author AigeStudio 2015-12-03
 * @author AigeStudio 2015-12-08
 *         增加接口方法
 *         移除枚举类型改由int替代常量定义
 * @version 1.0.0 beta
 */
public class WheelPicker extends WheelView implements IWheelPicker {
    /**
     * 滚轮方向枚举类
     * 目前为止WheelPicker仅仅支持垂直方向的滚轮滚动
     * 水平方向的滚轮滚动将在以后的版本中支持
     * Enum of WheelView's direction
     * So far, WheelPicker only support vertical scrolling
     * We will support horizontal scrolling in a later version
     */
    static final int HORIZONTAL = 0, VERTICAL = 1;

    /**
     * 滚轮样式枚举类
     * 目前为止WheelPicker仅仅支持直向和弯曲两种样式
     * Enum of WheelView's direction
     * So far, WheelPicker only support straight and curved style
     */
    public static final int STRAIGHT = 0, CURVED = 1;

    /**
     * WheelView滚动状态标识值
     * SCROLL_STATE_IDLE标识WheelView处于静止时的状态
     * SCROLL_STATE_DRAGGING标识WheelView处于拖拽时的状态
     * SCROLL_STATE_SCROLLING标识WheelView处于滚动时的状态
     * Flag of scroll state
     * SCROLL_STATE_IDLE Set when WheelView is idle
     * SCROLL_STATE_DRAGGING Set when WheelView is dragging
     * SCROLL_STATE_SCROLLING Set when WheelView is scrolling
     */
    public static final int
            SCROLL_STATE_IDLE = 0,
            SCROLL_STATE_DRAGGING = 1,
            SCROLL_STATE_SCROLLING = 2;

    public interface OnWheelChangeListener {
        void onWheelScrolling();

        void onWheelSelected(int index, String data);

        void onWheelScrollStateChanged(int state);
    }

    public static class SimpleWheelChangeListener implements OnWheelChangeListener {
        @Override
        public void onWheelScrolling() {

        }

        @Override
        public void onWheelSelected(int index, String data) {

        }

        @Override
        public void onWheelScrollStateChanged(int state) {

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
    public void setStyle(int style) {
        if (this.style != style) {
            this.style = style;
            wheel = WheelFactory.createWheelStyle(style, this);
            requestLayout();
        }
    }

    @Override
    public void setData(List<String> data) {
        this.data = data;
        wheel.computeTextSize();
        wheel.computeUnitArea();
        wheel.computeWheel();
        wheel.checkScrollState();
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
        wheel.computeUnitArea();
        requestLayout();
    }

    @Override
    public void setTextSize(int size) {
        wheel.paint.setTextSize(size);
        wheel.computeTextSize();
        wheel.computeWheel();
        requestLayout();
    }

    @Override
    public void setOnWheelChangeListener(OnWheelChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void setCurrentItemBackgroundDecor(boolean ignorePadding, AbstractWheelDecor decor) {
        decorBg = decor;
        if (this.ignorePaddingDecorBG != ignorePadding) {
            this.ignorePaddingDecorBG = ignorePadding;
            wheel.computeCurrentDecorArea();
        }
        invalidate();
    }

    @Override
    public void setCurrentItemForegroundDecor(boolean ignorePadding, AbstractWheelDecor decor) {
        decorFg = decor;
        if (this.ignorePaddingDecorFG != ignorePadding) {
            this.ignorePaddingDecorFG = ignorePadding;
            wheel.computeCurrentDecorArea();
        }
        invalidate();
    }

    @Override
    public void setItemSpace(int space) {
        itemSpace = space;
        wheel.computeWheel();
        wheel.computeCurrentDecorArea();
        requestLayout();
    }

    @Override
    public void setItemCount(int count) {
        itemCount = count;
        wheel.computeWheel();
        wheel.computeUnitArea();
        requestLayout();
    }
}