package com.mangues.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by mangues on 16/11/8.
 */

public class VelocityTrackerView extends LinearLayout {

    private TextView mInfo;
    private VelocityTracker mVelocityTracker;
    private int mMaxVelocity;

    private int mPointerId;


    public VelocityTrackerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //用来初始化fling的最大速度，单位是每秒多少像素
        mMaxVelocity = ViewConfiguration.get(context).getMaximumFlingVelocity();

//        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();


        mInfo = new TextView(context);
        mInfo.setLines(4);
        mInfo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
//        mInfo.setTextColor(Color.WHITE);
        addView(mInfo);
    }

    public VelocityTrackerView(Context context) {
        super(context);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        acquireVelocityTracker(event);
        switch (action){
            case MotionEvent.ACTION_UP:
                releaseVelocityTracker();
                break;

            case MotionEvent.ACTION_MOVE:
                /*参数：units  你想要指定的得到的速度单位，如果值为1，代表1毫秒运动了多少像素。如果值为1000，代表
                 * 1秒内运动了多少像素。
                 *
                 * 参数：maxVelocity  该方法所能得到的最大速度，这个速度必须和你指定的units使用同样的单位，而且
                 * 必须是整数。（也就是，你指定一个速度的最大值，如果计算超过这个最大值，就使用这个最大值，否则，使用计算的的结果）
                 */
                mVelocityTracker.computeCurrentVelocity(1000,mMaxVelocity);
                //参数 id   代表返回指定触点的速率
                final float velocityX = mVelocityTracker.getXVelocity(mPointerId);
                final float velocityY = mVelocityTracker.getYVelocity(mPointerId);
                recodeInfo(velocityX, velocityY);
                break;

            case MotionEvent.ACTION_DOWN:
                mPointerId = event.getPointerId(0);
                break;

            case MotionEvent.ACTION_CANCEL:
                releaseVelocityTracker();
                break;
        }


        return super.onTouchEvent(event);
    }



    private void acquireVelocityTracker(final MotionEvent event) {
        if(null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }


    private void releaseVelocityTracker() {
        if(null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private static final String sFormatStr = "velocityX=%f\nvelocityY=%f";

    /**
     * 记录当前速度
     *
     * @param velocityX x轴速度
     * @param velocityY y轴速度
     */
    private void recodeInfo(final float velocityX, final float velocityY) {
        final String info = String.format(sFormatStr, velocityX, velocityY);
        mInfo.setText(info);
    }
}
