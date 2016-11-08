package com.mangues.view.view;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by mangues on 16/11/8.
 */

public class ScrollerLayout extends ViewGroup {

    //用于完成滚动操作的实例
    private Scroller mScroller;

    //判断拖动的最小移动像素
    private int mTouchSlop;

    /**
     * 手机按下时的屏幕坐标
     */
    private float mXDown;

    //手机当时所处的屏幕坐标
    private float mXMove;

    private float mXLastMove;

    /**
     * 界面可滚动的左边界
     */
    private int leftBorder;

    /**
     * 界面可滚动的右边界
     */
    private int rightBorder;


    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        // 获取TouchSlop值
        mTouchSlop = configuration.getScaledPagingTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 为ScrollerLayout中的每一个子控件测量大小
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                // 为ScrollerLayout中的每一个子控件在水平方向上进行布局
                childView.layout(i * childView.getMeasuredWidth(), 0, (i + 1) * childView.getMeasuredWidth(), childView.getMeasuredHeight());
            }
            // 初始化左右边界值,-100 +100  回弹效果
            leftBorder = getChildAt(0).getLeft()-100;
            rightBorder = getChildAt(getChildCount() - 1).getRight()+100;
        }
    }

    /**
     * 在整个touch过程中只执行一遍
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getRawX();
                mXLastMove = mXDown;
                break;
//            case MotionEvent.ACTION_MOVE:
//                mXMove = ev.getRawX();
//                float diff = Math.abs(mXMove - mXDown);
//                mXLastMove = mXMove;
//                // 当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
//                if (diff > mTouchSlop) {
//                    return true;
//                }
//                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 你好，一般在自定义控件的时候getMeasuredWidth/getMeasuredHeight它的赋值在View的setMeasuredDimension中，
     * 所以有时可以在onMeasure方法中看到利用getMeasuredWidth/getMeasuredHeight初始化别的参数。
     * 而getWidth/getHeight一直在onLayout完成后才会被赋值。一般情况下，如果都完成了赋值，两者值是相同的，你可以测试下
     *
     * getLeft getRight getTop getBottom getWidth getHeight 都是视图坐标系
     * @param event
     * @return
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                int scrolledX = (int) (mXLastMove - mXMove);
                if (getScrollX() + scrolledX < leftBorder) {
                    scrollTo(leftBorder, 0);
                    return true;
                    //getScrollX() 就是当前view的左上角相对于母视图的左上角的X轴偏移量
                } else if (getScrollX() + getWidth() + scrolledX > rightBorder) {
                    scrollTo(rightBorder - getWidth(), 0);
                    return true;
                }
                scrollBy(scrolledX, 0);
                mXLastMove = mXMove;
                break;
            case MotionEvent.ACTION_UP:
                // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = targetIndex * getWidth() - getScrollX();


                // 第二步，调用startScroll()方法来初始化滚动数据并刷新界面
                /**
                 *   startX 水平方向滚动的偏移值，以像素为单位。正值表明滚动将向左滚动

                 　　startY 垂直方向滚动的偏移值，以像素为单位。正值表明滚动将向上滚动

                 　　dx 水平方向滑动的距离，正值会使滚动向左滚动

                 　　dy 垂直方向滑动的距离，正值会使滚动向上滚动
                 */
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
