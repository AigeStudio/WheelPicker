package com.aigestudio.wheelpicker.cross;

/**
 * Created by Administrator on 2016/6/20.
 */
abstract class WheelCrossPattern {
    private int orientation;

    public void init(int orientation) {

    }

    /**
     * 计算滚动选择器尺寸大小
     *
     * @return 滚轮选择器尺寸大小
     */
    abstract int computeWheelSize();
}