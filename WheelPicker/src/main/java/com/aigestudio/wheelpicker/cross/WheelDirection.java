package com.aigestudio.wheelpicker.cross;

import android.graphics.Paint;
import android.graphics.Rect;

import java.util.List;

/**
 * Created by Administrator on 2016/6/20.
 */
abstract class WheelDirection {
    protected final Rect mTextBounds = new Rect();

    abstract int computeWidth(Paint paint, List data, int count, boolean hasSameSize);

    protected int computeTextWidth(Paint paint, Object obj) {
        computeTextBounds(paint, obj);
        return mTextBounds.width();
    }

    abstract int computeHeight(Paint paint, List data, int count, boolean hasSameSize);

    protected int computeTextHeight(Paint paint, Object obj) {
        computeTextBounds(paint, obj);
        return mTextBounds.height();
    }

    private void computeTextBounds(Paint paint, Object obj) {
        String text = String.valueOf(obj);
        paint.getTextBounds(text, 0, text.length(), mTextBounds);
    }
}
