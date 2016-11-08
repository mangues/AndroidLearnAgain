package com.mangues.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by mangues on 16/11/8.
 */

public class ScrollerView extends LinearLayout {

    public ScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }


    int mLastX;
    int mLastY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //相对于手机屏幕的坐标
        int x =(int) event.getRawX();
        int y =(int) event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:

                break;

            case MotionEvent.ACTION_DOWN:

                break;

            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;

                //兼容库
//                float trabslationX = ViewHelper.getTranslationX(this) + deltaX;
//                float trabslationY = ViewHelper.getTranslationY(this) + deltaY;
//                ViewHelper.setTranslationX(this,trabslationX);
//                ViewHelper.setTranslationY(this,trabslationY);

                smootthScrollTo(deltaX,deltaY);
                break;

        }



        mLastX = x;
        mLastY = y;

        return super.onTouchEvent(event);
    }

    Scroller scroller = new Scroller(getContext());

    private void smootthScrollTo(int destX,int destY){
        int scrollX = getScrollX();
        int deltaX = destX - scrollX;
        //1000ms内滑向destX，效果是慢慢滑动
        scroller.startScroll(scrollX,0,deltaX,0,1000);
    }

    @Override
    public void computeScroll() {
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            postInvalidate();
        }
    }


}
