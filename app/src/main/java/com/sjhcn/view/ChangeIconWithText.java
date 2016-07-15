package com.sjhcn.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.sjhcn.qrcode.R;


/**
 * Created by sjhcn on 2016/7/11.
 */

public class ChangeIconWithText extends View {
    private int mColor = 0x4EC963;
    private String mText = "我的二维码";
    private int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
    private Bitmap mIconBitmap;

    //在内存中会图需要的变量
    private Paint mPaint;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private float mAlpha = 1.0f;

    private Rect mIconRect;
    private Rect mTextBound;
    private Paint mTextPaint;
    private int mIconWidth;

    public ChangeIconWithText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangeIconWithText(Context context) {
        this(context, null);
    }

    /**
     * 这里去初始化
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public ChangeIconWithText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ChangeIconWithText);
        //自定义属性这里去获取
        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.ChangeIconWithText_mycolor:
                    mColor = ta.getColor(attr, 0x4EC963);
                    break;
                case R.styleable.ChangeIconWithText_myicon:
                    BitmapDrawable drawable = (BitmapDrawable) ta.getDrawable(attr);
                    mIconBitmap = drawable.getBitmap();
                    break;
                case R.styleable.ChangeIconWithText_text:
                    mText = ta.getString(attr);
                    break;
                case R.styleable.ChangeIconWithText_text_size:
                    mTextSize = (int) ta.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    break;
            }
        }
        ta.recycle();
        mTextBound = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setColor(0xff333333);
        mTextPaint.setTextSize(mTextSize);
        //通过画笔给mTextBound赋值
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }


    public void setAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - mTextBound.height();
        mIconWidth = Math.min(width, height);
        int left = getMeasuredWidth() / 2 - mIconWidth / 2;
        int top = getMeasuredHeight() / 2 - (mTextBound.height() + mIconWidth) / 2;
        mIconRect = new Rect(left, top, left + mIconWidth, top + mIconWidth);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制原生图片
        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);
        //绘制变色的图片,在内存中准备mBitmap，setAlpha,绘制纯色，设置xfermode,绘制图标
        int alpha = (int) Math.ceil(255 * mAlpha);
        drawTargetBitmap(alpha);
        // 再用此方法的canvas画出mBitmap
        canvas.drawBitmap(mBitmap, 0, 0, null);
        drawSourceText(canvas, alpha);
        drawTargetText(canvas, alpha);

    }

    /**
     * 绘制目标文本
     *
     * @param canvas
     * @param alpha
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
     *
     * @param canvas
     * @param alpha
     */
    private void drawSourceText(Canvas canvas, int alpha) {
        mTextPaint.setColor(0xff333333);
        mTextPaint.setAlpha(255 - alpha);
        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        int y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mText, x, y, mTextPaint);
    }


    /**
     * 绘制变色的图片
     */
    private void drawTargetBitmap(int alpha) {
        //首先在内存中绘制bitmap
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        //从bitmap中拿到canvas
        mCanvas = new Canvas(mBitmap);
        //设置画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);
        // 在mIconRect范围内绘制纯色
        mCanvas.drawRect(mIconRect, mPaint);
        // 设置xfermode
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);

    }

}