package com.aigestudio.wheelpicker.widgets;

/**
 * 选择器方法接口
 * <p>
 * Interface of WheelMonthPicker
 *
 * @author AigeStudio 2016-07-12
 * @version 1
 */
public interface IWheelDayPicker {
    /**
     * 获取日期选择器初始化时选择的日期
     *
     * @return 选择的日期
     */
    int getSelectedDay();

    /**
     * 设置日期选择器初始化时选择的日期
     *
     * @param day 选择的日期
     */
    void setSelectedDay(int day);

    /**
     * 获取当前选择的日期
     *
     * @return 选择的日期
     */
    int getCurrentDay();

    /**
     * 设置年份和月份
     *
     * @param year  年份
     * @param month 月份
     */
    void setYearAndMonth(int year, int month);

    /**
     * 获取年份
     *
     * @return 年份
     */
    int getYear();

    /**
     * 设置年份
     *
     * @param year ...
     */
    void setYear(int year);

    /**
     * 获取月份
     *
     * @return 月份
     */
    int getMonth();

    /**
     * 设置月份
     *
     * @param month 月份
     */
    void setMonth(int month);
}