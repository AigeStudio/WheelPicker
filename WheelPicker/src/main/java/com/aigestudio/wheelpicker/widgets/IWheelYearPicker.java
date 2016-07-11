package com.aigestudio.wheelpicker.widgets;

/**
 * @author AigeStudio
 * @since 2016-07-11
 */
public interface IWheelYearPicker {
    /**
     * 设置年份范围
     * 用于设置年份选择器能够选择的年份范围
     *
     * @param start 年份开始
     * @param end   年份结束
     */
    void setYearRange(int start, int end);

    /**
     * 获取年份选择器开始的年份
     *
     * @return 开始的年份
     */
    int getYearStart();

    /**
     * 获取年份选择器结束的年份
     *
     * @return 结束的年份
     */
    int getYearEnd();

    void setYearStart(int start);

    void setYearEnd(int end);

    void setSelectedYear(int year);
}