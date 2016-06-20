package com.aigestudio.wheelpicker.cross;

import android.graphics.Paint;

import java.util.List;

/**
 * Created by Administrator on 2016/6/20.
 */
class WheelVerImpl extends WheelDirection {
    @Override
    int computeWidth(Paint paint, List data, int count, boolean hasSameSize) {
        int size = 0;
        if (hasSameSize)
            size = Math.max(size, computeTextWidth(paint, data.get(0)));
        else
            for (Object obj : data)
                size = Math.max(size, computeTextWidth(paint, obj));
        return size;
    }

    @Override
    int computeHeight(Paint paint, List data, int count, boolean hasSameSize) {
        int size = 0;
        if (hasSameSize)
            size = computeTextHeight(paint, data.get(0)) * count;
        else
            for (Object obj : data)
                size += computeTextWidth(paint, obj);
        return size;
    }
}