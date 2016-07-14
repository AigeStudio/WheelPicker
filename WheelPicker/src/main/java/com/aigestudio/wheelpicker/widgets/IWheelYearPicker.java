package com.aigestudio.wheelpicker.widgets;

/**
 * 年份选择器方法接口
 * <p>
 * Interface of WheelYearPicker
 *
 * @author AigeStudio 2016-07-12
 * @version 1
 */
public interface IWheelYearPicker {
    /**
     * 设置年份范围
     *
     * @param start 开始的年份
     * @param end   结束的年份
     */
    void setYearFrame(int start, int end);

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
     */
    int getSelectedYear();

    /**
     * 设置年份选择器初始化时选中的年份
     *
     * @param year 年份选择器初始化时选中的年份
     */
    void setSelectedYear(int year);

    /**
     * 获取当前选中的年份
     *
     * @return 当前选中的年份
     */
    int getCurrentYear();
}