package com.aigestudio.wheelpicker.cross;

/**
 * Created by Administrator on 2016/6/20.
 */
class WheelVerImpl extends WheelDirection {
    public WheelVerImpl(WheelCrossPicker picker) {
        super(picker);
    }

    @Override
    int computeWidth() {
        return mPicker.getTextMaxWidth();
    }

    @Override
    int computeHeight() {
        return mPicker.getTextMaxHeight() * mPicker.getItemCount();
    }
}