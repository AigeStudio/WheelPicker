package com.aigestudio.wheelpicker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.aigestudio.wheelpicker.R;
import com.aigestudio.wheelpicker.view.WheelPicker.Direction;
import com.aigestudio.wheelpicker.view.WheelPicker.Style;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 滚轮实际的控制类
 * Controller of WheelView
 *
 * @author AigeStudio 2015-12-03
 * @version 1.0.0 preview
 */
class WheelView extends View {
    AbstractWheelStyle wheel;

    Direction direction;
    Style style;

    List<String> data = new ArrayList<>();

    int textSize, textColor;
    int itemCount, itemSpace, itemIndex;
    int itemIndexWidthMaximum = -1, itemIndexHeightMaximum = -1;

    boolean hasSameSize;
    boolean isTextTransGradient;

    String textWidthMaximum, textHeightMaximum;

    WheelView(Context context) {
        this(context, null);
    }

    WheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    WheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WheelPicker);
        int idData = a.getResourceId(R.styleable.WheelPicker_wheel_data, 0);
        if (idData == 0) idData = R.array.WheelArrayDefault;
        data = Arrays.asList(context.getResources().getStringArray(idData));
        direction = Direction.fromID(a.getInt(R.styleable.WheelPicker_wheel_direction, 1));
        style = Style.fromID(a.getInt(R.styleable.WheelPicker_wheel_style, 1));
        itemIndex = a.getInt(R.styleable.WheelPicker_wheel_item_index, 0);
//        itemIndexWidthMaximum = a.getInt(R.styleable.WheelPicker_wheel_item_index_size_maximum, -1);
        itemCount = a.getInt(R.styleable.WheelPicker_wheel_item_count, 5);
        itemSpace = a.getDimensionPixelSize(R.styleable.WheelPicker_wheel_item_space, 25);
        textSize = a.getDimensionPixelSize(R.styleable.WheelPicker_wheel_text_size, 100);
        textColor = a.getColor(R.styleable.WheelPicker_wheel_text_color, Color.BLACK);
        hasSameSize = a.getBoolean(R.styleable.WheelPicker_wheel_item_same_size, false);
//        textWidthMaximum = a.getString(R.styleable.WheelPicker_wheel_text_maximum_size);
        isTextTransGradient = true;
//        isTextTransGradient = a.getBoolean(R.styleable.WheelPicker_wheel_text_transparent_gradient,
//                false);
        a.recycle();

        wheel = WheelFactory.createWheelStyle(style, this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        wheel.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    void setWheelSize(int width, int height) {
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        wheel.onSizeChanged(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        wheel.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        wheel.onTouchEvent(event);
        return true;
    }

    void interceptTouchEvent(boolean isIntercept) {
        getParent().requestDisallowInterceptTouchEvent(isIntercept);
    }

    @Override
    public void computeScroll() {
        wheel.computeScroll();
    }
}