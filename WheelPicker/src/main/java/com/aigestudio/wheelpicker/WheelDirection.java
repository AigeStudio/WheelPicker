package com.aigestudio.wheelpicker;

/**
 * Created by Administrator on 2016/6/20.
 */
abstract class WheelDirection {
    protected final WheelPicker mPicker;

    public WheelDirection(WheelPicker picker) {
        mPicker = picker;
    }

    static WheelDirection getWheelDirection(WheelPicker picker) {
//        switch (picker.getDirection()) {
//            case WheelPicker.DIR_HOR:
//                return new WheelHorImpl(picker);
//            case WheelPicker.DIR_VER:
//                return new WheelVerImpl(picker);
//        }
        return null;
    }
}