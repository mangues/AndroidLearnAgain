package com.mangues.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.util.Calendar;
import android.util.AttributeSet;
import android.view.View;

import com.mangues.utils.UnitsUtil;

import java.util.Date;

import mangues.com.rxandroiddemo.R;

/**
 * Created by mangues on 16/11/4.
 */

public class ClockView extends View{

    private float mWatchPadding = 5;
    /**表盘与刻度边距*/
    private float mWatchScalePadding = 5;
    /**表盘半径*/
    private float mWatchRadius = 250;
    /**表盘刻度长度*/
    private float mWatchScaleLength;
    /**表盘刻度颜色*/
    private int mWatchScaleColor = Color.BLACK;
    /**表盘整点刻度长度*/
    private float mHourScaleLength = 8;
    /**表盘整点刻度颜色*/
    private int mHourScaleColor = Color.BLUE;
    /**表盘时针颜色*/
    private int mHourPointColor = Color.BLACK;
    /**表盘时针长度*/
    private float mHourPointLength = 100;
    /**表盘分针颜色*/
    private int mMinutePointColor = Color.BLACK;
    /**表盘分针长度*/
    private float mMinutePointLength = 130;
    /**表盘秒针颜色*/
    private int mSecondPointColor = Color.RED;
    /**表盘秒针长度*/
    private float mSecondPointLength = 160;
    /**表盘尾部指针长度*/
    private float mEndPointLength;
    /**表盘数字颜色*/
    private int mTimeTextColor = Color.BLACK;
    /**表盘数字大小*/
    private int mTimeTextSize = 15;
    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WatchView);
        int n = typedArray.getIndexCount();
        for (int i = 0;i<n;i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.WatchView_watchRadius:
                    mWatchRadius = typedArray.getDimensionPixelSize(attr, UnitsUtil.dip2px(context, 60));
                    break;
                case R.styleable.WatchView_watchPadding:
                    mWatchPadding = typedArray.getDimensionPixelSize(attr,UnitsUtil.dip2px(context,5));
                    break;
                case R.styleable.WatchView_watchScalePadding:
                    mWatchScalePadding = typedArray.getDimensionPixelSize(attr,UnitsUtil.dip2px(context,3));
                    break;
                case R.styleable.WatchView_watchScaleLength:
                    mWatchScaleLength = typedArray.getDimensionPixelSize(attr,UnitsUtil.dip2px(context,5));
                    break;
                case R.styleable.WatchView_watchScaleColor:
                    mWatchScaleColor = typedArray.getColor(attr, Color.parseColor("#50000000"));
                    break;
                case R.styleable.WatchView_watchHourScaleLength:
                    mHourScaleLength = typedArray.getDimensionPixelSize(attr,UnitsUtil.dip2px(context,10));
                    break;
                case R.styleable.WatchView_watchHourScaleColor:
                    mHourScaleColor = typedArray.getColor(attr,Color.BLACK);
                    break;
                case R.styleable.WatchView_hourPointLength:
                    mHourPointLength = typedArray.getDimensionPixelSize(attr,UnitsUtil.dip2px(context,35));
                    break;
                case R.styleable.WatchView_hourPointColor:
                    mHourPointColor = typedArray.getColor(attr,Color.BLACK);
                    break;
                case R.styleable.WatchView_minutePointLength:
                    mMinutePointLength = typedArray.getDimensionPixelSize(attr,UnitsUtil.dip2px(context,40));
                    break;
                case R.styleable.WatchView_minutePointColor:
                    mMinutePointColor = typedArray.getColor(attr,Color.BLACK);
                    break;
                case R.styleable.WatchView_secondPointLength:
                    mSecondPointLength = typedArray.getDimensionPixelSize(attr,UnitsUtil.dip2px(context,50));
                    break;
                case R.styleable.WatchView_secondPointColor:
                    mSecondPointColor = typedArray.getColor(attr,Color.BLUE);
                    break;
                case R.styleable.WatchView_timeTextSize:
                    mTimeTextSize = typedArray.getDimensionPixelSize(attr,UnitsUtil.dip2px(context,15));
                    break;
                case R.styleable.WatchView_timeTextColor:
                    mTimeTextColor = typedArray.getColor(attr,Color.BLACK);
                    break;
            }
        }
        //程序在运行时维护了一个 TypedArray的池，程序调用时，会向该池中请求一个实例，用完之后，调用 recycle() 方法来释放该实例，从而使其可被其他模块复用。

        //那为什么要使用这种模式呢？答案也很简单，TypedArray的使用场景之一，就是上述的自定义View，会随着 Activity的每一次Create而Create，因此，需要系统频繁的创建array，对内存和性能是一个不小的开销，如果不使用池模式，每次都让GC来回收，很可能就会造成OutOfMemory。

        //这就是使用池+单例模式的原因，这也就是为什么官方文档一再的强调：使用完之后一定 recycle,recycle,recycle。

        typedArray.recycle();

    }



    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wrapContentSize = 1000;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //* wrap_parent -> MeasureSpec.AT_MOST
        //* match_parent -> MeasureSpec.EXACTLY
        // * 具体值 -> MeasureSpec.EXACTLY
        if (widthMode == MeasureSpec.UNSPECIFIED && heightMode == MeasureSpec.UNSPECIFIED){
            wrapContentSize = (int) Math.max(wrapContentSize,mWatchRadius+mWatchPadding);
            setMeasuredDimension(wrapContentSize,wrapContentSize);
        }else {
            setMeasuredDimension(widthSize,heightSize);
        }
    }


    private Paint mPaint;
    private void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth()/2,getHeight()/2);

        paintWatchBoard(canvas); //画表盘
        paintScale(canvas); //画刻度
        paintPoint(canvas); //画指针
        canvas.drawCircle(0,0,15,mSecondPaint); //为了美观，也让表盘更接近我们显示生活中的样子，我在圆盘中心画了一个大红圆点装饰秒针
        postInvalidateDelayed(1000); //每隔一秒钟画一次

    }



   /**
    * 画表盘
    * @param canvas
    */
    private void paintWatchBoard(Canvas canvas){
        initPaint();
        //：每次画图之前都要先调用canvas.save()方法，保存画笔属性，画完之后要调用canvas.restore()方法，重置画笔属性
        canvas.save();
        canvas.drawCircle(0,0,mWatchRadius,mPaint); //画圆盘
        canvas.restore();
    }

    private String[] mTimes = {"XII","Ⅰ","Ⅱ","Ⅲ","Ⅳ","Ⅴ","Ⅵ","Ⅶ","Ⅷ","Ⅸ","Ⅹ","XI"};


    /**
     * 画刻度及整点数字
     * @param canvas
     */
    private void paintScale(Canvas canvas){
        int lineLength; //刻度线长度
        canvas.save();
        for (int i = 0;i<60;i++){
            if (i%5 == 0){//整点刻度下画笔相关属性
                mPaint.setStrokeWidth(UnitsUtil.dip2px(getContext(),1.5f));
                mPaint.setColor(mHourScaleColor);
                lineLength = UnitsUtil.dip2px(getContext(),8);
                canvas.drawLine(0,-mWatchRadius+mWatchScalePadding,0,-mWatchRadius+mWatchScalePadding+lineLength,mPaint);
                mPaint.setColor(mTimeTextColor);
                mPaint.setTextSize(mTimeTextSize);
                canvas.drawText(mTimes[i/5],-mTimeTextSize/2,-mWatchRadius+mWatchScalePadding + lineLength+mTimeTextSize,mPaint);//整点的位置标上整点时间数字
            }else {//非整点刻度下画笔相关属性
                mPaint.setStrokeWidth(UnitsUtil.dip2px(getContext(),0.8f));
                mPaint.setColor(mWatchScaleColor);
                lineLength = UnitsUtil.dip2px(getContext(),5);
                canvas.drawLine(0,-mWatchRadius+mWatchScalePadding,0,-mWatchRadius+mWatchScalePadding+lineLength,mPaint);
            }
            canvas.rotate(6);//每次画完一个刻度线，画笔顺时针旋转6度（360/60，相邻两刻度之间的角度差为6度）
        }
        canvas.restore();
    }



    private Paint mHourPaint;
    private Paint mMinutePaint;
    private Paint mSecondPaint;
    /**
     * 初始化指针
     */
    private void initPointPaint(){
        mHourPaint = new Paint();
        mHourPaint.setAntiAlias(true);
        mHourPaint.setStyle(Paint.Style.FILL);
        mHourPaint.setStrokeWidth(16);
        mHourPaint.setColor(mHourPointColor);

        mMinutePaint = new Paint();
        mMinutePaint.set(mHourPaint);
        mMinutePaint.setStrokeWidth(12);
        mMinutePaint.setColor(mMinutePointColor);

        mSecondPaint = new Paint();
        mSecondPaint.set(mHourPaint);
        mSecondPaint.setStrokeWidth(7);
        mSecondPaint.setColor(mSecondPointColor);
        mEndPointLength = mWatchRadius/6; //（修饰部分）指针尾部长度，定义为表盘半径的六分之一
    }



   /**
     * 画指针
     * @param canvas
     */
    private void paintPoint(Canvas canvas){
        initPointPaint();
//       Calendar c = Calendar.getInstance(); //取当前时间
        Date date = new Date();




        int hour = date.getHours();
        int minute = date.getMinutes();
        int second = date.getSeconds();
        //绘制时针
        canvas.save();
        canvas.rotate(hour*30);
        canvas.drawLine(0,0,0,-mHourPointLength,mHourPaint);
        canvas.drawLine(0,0,0,mEndPointLength,mHourPaint);
        canvas.restore();
        //绘制分针
        canvas.save();
        canvas.rotate(minute*6);
        canvas.drawLine(0,0,0,-mMinutePointLength,mMinutePaint);
        canvas.drawLine(0,0,0,mEndPointLength,mMinutePaint);
        canvas.restore();
        //绘制秒针
        canvas.save();
        canvas.rotate(second*6);
        canvas.drawLine(0,0,0,-mSecondPointLength,mSecondPaint);
        canvas.drawLine(0,0,0,mEndPointLength,mSecondPaint);
        canvas.restore();
    }
}
