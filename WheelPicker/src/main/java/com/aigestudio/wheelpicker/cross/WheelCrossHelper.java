package com.aigestudio.wheelpicker.cross;

/**
 * 十字滚轮选择器帮助类
 * 用于根据滚动不同属性构造不同对象
 *
 * @author AigeStudio
 * @since 2016-06-20
 */
final class WheelCrossHelper {
    static WheelDirection getWheelCrossDirection(int direction) {
        switch (direction) {
            case WheelCrossPicker.ORI_HOR:
                return new WheelHorImpl();
            case WheelCrossPicker.ORI_VER:
                return new WheelVerImpl();
        }
        return null;
    }
}