package com.xdu.xhin.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.xdu.xhin.library.R;


public class ChangeColorItem extends View {

    private int mColor = 0xFF45C01A;
    private Bitmap mIconBitmap;
    private String mText = "";

    private Bitmap mBitmap;

    private float mAlpha;

    private Rect mIconRect;
    private Rect mTextBound;
    private Paint mTextPaint;

    public ChangeColorItem(Context context) {
        this(context, null);
    }

    public ChangeColorItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 获取自定义属性的值
     */
    public ChangeColorItem(Context context, AttributeSet attrs,
                           int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取自定义属性的值
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.ChangeColorItem);
        //先设置默认的字体大小
        int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.ChangeColorItem_item_icon) {
                BitmapDrawable drawable = (BitmapDrawable) typedArray.getDrawable(attr);
                if (drawable != null)
                    mIconBitmap = drawable.getBitmap();

            } else if (attr == R.styleable.ChangeColorItem_item_color) {
                mColor = typedArray.getColor(attr, 0xFF45C01A);

            } else if (attr == R.styleable.ChangeColorItem_item_text) {
                mText = typedArray.getString(attr);

            } else if (attr == R.styleable.ChangeColorItem_item_text_size) {
                mTextSize = (int) typedArray.getDimension(attr, TypedValue
                        .applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                                getResources().getDisplayMetrics()));

            }

        }
        //用完记得回收
        typedArray.recycle();

        //文本框的矩形
        mTextBound = new Rect();
        //文本的画笔
        mTextPaint = new Paint();
        //启用绘图反锯齿
        mTextPaint.setAntiAlias(true);
        //启用防抖动
        mTextPaint.setDither(true);
        //设置文本大小
        mTextPaint.setTextSize(mTextSize);
        //设置文本颜色
        mTextPaint.setColor(0Xff555555);
        //设置要绘制的文本和文本框
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);

        mIconRect = new Rect();

    }


    //测量View
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft()
                - getPaddingRight(), getMeasuredHeight() - getPaddingTop()
                - getPaddingBottom() - mTextBound.height());

        int left = getMeasuredWidth() / 2 - iconWidth / 2;
        int top = getMeasuredHeight() / 2 - (mTextBound.height() + iconWidth) / 2;
        mIconRect.set(left, top, left + iconWidth, top + iconWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);
        int alpha = (int) Math.ceil(255 * mAlpha);
        // 内存去准备mBitmap , setAlpha , 纯色 ，xfermode ， 图标
        setupTargetBitmap(alpha);
        // 1、绘制原文本 ； 2、绘制变色的文本
        drawSourceText(canvas, alpha);
        drawTargetText(canvas, alpha);
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    /**
     * 绘制变色的文本
     */
    private void drawTargetText(Canvas canvas, int alpha) {
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(alpha);
        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        int y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mText, x, y, mTextPaint);
    }

    /**
     * 绘制原文本
     */
    private void drawSourceText(Canvas canvas, int alpha) {
        mTextPaint.setColor(0Xff555555);
        mTextPaint.setAlpha(255 - alpha);
        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        int y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mText, x, y, mTextPaint);
    }

    /**
     * 在内存中绘制可变色的Icon
     */
    private void setupTargetBitmap(int alpha) {
        //创建一个Bitmap,并设置为32位真彩色
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
        //在原Bitmap上创建一个画板
        Canvas mCanvas = new Canvas(mBitmap);
        //新建一个画笔并设置其参数
        Paint mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);
        //画板上画出原图像
        mCanvas.drawRect(mIconRect, mPaint);
        //Xfermode 共有16种模式,现在选择DST_IN模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //设置画笔透明度为完全透明
        mPaint.setAlpha(255);
        //画板上再画一个图像
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
    }

    private static final String INSTANCE_STATUS = "instance_status";
    private static final String STATUS_ALPHA = "status_alpha";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
        bundle.putFloat(STATUS_ALPHA, mAlpha);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mAlpha = bundle.getFloat(STATUS_ALPHA);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
            return;
        }
        super.onRestoreInstanceState(state);
    }


    //供外部调用的设置透明度(图像颜色深浅)的方法
    public void setIconAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidateView();
    }

    //重绘
    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

}
