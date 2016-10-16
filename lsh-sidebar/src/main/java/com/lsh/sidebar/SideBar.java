package com.lsh.sidebar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * 导航控件
 * Created by hua on 2016/10/12.
 */


public class SideBar extends View {
    private static final String[] DEFAULT_DATA_ARRAY = {"∧", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    private String[] mDataArray;
    private static final int DEFALUT_TEXT_SIZE = 15;//SP
    private static final int DEFAULT_SPACE = 1;//DP
    private static final int DEFAULT_BAR_WIDTH = 20;//DP
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_CENTER_WIDTH = 100;//DP
    private static final int DEFAULT_CENTER_TEXTSIZE = 50;//SP
    //BAR
    private float mTextSize;
    private float mItemSpace;
    private float mBarWidth;
    private int mTextColor;
    //center
    private float mCenterWidth;
    private float mCenterTextSize;

    private Paint paint;
    private int mBarItemHeight;//bar item height
    private int mBarHeight;//bar height
    private int width, height;
    private DisplayMetrics mDisplayMetrics;
    private boolean isShowBg = false;
    String currentValue;

    public SideBar(Context context) {
        super(context);
        init(context);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        log("init");
        mDisplayMetrics = context.getResources().getDisplayMetrics();
        mTextSize = sp2px(DEFALUT_TEXT_SIZE);
        mItemSpace = dp2px(DEFAULT_SPACE);
        mBarWidth = dp2px(DEFAULT_BAR_WIDTH);
        mTextColor = DEFAULT_TEXT_COLOR;
        mDataArray = DEFAULT_DATA_ARRAY;
        mCenterWidth = dp2px(DEFAULT_CENTER_WIDTH);
        mCenterTextSize = sp2px(DEFAULT_CENTER_TEXTSIZE);
        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(mTextSize);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int fontHeight = fontMetrics.descent - fontMetrics.ascent;
        log("fontMetrics:" + fontMetrics.toString());
        mBarItemHeight = fontHeight + (int) mItemSpace;

        mBarHeight = mBarItemHeight * mDataArray.length;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        log("onDraw");
        super.onDraw(canvas);
        drawBarBg(canvas); //bg
        drawBarText(canvas);//text
        drawBarCenterView(canvas); //center
    }

    private void drawBarBg(Canvas canvas) {
        if (isShowBg) {
            int left = width - (int) mBarWidth;
            int top = (height - mBarHeight) / 2;
            int right = width;
            int bottom = top + mBarHeight;
            paint.setColor(Color.GRAY);
            canvas.drawRect(left, top, right, bottom, paint);
        }
    }

    private void drawBarText(Canvas canvas) {
        paint.setColor(mTextColor);
        paint.setTextSize(mTextSize);
        for (int i = 0; i < mDataArray.length; i++) {
            int left = width - (int) mBarWidth;
            int top = (height - mBarHeight) / 2 + i * mBarItemHeight;
            int right = width;
            int bottom = (height - mBarHeight) / 2 + (i + 1) * mBarItemHeight;
            int barItemCenterX = width - (int) mBarWidth / 2;
            drawCenterText(canvas, paint, top, bottom, mDataArray[i], barItemCenterX);
        }
    }

    private void drawBarCenterView(Canvas canvas) {
        if (currentValue != null) {
            paint.setColor(Color.GRAY);
            int left = width / 2 - (int) mCenterWidth / 2;
            int top = height / 2 - (int) mCenterWidth / 2;
            int right = width / 2 + (int) mCenterWidth / 2;
            int bottom = height / 2 + (int) mCenterWidth / 2;
            canvas.drawRect(left, top, right, bottom, paint);
            //
            paint.setColor(Color.WHITE);
            paint.setTextSize(mCenterTextSize);
            drawCenterText(canvas, paint, top, bottom, currentValue, width / 2);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        log("onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        log("mViewWidth:" + w);
        log("mViewHeight:" + h);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getX();
        float currentY = event.getY();
        log("currentX:" + currentX);
        log("currentY:" + currentY);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentValue = getDataItemByPos(currentX, currentY);
                if (!TextUtils.isEmpty(currentValue)) {
                    isShowBg = true;
                    setSelectValue(currentValue);
                    invalidate();
                    return true;
                } else {
                    isShowBg = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                String value = getDataItemByPos(currentX, currentY);
                if (!TextUtils.isEmpty(value) && !TextUtils.equals(value, currentValue)) {
                    currentValue = value;
                    log("currentValue:" + currentValue);
                    setSelectValue(value);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                //
                if (!TextUtils.isEmpty(currentValue)) {
                    //
                    isShowBg = false;
                    currentValue = null;
                    invalidate();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private String getDataItemByPos(float currentX, float currentY) {
        boolean blnX = width - mBarWidth <= currentX && currentX <= width;
        boolean blnY = (height - mBarHeight) / 2 <= currentY && currentY <= (height + mBarHeight) / 2;
        if (isShowBg && blnY || !isShowBg && blnX && blnY) {
            int i = (int) ((currentY - (height - mBarHeight) / 2) / mBarItemHeight);
            return mDataArray[i];
        }
        return null;
    }

    /**
     * @param canvas  Canvas
     * @param paint   Paint
     * @param top     targetRect top
     * @param bottom  targetRect bottom
     * @param des     text
     * @param centerX targetRect centerX
     */
    private void drawCenterText(Canvas canvas, Paint paint, int top, int bottom, String des, int centerX) {
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        // 转载请注明出处：http://blog.csdn.net/hursing
        int baseline = (bottom + top - fontMetrics.bottom - fontMetrics.top) / 2;
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(des, centerX, baseline, paint);
    }

    /*==========================================================================*/
    private void log(String msg) {
        Log.d("SideBar", msg);
    }

    private float sp2px(int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, mDisplayMetrics);
    }

    private float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mDisplayMetrics);
    }

    /*================================================================*/
    private OnSelectListener mOnSelectListener;

    public interface OnSelectListener {
        void onSelect(String value);
    }

    public void setOnSelectListener(OnSelectListener mOnSelectListener) {
        this.mOnSelectListener = mOnSelectListener;
    }

    private void setSelectValue(String value) {
        if (mOnSelectListener != null) {
            mOnSelectListener.onSelect(value);
        }
    }
}
