package com.sjhcn.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by sjhcn on 2016/9/4.
 */
public class ViewPagerIndicator extends LinearLayout {
    //三角形的初始偏移量
    private int mInitTranslationX;
    //三角形的移动偏移量
    private float mTranslationX;
    //三角形的width是tab的六分之一
    private float RADIO = 1f / 6;
    //三角形的宽度
    private int mTriangleWidth;
    //三角形的高度
    private int mTriangleHeight;
    //三角形路径
    private Path mPath;
    private Paint mPaint;
    private OnPageChangeListener mListener;

    /**
     * 标题正常时的颜色
     */
    private static final int COLOR_TEXT_NORMAL = 0x77FFFFFF;
    /**
     * 标题选中时的颜色
     */
    private static final int COLOR_TEXT_HIGHLIGHTCOLOR = 0xFFFFFFFF;

    private ViewPager mViewPager;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {

        super(context, attrs);
        // 初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#FFFFFF"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));

    }


    /**
     * 一些初始化操作
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }
        for (int i = 0; i < childCount; i++) {
            //给child设置点击事件
            View view = getChildAt(i);
            final int j = i;
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //在这里面去做三角形的初始化操作
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleWidth = (int) (w / 3 * RADIO);
        mTriangleHeight = mTriangleWidth / 2;
        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
        // 初始时的偏移量
        mInitTranslationX = getWidth() / 3 / 2 - mTriangleWidth / 2;

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        //先将三角形绘制出来
        canvas.save();
        canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 1);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }


    /**
     * 开放接口，从外部设置viewpager
     *
     * @param viewPager
     * @param pos
     */
    public void setViewPager(ViewPager viewPager, int pos) {
        this.mViewPager = viewPager;
        this.mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 滚动
                scroll(position, positionOffset);

                // 回调
                if (mListener != null) {
                    mListener.onPageScrolled(position,
                            positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                // 设置字体颜色高亮
                resetTextViewColor();
                highLightTextView(position);

                // 回调
                if (mListener != null) {
                    mListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mListener != null) {
                    mListener.onPageScrollStateChanged(state);
                }
            }
        });

        // 设置当前页
        mViewPager.setCurrentItem(pos);
        // 高亮
        highLightTextView(pos);
    }

    /**
     * 指示器跟随手指滚动，以及容器滚动
     *
     * @param position
     * @param offset
     */
    public void scroll(int position, float offset) {
        /**
         * <pre>
         *  0-1:position=0 ;1-0:postion=0;
         * </pre>
         */
        // 不断改变偏移量，invalidate
        mTranslationX = getWidth() / 3 * (position + offset);

//        WindowManager wm = (WindowManager) QRcodeApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
//        int tabWidth = wm.getDefaultDisplay().getWidth() / 3;
//
//        // 容器滚动，当移动到倒数最后一个的时候，开始滚动
//        if (offset > 0 && position >= (3 - 2)
//                && getChildCount() > 3) {
////            if (mTabVisibleCount != 1)
////            {
//            this.scrollTo((position - 1) * tabWidth
//                    + (int) (tabWidth * offset), 0);
////            } else
////            // 为count为1时 的特殊处理
////            {
////                this.scrollTo(
////                        position * tabWidth + (int) (tabWidth * offset), 0);
////            }
//        }

        invalidate();
    }

    /**
     * 高亮文本
     *
     * @param position
     */
    protected void highLightTextView(int position) {
        View view = getChildAt(position);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(COLOR_TEXT_HIGHLIGHTCOLOR);
        }
    }

    /**
     * 重置文本颜色
     */
    private void resetTextViewColor() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(COLOR_TEXT_NORMAL);
            }
        }
    }

    public void setOnPagerChangerListener(OnPageChangeListener listener) {
        this.mListener = listener;
    }

    /**
     * 因为在内部使用了监听，这里开放给用户
     */
    public interface OnPageChangeListener {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        void onPageSelected(int position);

        void onPageScrollStateChanged(int state);
    }
}
