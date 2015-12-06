package com.aigestudio.wheelpicker.view;

import com.aigestudio.wheelpicker.view.WheelPicker.Style;
import com.aigestudio.wheelpicker.view.WheelPicker.Direction;

/**
 * 滚轮构建工厂
 * Factory to create WheelView's objects
 *
 * @author AigeStudio 2015-12-03
 * @version 1.0.0 preview
 */
final class WheelFactory {
    static AbstractWheelStyle createWheelStyle(Style style, WheelView view) {
        AbstractWheelStyle wheel = null;
        switch (style) {
            case STRAIGHT:
                wheel = new WheelStyleStraight(view);
                break;
            case CURVED:
                wheel = new WheelStyleCurved(view);
                break;
        }
        return wheel;
    }

    static IWheelDirection createWheelDirection(Direction direction) {
        IWheelDirection wheel = null;
        switch (direction) {
            case HORIZONTAL:
                wheel = new WheelDirectHor();
                break;
            case VERTICAL:
                wheel = new WheelDirectVer();
                break;
        }
        return wheel;
    }
}