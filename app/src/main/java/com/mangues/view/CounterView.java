package com.mangues.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by mangues on 16/11/3.
 */

public class CounterView extends View implements View.OnClickListener{

    private Paint mPaint;
    // 用于获取文字的宽和高
    private Rect mBounds;
    private int mCount = 0;
    public CounterView(Context context,  AttributeSet attrs) {
        super(context,attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBounds = new Rect();

        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0,0,getWidth(),getHeight(),mPaint);

        mPaint.setColor(Color.YELLOW);
        mPaint.setTextSize(50);

        String  text = String.valueOf(mCount);
        mPaint.getTextBounds(text,0,text.length(),mBounds);
        float textWidth = mBounds.width();
        float textHeight = mBounds.height();

        canvas.drawText(text,getWidth()/2-textWidth/2,getHeight()/2 + textHeight/2,mPaint);




    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
//        switch (event.)
//
//
//
//    }

    @Override
    public void onClick(View view) {
        mCount++;
        invalidate();
    }
}
