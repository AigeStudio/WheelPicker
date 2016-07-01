package com.aigestudio.wheelpicker;

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
interface IWheelPicker {

    int getVisibleItemCount();

    void setVisibleItemCount(int count);

    boolean isCyclic();

    void setCyclic(boolean isCyclic);

    /**
     * 设置滚轮Item选中监听器
     *
     * @param listener 滚轮Item选中监听器{@link WheelPicker.OnItemSelectListener}
     */
    void setOnItemSelectListener(WheelPicker.OnItemSelectListener listener);

    /**
     * 获取当前被选中的Item所显示的数据在数据源中的位置
     *
     * @return 当前被选中的Item所显示的数据在数据源中的位置
     */
    int getCurrentItem();

    /**
     * 设置当前被选中的Item所显示的数据在数据源中的位置
     *
     * @param position 当前被选中的Item所显示的数据在数据源中的位置
     */
    void setCurrentItem(int position);

    /**
     * 获取数据列表
     *
     * @return ...
     */
    List getData();

    /**
     * 设置数据列表
     * 数据源可以是任意类型，但是需要注意的是WheelPicker在绘制数据的时候会将数据转换成String类型
     * 在没有设置数据源的情况下滚轮选择器会设置一个默认的数据源作为展示
     *
     * @param data 数据列表
     */
    void setData(List data);

    /**
     * 设置Item是否有相同的大小
     * 设置Item有相同大小可以大幅提升滚动选择器运行效率
     * Item有相同大小分为两种情况，当滚轮为垂直时相同Item大小表示所有的Item宽度相同，当滚轮为水平时相同Item大小表示所有的Item高度相同
     * 实际情况下为水平滚动选择器设置该值并无意义
     *
     * @param hasSameSize ...
     */
    void setHasSameWidth(boolean hasSameSize);

    /**
     * Item是否有相同大小
     *
     * @return ...
     */
    boolean hasSameWidth();

    /**
     * 获取滚轮选择器滚动方向
     *
     * @return ...
     */
    int getDirection();

    /**
     * 设置滚轮选择器滚动方向
     *
     * @param orientation 滚动方向标识值，仅能是下列值之一：
     *                    {@link WheelPicker#DIR_VER}
     *                    {@link WheelPicker#DIR_HOR}
     */
    void setDirection(int orientation);

    /**
     * 设置滚轮滚动监听器
     *
     * @param listener ...
     */
    void setOnWheelChangeListener(WheelPicker.OnWheelChangeListener listener);

    /**
     * 获取最宽的文本
     *
     * @return 最宽的文本
     */
    String getMaximumWidthText();

    /**
     * 设置最宽的文本
     *
     * @param text 最宽的文本
     */
    void setMaximumWidthText(String text);

    /**
     * 获取最宽的文本在数据源中的位置
     *
     * @return 最宽的文本在数据源中的位置
     */
    int getMaximumWidthTextPosition();

    /**
     * 设置最宽的文本在数据源中的位置
     *
     * @param position 最宽的文本在数据源中的位置
     */
    void setMaximumWidthTextPosition(int position);

    /**
     * 获取当前Item文本颜色
     *
     * @return 当前Item文本颜色
     */
    int getCurrentItemTextColor();

    /**
     * 设置当前Item文本颜色
     *
     * @param color 当前Item文本颜色
     */
    void setCurrentItemTextColor(int color);

//    int getItemTextSize();
//
//    void setItemTextSize(int dp);
//
//    Typeface getItemTextTypeface();
//
//    void setItemTextTypeface(Typeface typeface);
//
//    int getItemTextColor();
//
//    void setItemTextColor(int color);
//

//
//    int getTextMaxWidth();
//
//    int getTextMaxHeight();
//
//    /**
//     * 设置滚轮当前显示的Item数量
//     * 滚轮显示的Item不能小于2
//     *
//     * @param count 显示的Item数量
//     */
//    void setItemCount(int count);
//
//    /**
//     * 获取滚轮当前显示的Item数量
//     *
//     * @return ...
//     */
//    int getItemCount();
//
//    /**
//     * 设置滚轮Item间距
//     *
//     * @param space Item间距dp值
//     */
//    void setItemSpace(int space);
//
//    /**
//     * 获取滚轮Item间距
//     *
//     * @return ...
//     */
//    int getItemSpace();
//
//    /**
//     * 设置Item在水平或垂直滚动器上的对齐方式
//     * 滚动选择器默认的Item对齐方式为居中对齐{@link WheelCrossPicker#ALIGN_CENTER}
//     * 居中对齐{@link WheelCrossPicker#ALIGN_CENTER}适用于水平和垂直两个方向的滚轮选择器上
//     * 靠左对齐{@link WheelCrossPicker#ALIGN_LEFT}和靠右{@link WheelCrossPicker#ALIGN_RIGHT}对齐只能用于垂直方向的滚轮选择器上
//     * 置顶对齐{@link WheelCrossPicker#ALIGN_TOP}和置底对齐{@link WheelCrossPicker#ALIGN_BOTTOM}只能用于水平方向的滚轮选择器上
//     * 当且仅当满足以下条件之一时Item的对齐方式才会生效：
//     * 1.如果{@link #isPerspective()}返回true
//     * 2.如果{@link #isPerspective()}返回false并且{@link #hasSameWidth()}返回false并且当前滚轮选择器为垂直滚动状态
//     *
//     * @param align 对齐方式标识值
//     *              当滚动选择器为垂直滚动时该值仅能是下列值之一：
//     *              {@link WheelCrossPicker#ALIGN_CENTER}
//     *              {@link WheelCrossPicker#ALIGN_LEFT}
//     *              {@link WheelCrossPicker#ALIGN_RIGHT}
//     *              当滚动选择器为水平滚动时该值仅能是下列值之一：
//     *              {@link WheelCrossPicker#ALIGN_CENTER}
//     *              {@link WheelCrossPicker#ALIGN_TOP}
//     *              {@link WheelCrossPicker#ALIGN_BOTTOM}
//     */
//    void setItemAlign(int align);
//
//    /**
//     * 获取Item对齐方式
//     *
//     * @return Item对齐方式
//     */
//    int getItemAlign();
//
//    /**
//     * 设置滚轮是否有指示器
//     * 如果设置滚轮有指示器，那么将会在滚轮的当前项上下(垂直)/左右(水平)显示两根分割线
//     *
//     * @param hasIndicator 是否有指示器
//     */
//    void setIndicator(boolean hasIndicator);
//
//    /**
//     * 是否有指示器
//     *
//     * @return ...
//     */
//    boolean hasIndicator();
//
//    /**
//     * 设置指示器尺寸
//     * 设置指示器分割线的高度(垂直)/宽度(水平)
//     *
//     * @param size 指示器尺寸值，单位dp
//     */
//    void setIndicatorSize(int size);
//
//    /**
//     * 获取指示器尺寸
//     * 当滚轮滚动方向为垂直时指示器尺寸将返回指示器高度
//     * 当滚轮滚动方向为水平时指示器尺寸将返回指示器宽度
//     *
//     * @return 根据当前滚轮滚动方向返回的指示器尺寸
//     */
//    int getIndicatorSize();
//
//    /**
//     * 设置指示器颜色
//     *
//     * @param color 16位指示器颜色
//     */
//    void setIndicatorColor(int color);
//
//    /**
//     * 获取指示器颜色
//     *
//     * @return 16位颜色值
//     */
//    int getIndicatorColor();
//
//    /**
//     * 设置滚轮是否显示幕布
//     * 设置滚轮显示幕布的话将会在当前Item项上方绘制一个与当前Item项大小一致的矩形区域并填充指定颜色
//     *
//     * @param hasCurtain 是否显示幕布
//     */
//    void setCurtain(boolean hasCurtain);
//
//    /**
//     * 是否显示幕布
//     *
//     * @return ...
//     */
//    boolean hasCurtain();
//
//    /**
//     * 设置滚轮幕布颜色
//     *
//     * @param color 16位颜色值
//     */
//    void setCurtainColor(int color);
//
//    /**
//     * 获取幕布颜色
//     *
//     * @return 16位颜色值
//     */
//    int getCurtainColor();
//
//    /**
//     * 设置是否循环展示数据
//     *
//     * @param isCyclic 是否循环展示数据
//     */
//    void setCyclic(boolean isCyclic);
//
//    /**
//     * 是否为循环展示数据
//     *
//     * @return ...
//     */
//    boolean isCyclic();
//
//    /**
//     * 设置滚轮是否为透视状态
//     * 处于透视状态的滚轮将呈现一种中间大两端小并且两端向屏幕内弯曲的效果
//     *
//     * @param isPerspective 是否为透视状态
//     */
//    void setPerspective(boolean isPerspective);
//
//    /**
//     * 是否为透视状态
//     *
//     * @return ...
//     */
//    boolean isPerspective();
//
//    /**
//     * 设置滚轮是否有空气感
//     * 开启空气感的滚轮将呈现中间不透明逐渐向两端透明过度的渐变效果
//     * 注意如果调用{@link #setCurrentItemTextColor(int)}为当前Item设置颜色，那么当前Item的空气感效果将会被设定的颜色值所覆盖
//     *
//     * @param hasAtmospheric 是否有空气感
//     */
//    void setAtmospheric(boolean hasAtmospheric);
//
//    /**
//     * 是否有空气感
//     *
//     * @return ...
//     */
//    boolean isAtmospheric();

}