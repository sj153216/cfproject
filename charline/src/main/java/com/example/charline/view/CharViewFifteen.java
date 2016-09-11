package com.example.charline.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;

/**
 * 折线图，自定义view
 * Created by sjhcn on 2016/9/11.
 */
public class CharViewFifteen extends HorizontalScrollView {
    private Paint mLinePaint;
    //最高温
    private Paint mPathPaintdHigh;
    //最低温
    private Paint mPathPaintdLow;
    private Paint mPointPaint;
    //绘制温度
    private Rect mTextBound;
    private Paint mTextPaint;
    //设置文字大小
    private int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
    //要绘制的文字
    private String mText = "32°";

    //整个scrollview的宽度
    private int mWidth;
    //时间间隔宽度
    private int mUnitWidth;
    //整个scrollView的高度
    private int mHeight;

    //模拟Y轴数据_高温
    private int[] mTempsHigh = new int[]{150, 160, 150, 140, 170, 180, 170, 170, 160, 150, 140, 170, 150, 160, 160, 170};
    //模拟Y轴数据_低温
    private int[] mTempsLow = new int[]{250, 260, 250, 240, 270, 280, 270, 270, 260, 250, 240, 270, 250, 250, 260, 270};
    //高温_温度
    private String[] mTempsHighText = new String[]{"32°", "31°", "32°", "33°", "30°", "29°", "30°", "30°",
            "32°", "32°", "33°", "30°", "32°", "31°", "31°", "30°"};
    //低温_温度
    private String[] mTempsLowText = new String[]{"22°", "21°", "22°", "23°", "20°", "19°", "20°", "20°",
            "21°", "22°", "23°", "20°", "22°", "21°", "21°", "20°"};

    public CharViewFifteen(Context context, AttributeSet attrs) {
        super(context, attrs);
        mLinePaint = new Paint();
        mLinePaint.setColor(Color.parseColor("#FFD4D1D1"));
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(1);
        mLinePaint.setStyle(Paint.Style.FILL);

        mPathPaintdHigh = new Paint();
        mPathPaintdHigh.setColor(Color.parseColor("#FF16E95C"));
        mPathPaintdHigh.setAntiAlias(true);
        mPathPaintdHigh.setStrokeWidth(3);
        mPathPaintdHigh.setStyle(Paint.Style.FILL);
//        mPathPaint.setPathEffect(new CornerPathEffect(3));

        mPathPaintdLow = new Paint();
        mPathPaintdLow.setColor(Color.parseColor("#FF6845B9"));
        mPathPaintdLow.setAntiAlias(true);
        mPathPaintdLow.setStrokeWidth(3);
        mPathPaintdLow.setStyle(Paint.Style.FILL);

        mPointPaint = new Paint();
        mPointPaint.setColor(Color.parseColor("#FFFFFF"));
        mPointPaint.setAntiAlias(true);
        //mPointPaint.setStrokeWidth(7);
        mPointPaint.setStyle(Paint.Style.FILL);


        mTextBound = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.parseColor("#ffffff"));
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true);
        //通过画笔给mTextBound赋值
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        ViewGroup childRl = (ViewGroup) getChildAt(0);
        for (int i = 0; i < childRl.getChildCount(); i++) {
            ViewGroup childLl = (ViewGroup) childRl.getChildAt(i);
            for (int j = 0; j < childLl.getChildCount(); j++) {
                View child = childLl.getChildAt(j);
                ViewGroup.LayoutParams lp = child.getLayoutParams();
                lp.width = metrics.widthPixels / 6;
                child.setLayoutParams(lp);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mUnitWidth = mWidth / 6;
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawLine(0, 0, mUnitWidth * 16, 0, mLinePaint);
        int spaceLine = 0;
        for (int i = 0; i < mTempsHigh.length; i++) {
            canvas.drawLine(mUnitWidth + spaceLine, 0, mUnitWidth + spaceLine, getHeight(), mLinePaint);
            spaceLine += mUnitWidth;
        }

        int spacePathHigh = 0;
        //绘制最高温度
        for (int i = 0; i < mTempsHigh.length - 1; i++) {
            canvas.drawLine(mUnitWidth / 2 + spacePathHigh, mTempsHigh[i],
                    mUnitWidth / 2 + spacePathHigh + mUnitWidth, mTempsHigh[i + 1], mPathPaintdHigh);
            spacePathHigh += mUnitWidth;
        }
        //绘制最低温度
        int spacePathLow = 0;
        for (int i = 0; i < mTempsLow.length - 1; i++) {
            canvas.drawLine(mUnitWidth / 2 + spacePathLow, mTempsLow[i],
                    mUnitWidth / 2 + spacePathLow + mUnitWidth, mTempsLow[i + 1], mPathPaintdLow);
            spacePathLow += mUnitWidth;
        }
        int spacePointHigh = 0;
        for (int i = 0; i < mTempsHigh.length; i++) {
            //canvas.drawPoint(mUnitWidth / 2 + space, 400 - mTemps[i], mPointPaint);
            canvas.drawCircle(mUnitWidth / 2 + spacePointHigh, mTempsHigh[i], 5, mPointPaint);
            spacePointHigh += mUnitWidth;
        }
        int spaceLow = 0;
        for (int i = 0; i < mTempsLow.length; i++) {
            //canvas.drawPoint(mUnitWidth / 2 + space, 400 - mTemps[i], mPointPaint);
            canvas.drawCircle(mUnitWidth / 2 + spaceLow, mTempsLow[i], 5, mPointPaint);
            spaceLow += mUnitWidth;
        }


        //绘制温度
        int spaceTextHigh = 0;
        for (int i = 0; i < mTempsHighText.length; i++) {
            canvas.drawText(mTempsHighText[i], (mUnitWidth - mTextBound.width()) / 2 + spaceTextHigh,
                    mTempsHigh[i] - 2 * mTextBound.height(), mTextPaint);
            spaceTextHigh += mUnitWidth;
        }
        int spaceTextLow = 0;
        for (int i = 0; i < mTempsLowText.length; i++) {
            canvas.drawText(mTempsLowText[i], (mUnitWidth - mTextBound.width()) / 2 + spaceTextLow,
                    mTempsLow[i] - 2 * mTextBound.height(), mTextPaint);
            spaceTextLow += mUnitWidth;
        }
        super.onDraw(canvas);
    }
}
