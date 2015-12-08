package com.aigestudio.wheelpicker.view;

import com.aigestudio.wheelpicker.view.WheelPicker.Style;

import java.util.List;

/**
 * 滚轮控件对外功能接口
 * Interface of WheelView's function
 *
 * @author AigeStudio 2015-12-03
 * @version 1.0.0 preview
 */
interface IWheelPicker {
//    /**
//     * 设置滚轮的滚动方向
//     * 目前位置仅仅支持垂直滚动{@link WheelPicker.Direction#VERTICAL}
//     * Set direction of WheelView
//     * So far, WheelPicker only support vertical{@link WheelPicker.Direction#VERTICAL} scrolling
//     * This method will available in a later version
//     *
//     * @param direction 滚轮的滚动方向
//     *                  Direction of WheelView
//     */
//    void setDirection(Direction direction);

    /**
     * 设置滚轮的样式
     * 目前为止WheelPicker仅仅支持直向{@link WheelPicker.Style#STRAIGHT}和弯曲{@link WheelPicker.Style#CURVED}两种样式
     * Set style of WheelView
     * So far, WheelPicker only support straight{@link WheelPicker.Style#STRAIGHT} and curved{@link WheelPicker.Style#CURVED} style
     *
     * @param style 滚轮的样式
     *              Style of WheelView
     */
    void setStyle(Style style);

    /**
     * 设置显示数据
     * 目前为止WheelPicker仅仅支持字符串列表类型的数据源
     * Set data to display in WheelView
     * So far, WheelPicker only support string list for data set
     *
     * @param data 显示数据
     *             Display data set
     */
    void setData(List<String> data);

    /**
     * 设置文本颜色
     * Set item text color
     *
     * @param color 文本颜色
     *              Item text color
     */
    void setTextColor(int color);

    /**
     * 设置当前居中显示的文本在数据列表中的下标值
     * Set index of data in list will display in WheelView center
     *
     * @param index 下标值
     *              Index of data in list will display in WheelView center
     */
    void setItemIndex(int index);

    /**
     * 设置文本大小
     * Set size of text
     *
     * @param size 文本大小
     *             Text size
     */
    void setTextSize(int size);


    /**
     * 获取当前Item项上对应的文本数据
     * Get text of current item display
     *
     * @return 当前Item项上对应的文本数据 Text of current item
     */
    String getCurrentItemText();

    /**
     * 设置当前Item项的背景装饰物
     * Set background decorate of current item
     *
     * @param ignorePadding 是否忽略WheelView的内边距
     *                      Is ignore padding of WheelView
     * @param decor         装饰物对象
     *                      Subclass of AbstractWheelDecor{@link AbstractWheelDecor}
     */
    void setCurrentItemBackgroundDecor(boolean ignorePadding, AbstractWheelDecor decor);

    /**
     * 设置当前Item项的前景装饰物
     * Set foreground decorate of current item
     *
     * @param ignorePadding 是否忽略WheelView的内边距
     *                      Is ignore padding of WheelView
     * @param decor         装饰物对象
     *                      Subclass of AbstractWheelDecor{@link AbstractWheelDecor}
     */
    void setCurrentItemForegroundDecor(boolean ignorePadding, AbstractWheelDecor decor);
}