package com.aigestudio.wheelpicker.view;

/**
 * 通用常量值
 * General constant value
 *
 * @author AigeStudio 2015-12-01
 * @version 1.0.0 preview
 */
class WheelCons {
    /**
     * 全局Debug开关
     * Global debug switch
     */
    static final boolean DEBUG = true;

    /**
     * 圆环显示的最大角度范围
     * Maximum degree of ring display in range
     */
    static final int DEGREE_MAXIMUM = 90;

    /**
     * 圆环显示的最小角度范围
     * Minimum degree of ring display in range
     */
    static final int DEGREE_MINIMUM = -DEGREE_MAXIMUM;

    /**
     * 触摸距离的最小响应值
     * Minimum response value of touch move distance
     */
    static final int TOUCH_DISTANCE_MINIMUM = 8;

    /**
     * 速度追踪单位值
     * Value of velocity track
     */
    static final int VELOCITY_TRACKER_UNITS = 200;
}