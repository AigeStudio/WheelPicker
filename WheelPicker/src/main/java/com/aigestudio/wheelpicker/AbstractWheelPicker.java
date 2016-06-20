package com.aigestudio.wheelpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;
import java.util.List;

/**
 * 抽象滚轮选择器
 *
 * @author AigeStudio 2015-12-12
 * @author AigeStudio 2016-06-17
 *         更新项目结构
 * @version 1.1.0 beta
 */
public abstract class AbstractWheelPicker extends View implements IWheelPicker {
    // 滚轮选择器滚动状态标识值
    public static final int
            SCROLL_STATE_IDLE = 0,// 滚动停止时
            SCROLL_STATE_DRAGGING = 1,// 拖拽时
            SCROLL_STATE_SCROLLING = 2;// 滚动时

    protected OnItemSelectListener mItemSelectListener;
    protected OnWheelChangeListener mWheelChangeListener;

    protected Paint mPaint;
    protected Typeface mItemTextTypeface;

    protected List mData;// 数据列表

    protected int mWheelActualWidth, mWheelActualHeight;
    protected int mCurrentItemPosition;
    protected int mItemCount;
    protected int mItemTextSize;
    protected int mItemTextColor, mCurrentItemTextColor;

    protected boolean hasSameSize;

    public AbstractWheelPicker(Context context) {
        this(context, null);
    }

    public AbstractWheelPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    protected void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.WheelPicker);
        int idData = a.getResourceId(R.styleable.WheelPicker_wheel_data, 0);
        if (idData == 0)
            mData = Arrays.asList(getResources().getStringArray(R.array.WheelArrayDefault));
        else
            mData = Arrays.asList(getResources().getStringArray(idData));
        mCurrentItemPosition = a.getInt(R.styleable.WheelPicker_wheel_current_item_position, 0);
        mItemCount = a.getInt(R.styleable.WheelPicker_wheel_item_count, 5);
        mItemTextSize = a.getDimensionPixelSize(
                R.styleable.WheelPicker_wheel_item_text_size,
                getResources().getDimensionPixelSize(R.dimen.WheelItemTextSize));
        mItemTextColor = a.getColor(R.styleable.WheelPicker_wheel_item_text_color, 0xFF000000);
        mCurrentItemTextColor = a.getColor
                (R.styleable.WheelPicker_wheel_current_item_position, 0xFF000000);
        hasSameSize = a.getBoolean(R.styleable.WheelPicker_wheel_same_size, false);
        a.recycle();

        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
    }

    @Override
    protected abstract void onMeasure(int widthMeasureSpec, int heightMeasureSpec);

    @Override
    protected abstract void onDraw(Canvas canvas);

    @Override
    public List getData() {
        return mData;
    }

    @Override
    public void setData(List data) {
        if (null == data)
            throw new RuntimeException("Wheel's data can not be null!");
        mData = data;
        requestLayout();
    }

    @Override
    public void setOnItemSelectListener(OnItemSelectListener listener) {

    }

    @Override
    public void setOnWheelChangeListener(OnItemSelectListener listener) {

    }

    @Override
    public int getCurrentItem() {
        return 0;
    }

    @Override
    public void setCurrentItem(int position) {

    }

    @Override
    public void setHasSameSize(boolean hasSameSize) {

    }

    @Override
    public boolean hasSameSize() {
        return false;
    }

    @Override
    public int getItemTextSize() {
        return 0;
    }

    @Override
    public void setItemTextSize(int dp) {

    }

    @Override
    public Typeface getItemTextTypeface() {
        return null;
    }

    @Override
    public void setItemTextTypeface(Typeface typeface) {

    }

    @Override
    public int getItemTextColor() {
        return 0;
    }

    @Override
    public void setItemTextColor(int color) {

    }

    @Override
    public int getCurrentItemTextColor() {
        return 0;
    }

    @Override
    public void setCurrentItemTextColor(int color) {

    }
}