package com.aigestudio.wheelpicker.widgets;

/**
 * 月份选择器方法接口
 * <p>
 * Interface of WheelMonthPicker
 *
 * @author AigeStudio
 * @since 2016-07-11
 */
public interface IWheelMonthPicker {
    /**
     * 获取月份选择器初始化时当前选中的月份
     *
     * @return 月份选择器初始化时当前选中的月份
     */
    int getSelectedMonth();

    /**
     * 设置月份选择器初始化时当前选中的月份
     *
     * @param month 月份选择器初始化时当前选中的月份
     */
    void setSelectedMonth(int month);

    /**
     * 获取当前选中的月份
     *
     * @return 当前选中的月份
     */
    int getCurrentMonth();
}