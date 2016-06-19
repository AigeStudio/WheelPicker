package com.aigestudio.wheelpicker.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.aigestudio.wheelpicker.R;

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
        mPaint = new Paint();

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.WheelPicker);
        int idData = a.getResourceId(R.styleable.WheelPicker_wheel_data, 0);
        if (idData == 0)
            setData(Arrays.asList(getResources().getStringArray(R.array.WheelArrayDefault)));
        else
            setData(Arrays.asList(getResources().getStringArray(idData)));
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
    }

    @Override
    protected abstract void onMeasure(int widthMeasureSpec, int heightMeasureSpec);

    @Override
    protected abstract void onDraw(Canvas canvas);

    @Override
    public void setData(List data) {
        mData = data;
        computeWheelSize();
        requestLayout();
    }

    protected abstract void computeWheelSize();

    @Override
    public List getData() {
        return mData;
    }
}