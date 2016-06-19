package com.aigestudio.wheelpicker.core;

import android.graphics.Typeface;

import java.util.List;

/**
 * 滚轮选择器方法接口
 * Interface of WheelPicker
 *
 * @author AigeStudio 2015-12-03
 * @author AigeStudio 2015-12-08
 * @author AigeStudio 2015-12-12
 * @author AigeStudio 2016-06-17
 *         更新项目结构
 * @version 1.1.0 beta
 */
public interface IWheelPicker {
    /**
     * 设置数据列表
     * 数据源可以是任意类型，但是需要注意的是WheelPicker在绘制数据的时候会将数据转换成String类型
     * 在没有设置数据源的情况下滚轮选择器会设置一个默认的数据源作为展示
     *
     * @param data 数据列表
     */
    void setData(List data);

    /**
     * 获取数据列表
     *
     * @return ...
     */
    List getData();

    /**
     * 设置滚轮Item选中监听器
     *
     * @param listener 滚轮Item选中监听器{@link OnItemSelectListener}
     */
    void setOnItemSelectListener(OnItemSelectListener listener);

    /**
     * 设置滚轮滚动监听器
     *
     * @param listener ...
     */
    void setOnWheelChangeListener(OnItemSelectListener listener);

    /**
     * 设置当前被选中的Item所显示的数据在数据源中的位置
     *
     * @param position 当前被选中的Item所显示的数据在数据源中的位置
     */
    void setCurrentItem(int position);

    /**
     * 获取当前被选中的Item所显示的数据在数据源中的位置
     *
     * @return 当前被选中的Item所显示的数据在数据源中的位置
     */
    int getCurrentItem();

    /**
     * 设置Item是否有相同的大小
     * 设置Item有相同大小可以大幅提升滚动选择器运行效率
     * Item有相同大小分为两种情况，当滚轮为垂直时相同Item大小表示所有的Item宽度相同，当滚轮为水平时相同Item大小表示所有的Item高度相同
     *
     * @param hasSameSize ...
     */
    void setHasSameSize(boolean hasSameSize);

    /**
     * Item是否有相同大小
     *
     * @return ...
     */
    boolean hasSameSize();

    void setItemTextSize(int dp);

    int getItemTextSize();

    void setItemTextTypeface(Typeface typeface);

    Typeface getItemTextTypeface();

    void setItemTextColor(int color);

    int getItemTextColor();

    void setCurrentItemTextColor(int color);

    int getCurrentItemTextColor();
}