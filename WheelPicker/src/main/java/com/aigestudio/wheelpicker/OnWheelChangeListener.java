package com.aigestudio.wheelpicker;

/**
 * 滚轮选择器滚动时监听接口
 *
 * @author AigeStudio
 *         新项目结构
 * @since 2016-06-17
 */
public interface OnWheelChangeListener {
    /**
     * 当滚轮选择器滚动时回调该方法
     *
     * @param offset 当前滚轮滚动距离上一次滚轮滚动停止后偏移的距离
     */
    void onWheelScrolled(int offset);

    /**
     * 当滚轮选择器停止后回调该方法
     *
     * @param position 当前位于滚轮中心的数据在数据列表中的位置
     */
    void onWheelSelected(int position);

    /**
     * 当滚轮选择器滚动状态改变时回调该方法
     *
     * @param state 滚轮选择器滚动状态，其值仅可能为下列之一
     *              {@link WheelPicker#SCROLL_STATE_IDLE}
     *              {@link WheelPicker#SCROLL_STATE_DRAGGING}
     *              {@link WheelPicker#SCROLL_STATE_SCROLLING}
     */
    void onWheelScrollStateChanged(int state);
}