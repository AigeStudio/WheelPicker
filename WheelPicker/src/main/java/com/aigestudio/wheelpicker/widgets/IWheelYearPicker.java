package com.aigestudio.wheelpicker.widgets;

import com.aigestudio.wheelpicker.WheelPicker;

/**
 * 年份选择器方法接口
 * <p>
 * Interface of WheelYearPicker
 *
 * @author AigeStudio
 * @since 2016-07-11
 */
public interface IWheelYearPicker {
    /**
     * 设置年份范围
     * 用于设置年份选择器能够选择的年份范围
     * 默认的年份选择范围为1000到3000
     *
     * @param start 年份开始
     * @param end   年份结束
     */
    void setYearRange(int start, int end);

    /**
     * 获取开始的年份
     *
     * @return 开始的年份
     */
    int getYearStart();

    /**
     * 设置开始的年份
     *
     * @param start 开始的年份
     */
    void setYearStart(int start);

    /**
     * 获取结束的年份
     *
     * @return 结束的年份
     */
    int getYearEnd();

    /**
     * 设置结束的年份
     *
     * @param end 结束的年份
     */
    void setYearEnd(int end);

    /**
     * 获取年份选择器初始化时选中的年份
     *
     * @return 年份选择器初始化时选中的年份
     * @see WheelPicker#getSelectedItemPosition()
     */
    int getSelectedYear();

    /**
     * 设置年份选择器初始化时选中的年份
     * 年份选择器初始化时默认选中的年份为当前年份
     *
     * @param year 年份选择器初始化时选中的年份
     * @see WheelPicker#setSelectedItemPosition(int)
     */
    void setSelectedYear(int year);

    /**
     * 获取当前选中的年份
     *
     * @return 当前选中的年份
     */
    int getCurrentYear();
}