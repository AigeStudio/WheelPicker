package com.aigestudio.wheelpicker.cross;

/**
 * Created by Administrator on 2016/6/20.
 */
abstract class WheelDirection {
    protected final WheelCrossPicker mPicker;

    public WheelDirection(WheelCrossPicker picker) {
        mPicker = picker;
    }

    abstract int computeWidth();

    abstract int computeHeight();
}
