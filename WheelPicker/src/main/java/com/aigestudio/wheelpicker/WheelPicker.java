package com.aigestudio.wheelpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import java.util.Arrays;
import java.util.List;

/**
 * 滚轮选择器
 *
 * @author AigeStudio 2015-12-12
 * @author AigeStudio 2016-06-17
 *         更新项目结构
 * @version 1.1.0 beta
 */
public class WheelPicker extends View implements IDebug, IWheelPicker, Runnable {
    /**
     * 滚动状态标识值
     */
    public static final int SCROLL_STATE_IDLE = 0, SCROLL_STATE_DRAGGING = 1,
            SCROLL_STATE_SCROLLING = 2;

    public static final int DIR_VER = 0, DIR_HOR = 1;

    private static final String TAG = WheelPicker.class.getSimpleName();

    private final Handler mHandler = new Handler();

    private Paint mPaint;
    private Scroller mScroller;
    private VelocityTracker mTracker;
    private OnItemSelectListener mOnItemSelectListener;
    private OnWheelChangeListener mOnWheelChangeListener;
    private Rect mRectIndicatorHead, mRectIndicatorFoot;
    private Rect mRectCurtain;

    /**
     *
     */
    private List mData;// 数据源

    private String mMaxWidthText;

    /**
     * 滚轮选择器中可见的Item数量和滚轮选择器将会绘制的Item数量
     * 滚轮选择器将会绘制的Item数量取决于可见Item数量，绘制的Item数量总会比可见Item数量多两个，你不必也不需要为滚
     * 轮选择器指定将会绘制的Item数量，你只需通过{@link #setVisibleItemCount(int)}为滚轮选择器指定可见的Item
     * 数量，滚轮选择器会根据可见Item数量自动计算绘制的Item数量。
     * 滚轮选择器的可见Item数量应始终为奇数，如果你为可见Item数量设置一个偶数值，滚轮选择器会将其自动加一转换为奇
     * 数，默认为情况下滚轮选择器的可见Item数量为5，绘制Item数量为7。
     *
     * @see #setVisibleItemCount(int)
     */
    private int mVisibleItemCount, mDrawnItemCount;

    /**
     * 滚轮选择器将会绘制的Item数量的一半
     * 该值主要用于偏移值的计算，该值由滚轮选择器计算赋值
     */
    private int mHalfDrawnItemCount;
    private int mTextMaxWidth, mTextMaxHeight;

    private int mItemTextColor;
    private int mCurrentItemTextColor;

    private int mItemTextSize;

    private int mIndicatorSize;
    private int mIndicatorColor;

    private int mCurtainColor;

    private int mItemSpace;

    /**
     * 滚轮选择器单个Item宽高
     */
    private int mItemWidth, mItemHeight;
    private int mHalfItemWidth, mHalfItemHeight;


    private int mCurrentItemPosition;

    private int mDirection;

    private int mMinFlingY, mMaxFlingY;
    private int mMinimumVelocity = 50, mMaximumVelocity = 8000;

    /**
     * 滚轮选择器中心坐标
     * 滚轮选择器的中心坐标为滚轮正中心坐标，其X坐标取值为滚轮宽度的一半，而Y坐标取值为滚轮高度的一半
     */
    private int mWheelCenterX, mWheelCenterY;

    /**
     * 滚轮选择器绘制中心坐标
     */
    private int mDrawnCenterX, mDrawnCenterY;

    /**
     *
     */
    private int mScrollOffsetX, mScrollOffsetY;

    /**
     * 滚轮选择器中最宽或最高的文本在数据源中的位置
     */
    private int mTextMaxWidthPosition;

    private int mLastPointY;

    /**
     * 滚轮选择器的每一个Item是否拥有相同的尺寸
     */
    private boolean hasSameWidth;
    private boolean hasIndicator;
    private boolean hasCurtain;
    private boolean hasAtmospheric = true;
    private boolean isCyclic;
    private boolean isDebug;

    public WheelPicker(Context context) {
        this(context, null);
    }

    public WheelPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 从资源文件获取属性参数
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WheelPicker);
        int idData = a.getResourceId(R.styleable.WheelPicker_wheel_data, 0);
        mData = Arrays.asList(getResources()
                .getStringArray(idData == 0 ? R.array.WheelArrayDefault : idData));
        mItemTextSize = a.getDimensionPixelSize(R.styleable.WheelPicker_wheel_item_text_size,
                getResources().getDimensionPixelSize(R.dimen.WheelItemTextSize));
        mVisibleItemCount = a.getInt(R.styleable.WheelPicker_wheel_visible_item_count, 7);
        mCurrentItemPosition = a.getInt(R.styleable.WheelPicker_wheel_current_item_position, 0);
        hasSameWidth = a.getBoolean(R.styleable.WheelPicker_wheel_same_width, false);
        mTextMaxWidthPosition =
                a.getInt(R.styleable.WheelPicker_wheel_maximum_width_text_position, -1);
        mMaxWidthText = a.getString(R.styleable.WheelPicker_wheel_maximum_width_text);
        mCurrentItemTextColor = a.getColor
                (R.styleable.WheelPicker_wheel_current_item_text_color, 0xFF888888);
        mItemTextColor = a.getColor(R.styleable.WheelPicker_wheel_item_text_color, 0xFF888888);
        mItemSpace = a.getDimensionPixelSize(R.styleable.WheelPicker_wheel_item_space,
                getResources().getDimensionPixelSize(R.dimen.WheelItemSpace));
        hasIndicator = a.getBoolean(R.styleable.WheelPicker_wheel_indicator, false);
        mIndicatorColor = a.getColor(R.styleable.WheelPicker_wheel_indicator_color, 0xFFEE3333);
        mIndicatorSize = a.getDimensionPixelSize(R.styleable.WheelPicker_wheel_indicator_size,
                getResources().getDimensionPixelSize(R.dimen.WheelIndicatorSize));
        hasCurtain = a.getBoolean(R.styleable.WheelPicker_wheel_curtain, false);
        mCurtainColor = a.getColor(R.styleable.WheelPicker_wheel_curtain_color, 0x88FFFFFF);
        a.recycle();

        // 可见Item改变后更新与之相关的参数
        updateVisibleItemCount();

        // 初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mPaint.setTextSize(mItemTextSize);
        mPaint.setTextAlign(Paint.Align.CENTER);

        // 计算文本尺寸
        computeTextSize();

        // 初始化滚动器
        mScroller = new Scroller(getContext());

        // 初始化配置参数
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            ViewConfiguration conf = ViewConfiguration.get(getContext());
            mMinimumVelocity = conf.getScaledMinimumFlingVelocity();
            mMaximumVelocity = conf.getScaledMaximumFlingVelocity();
        }
        // 初始化指示器区域
        mRectIndicatorHead = new Rect();
        mRectIndicatorFoot = new Rect();

        mRectCurtain = new Rect();
    }

    private void updateVisibleItemCount() {
        // 确保滚轮选择器可见Item数量为奇数
        if (mVisibleItemCount % 2 == 0)
            mVisibleItemCount += 1;
        mDrawnItemCount = mVisibleItemCount + 2;
        mHalfDrawnItemCount = mDrawnItemCount / 2;
    }

    private void computeTextSize() {
        if (hasSameWidth) {
            mTextMaxWidth = (int) mPaint.measureText(String.valueOf(mData.get(0)));
        } else if (isPosInRang(mTextMaxWidthPosition)) {
            mTextMaxWidth = (int) mPaint.measureText
                    (String.valueOf(mData.get(mTextMaxWidthPosition)));
        } else if (!TextUtils.isEmpty(mMaxWidthText)) {
            mTextMaxWidth = (int) mPaint.measureText(mMaxWidthText);
        } else {
            for (Object obj : mData) {
                String text = String.valueOf(obj);
                int width = (int) mPaint.measureText(text);
                mTextMaxWidth = Math.max(mTextMaxWidth, width);
            }
        }
        Paint.FontMetrics metrics = mPaint.getFontMetrics();
        mTextMaxHeight = (int) (metrics.bottom - metrics.top);
        if (isDebug)
            Log.i(TAG, "Text's max size is: (" + mTextMaxWidth + "," + mTextMaxHeight + ")");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int resultWidth = mTextMaxWidth;
        int resultHeight = mTextMaxHeight * mVisibleItemCount + mItemSpace * (mVisibleItemCount - 1);

        resultWidth = measureSize(modeWidth, sizeWidth, resultWidth);
        resultHeight = measureSize(modeHeight, sizeHeight, resultHeight);

        setMeasuredDimension(resultWidth, resultHeight);
    }

    private int measureSize(int mode, int sizeExpect, int sizeActual) {
        int realSize;
        if (mode == MeasureSpec.EXACTLY) {
            realSize = sizeExpect;
        } else {
            realSize = sizeActual;
            if (mode == MeasureSpec.AT_MOST)
                realSize = Math.min(realSize, sizeExpect);
        }
        return realSize;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        mWheelCenterX = getWidth() / 2;
        mWheelCenterY = getHeight() / 2;

        mDrawnCenterX = mWheelCenterX;
        mDrawnCenterY = (int) (mWheelCenterY - ((mPaint.ascent() + mPaint.descent()) / 2));

        mItemHeight = getHeight() / mVisibleItemCount;
        mHalfItemHeight = mItemHeight / 2;

        // 初始化滑动最大坐标
        computeFlingLimitY();

        // 计算指示器绘制区域
        computeIndicatorRect();

        // 计算幕布绘制区域
        computeCurtainRect();
    }

    private void computeFlingLimitY() {
        int currentItemOffset = mCurrentItemPosition * mItemHeight;
        mMinFlingY = isCyclic ? Integer.MIN_VALUE :
                -mItemHeight * (mData.size() - 1) + currentItemOffset;
        mMaxFlingY = isCyclic ? Integer.MAX_VALUE : currentItemOffset;
    }

    private void computeIndicatorRect() {
        if (!hasIndicator) return;
        int halfIndicatorSize = mIndicatorSize / 2;
        if (isDebug)
            Log.i(TAG, "Indicator size is" + mIndicatorSize);
        int indicatorHeadCenterY = mWheelCenterY + mHalfItemHeight;
        int indicatorFootCenterY = mWheelCenterY - mHalfItemHeight;
        mRectIndicatorHead.set(0, indicatorHeadCenterY - halfIndicatorSize, getWidth(),
                indicatorHeadCenterY + halfIndicatorSize);
        mRectIndicatorFoot.set(0, indicatorFootCenterY - halfIndicatorSize, getWidth(),
                indicatorFootCenterY + halfIndicatorSize);
    }

    private void computeCurtainRect() {
        if (!hasCurtain) return;
        mRectCurtain.set(0, mWheelCenterY - mHalfItemHeight, getWidth(),
                mWheelCenterY + mHalfItemHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int drawnDataStartPos = -mScrollOffsetY / mItemHeight - mHalfDrawnItemCount;
        for (int drawnDataPos = drawnDataStartPos + mCurrentItemPosition,
             drawnOffsetPos = -mHalfDrawnItemCount;
             drawnDataPos < drawnDataStartPos + mCurrentItemPosition + mDrawnItemCount;
             drawnDataPos++, drawnOffsetPos++) {
            String data = "";
            if (isCyclic) {
                int actualPos = drawnDataPos % mData.size();
                actualPos = actualPos < 0 ? (actualPos + mData.size()) : actualPos;
                data = String.valueOf(mData.get(actualPos));
            } else {
                if (isPosInRang(drawnDataPos))
                    data = String.valueOf(mData.get(drawnDataPos));
            }
            mPaint.setColor(mItemTextColor);
            mPaint.setStyle(Paint.Style.FILL);
            int mDrawnItemCenterY = mDrawnCenterY + (drawnOffsetPos * mItemHeight) +
                    mScrollOffsetY % mItemHeight;
            if (isDebug)
                Log.i(TAG, "Item " + drawnOffsetPos + "'s draw centerY is" + mDrawnItemCenterY);
            if (hasAtmospheric) {
                int alpha = (int) ((mDrawnCenterY - Math.abs(mDrawnCenterY - mDrawnItemCenterY)) *
                        1.0F / mDrawnCenterY * 255);
                if (isDebug)
                    Log.i(TAG, "Item " + drawnOffsetPos + "'s draw alpha is" + alpha);
                alpha = alpha < 0 ? 0 : alpha;
                mPaint.setAlpha(alpha);
            }
            canvas.drawText(data, mDrawnCenterX, mDrawnItemCenterY, mPaint);

            if (isDebug) {
                mPaint.setColor(0xFFEE3333);
                int centerY = mWheelCenterY + (drawnOffsetPos * mItemHeight);
                canvas.drawLine(0, centerY, getWidth(), centerY, mPaint);
                mPaint.setColor(0xFF3333EE);
                mPaint.setStyle(Paint.Style.STROKE);
                int top = centerY - mHalfItemHeight;
                canvas.drawRect(0, top, getWidth(), top + mItemHeight, mPaint);
            }
        }
        if (hasCurtain) {
            mPaint.setColor(mCurtainColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(mRectCurtain, mPaint);
        }
        // 是否需要绘制指示器
        if (hasIndicator) {
            mPaint.setColor(mIndicatorColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(mRectIndicatorHead, mPaint);
            canvas.drawRect(mRectIndicatorFoot, mPaint);
        }
    }

    private boolean isPosInRang(int position) {
        return position >= 0 && position < mData.size();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (null == mTracker)
                    mTracker = VelocityTracker.obtain();
                else
                    mTracker.clear();
                mTracker.addMovement(event);
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();
                mLastPointY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mTracker.addMovement(event);

                // 滚动内容
                mScrollOffsetY += ((event.getY() - mLastPointY));
                mLastPointY = (int) event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mTracker.addMovement(event);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT)
                    mTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                else
                    mTracker.computeCurrentVelocity(1000);

                // 根据速度判断是该滚动还是滑动
                int velocity = (int) mTracker.getYVelocity();
                if (Math.abs(velocity) > mMinimumVelocity) {
                    mScroller.fling(0, mScrollOffsetY, 0, velocity, 0, 0, mMinFlingY, mMaxFlingY);
                    mScroller.setFinalY(mScroller.getFinalY() +
                            computeDistanceToEndPoint(mScroller.getFinalY() % mItemHeight));
                } else {
                    mScroller.startScroll(0, mScrollOffsetY, 0,
                            computeDistanceToEndPoint(mScrollOffsetY % mItemHeight));
                }
                // 校正坐标
                if (!isCyclic)
                    if (mScroller.getFinalY() > mMaxFlingY)
                        mScroller.setFinalY(mMaxFlingY);
                    else if (mScroller.getFinalY() < mMinFlingY)
                        mScroller.setFinalY(mMinFlingY);
                mHandler.post(this);
                if (null != mTracker) {
                    mTracker.recycle();
                    mTracker = null;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    private int computeDistanceToEndPoint(int remainder) {
        if (Math.abs(remainder) > mHalfItemHeight)
            if (mScrollOffsetY < 0)
                return -mItemHeight - remainder;
            else
                return mItemHeight - remainder;
        else
            return -remainder;
    }

    @Override
    public void run() {
        if (mScroller.isFinished()) {
            int position = (-mScrollOffsetY / mItemHeight + mCurrentItemPosition) % mData.size();
            position = position < 0 ? position + mData.size() : position;
            if (isDebug)
                Log.i(TAG, position + ":" + mData.get(position) + ":" + mScrollOffsetY);
            if (null != mOnItemSelectListener)
                mOnItemSelectListener.onItemSelected(this, mData.get(position), position);
            if (null != mOnWheelChangeListener)
                mOnWheelChangeListener.onWheelSelected(position);
        }
        if (mScroller.computeScrollOffset()) {
            mScrollOffsetY = mScroller.getCurrY();
            postInvalidate();
            mHandler.postDelayed(this, 16);
        }
    }

    @Override
    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    @Override
    public int getVisibleItemCount() {
        return mVisibleItemCount;
    }

    @Override
    public void setVisibleItemCount(int count) {
        mVisibleItemCount = count;
        updateVisibleItemCount();
        requestLayout();
    }

    @Override
    public boolean isCyclic() {
        return isCyclic;
    }

    @Override
    public void setCyclic(boolean isCyclic) {
        this.isCyclic = isCyclic;
        computeFlingLimitY();
        invalidate();
    }

    @Override
    public void setOnItemSelectListener(OnItemSelectListener listener) {
        mOnItemSelectListener = listener;
    }

    @Override
    public int getCurrentItem() {
        return mCurrentItemPosition;
    }

    @Override
    public void setCurrentItem(int position) {
        if (!isPosInRang(position))
            throw new ArrayIndexOutOfBoundsException("Current item position must in [0, " +
                    mData.size() + "), but current is " + position);
        mCurrentItemPosition = position;
        requestLayout();
        invalidate();
    }

    @Override
    public List getData() {
        return mData;
    }

    @Override
    public void setData(List data) {
        if (null == data)
            throw new NullPointerException("WheelPicker's data can not be null!");
        mData = data;
        computeTextSize();
        requestLayout();
        invalidate();
    }

    public void setSameWidth(boolean hasSameWidth) {
        this.hasSameWidth = hasSameWidth;
        computeTextSize();
        requestLayout();
        invalidate();
    }

    @Override
    public boolean hasSameWidth() {
        return hasSameWidth;
    }

//    @Override
//    public int getDirection() {
//        return mDirection;
//    }
//
//    @Override
//    public void setDirection(int orientation) {
//        // TODO Direction
//    }

    @Override
    public void setOnWheelChangeListener(OnWheelChangeListener listener) {
        mOnWheelChangeListener = listener;
    }

    @Override
    public String getMaximumWidthText() {
        return mMaxWidthText;
    }

    @Override
    public void setMaximumWidthText(String text) {
        if (null == text)
            throw new NullPointerException("Maximum width text can not be null!");
        mMaxWidthText = text;
        computeTextSize();
        requestLayout();
        invalidate();
    }

    @Override
    public int getMaximumWidthTextPosition() {
        return mTextMaxWidthPosition;
    }

    @Override
    public void setMaximumWidthTextPosition(int position) {
        if (!isPosInRang(position))
            throw new ArrayIndexOutOfBoundsException("Maximum width text Position must in [0, " +
                    mData.size() + "), but current is " + position);
        mTextMaxWidthPosition = position;
        computeTextSize();
        requestLayout();
        invalidate();
    }

    @Override
    public int getCurrentItemTextColor() {
        return mCurrentItemTextColor;
    }

    @Override
    public void setCurrentItemTextColor(int color) {
        mCurrentItemPosition = color;
        invalidate();
    }

    @Override
    public int getItemTextColor() {
        return mItemTextColor;
    }

    @Override
    public void setItemTextColor(int color) {
        mItemTextColor = color;
        invalidate();
    }

    @Override
    public int getItemTextSize() {
        return mItemTextSize;
    }

    @Override
    public void setItemTextSize(int size) {
        mItemTextSize = size;
        mPaint.setTextSize(mItemTextSize);
        computeTextSize();
        requestLayout();
        invalidate();
    }

    @Override
    public int getItemSpace() {
        return mItemSpace;
    }

    @Override
    public void setItemSpace(int space) {
        mItemSpace = space;
        requestLayout();
        invalidate();
    }

    @Override
    public void setIndicator(boolean hasIndicator) {
        this.hasIndicator = hasIndicator;
        computeIndicatorRect();
        invalidate();
    }

    @Override
    public boolean hasIndicator() {
        return hasIndicator;
    }

    @Override
    public int getIndicatorSize() {
        return mIndicatorSize;
    }

    @Override
    public void setIndicatorSize(int size) {
        mIndicatorSize = size;
        computeIndicatorRect();
        invalidate();
    }

    @Override
    public int getIndicatorColor() {
        return mIndicatorColor;
    }

    @Override
    public void setIndicatorColor(int color) {
        mIndicatorColor = color;
        invalidate();
    }

    @Override
    public void setCurtain(boolean hasCurtain) {
        this.hasCurtain = hasCurtain;
        computeCurtainRect();
        invalidate();
    }

    @Override
    public boolean hasCurtain() {
        return hasCurtain;
    }

    @Override
    public int getCurtainColor() {
        return mCurtainColor;
    }

    @Override
    public void setCurtainColor(int color) {
        mCurtainColor = color;
        invalidate();
    }

    @Override
    public void setAtmospheric(boolean hasAtmospheric) {
        this.hasAtmospheric = hasAtmospheric;
        invalidate();
    }

    @Override
    public boolean hasAtmospheric() {
        return hasAtmospheric;
    }

    /**
     * 滚轮选择器Item项被选中时监听接口
     *
     * @author AigeStudio 2016-06-17
     *         新项目结构
     * @version 1.1.0
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
}