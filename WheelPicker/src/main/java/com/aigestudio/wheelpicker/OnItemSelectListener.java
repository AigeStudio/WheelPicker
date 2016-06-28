package com.aigestudio.wheelpicker;

/**
 * 滚轮选择器Item项被选中时监听接口
 *
 * @author AigeStudio 2016-06-17
 *         新项目结构
 * @version 1.1.0 beta
 */
public interface OnItemSelectListener {
    /**
     * 当滚轮选择器Item被选中时
     * 滚动选择器滚动停止后会回调该方法并将当前在滚轮中心显示的数据和数据在数据列表中对应的位置返回
     * 该方法在滚动初始化设置数据后也会调用
     *
     * @param picker   滚轮选择器
     * @param data     当前位于滚轮中心显示的数据
     * @param position 当前位于滚轮中心显示的数据在数据列表中的位置
     */
    void onItemSelected(WheelPicker picker, Object data, int position);
}