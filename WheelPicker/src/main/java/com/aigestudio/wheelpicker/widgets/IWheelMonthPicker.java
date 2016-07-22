package com.aigestudio.wheelpicker.widgets;

/**
 * 月份选择器方法接口
 * <p>
 * Interface of WheelMonthPicker
 *
 * @author AigeStudio 2016-07-12
 * @version 1
 */
public interface IWheelMonthPicker {
    /**
     * 获取月份选择器初始化时选择的月份
     *
     * @return 选择的月份
     */
    int getSelectedMonth();

    /**
     * 设置月份选择器初始化时选择的月份
     *
     * @param month 选择的月份
     */
    void setSelectedMonth(int month);

    /**
     * 获取当前选择的月份
     *
     * @return 当前选择的月份
     */
    int getCurrentMonth();
}
