package com.example.charline.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * 折线图，自定义view
 * Created by sjhcn on 2016/9/11.
 */
public class CharViewTwenTyFour extends HorizontalScrollView {
    private Paint mLinePaint;
    private Paint mPathPaint;
    private Paint mPointPaint;

    //整个scrollview的宽度
    private int mWidth;
    //时间间隔宽度
    private int mUnitWidth;
    //整个scrollView的高度
    private int mHeight;

    //模拟Y轴数据
    private int[] mTemps = new int[]{200, 210, 220, 230, 240, 250, 270, 280, 290, 280, 270,
            260, 250, 240, 230, 220, 210, 200, 200, 200, 230, 210, 200, 200, 220};

    public CharViewTwenTyFour(Context context, AttributeSet attrs) {
        super(context, attrs);
        mLinePaint = new Paint();
        mLinePaint.setColor(Color.parseColor("#FFD4D1D1"));
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(1);
        mLinePaint.setStyle(Paint.Style.FILL);

        mPathPaint = new Paint();
        mPathPaint.setColor(Color.parseColor("#FF16E95C"));
        mPathPaint.setAntiAlias(true);
        mPathPaint.setStrokeWidth(3);
        mPathPaint.setStyle(Paint.Style.FILL);
//        mPathPaint.setPathEffect(new CornerPathEffect(3));


        mPointPaint = new Paint();
        mPointPaint.setColor(Color.parseColor("#FFFFFF"));
        mPointPaint.setAntiAlias(true);
        //mPointPaint.setStrokeWidth(7);
        mPointPaint.setStyle(Paint.Style.FILL);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mUnitWidth = mWidth / 8;
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawLine(0, mHeight - 50, mWidth * 3 + mUnitWidth, mHeight - 50, mLinePaint);
        canvas.drawLine(0, mHeight - 175, mWidth * 3 + mUnitWidth, mHeight - 180, mLinePaint);
        canvas.drawLine(0, mHeight - 280, mWidth * 3 + mUnitWidth, mHeight - 280, mLinePaint);


        int spacePath = 0;
        Path path = new Path();
//        path.moveTo(mUnitWidth / 2, 420 - mTemps[0]);
        for (int i = 0; i < mTemps.length - 1; i++) {
            canvas.drawLine(mUnitWidth / 2 + spacePath, 418 - mTemps[i],
                    mUnitWidth / 2 + spacePath + mUnitWidth, 420 - mTemps[i + 1], mPathPaint);
            spacePath += mUnitWidth;
        }
        int spacePoint = 0;
        for (int i = 0; i < mTemps.length; i++) {
            //canvas.drawPoint(mUnitWidth / 2 + space, 400 - mTemps[i], mPointPaint);
            canvas.drawCircle(mUnitWidth / 2 + spacePoint, 420 - mTemps[i], 5, mPointPaint);
            spacePoint += mUnitWidth;
        }
        super.onDraw(canvas);
    }
}
