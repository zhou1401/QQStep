package com.kaige.qqsetup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/7/28.
 */

public class QQStepView extends View {

    private int mOuterColor = Color.RED;
    private int mInnerColor = Color.BLUE;
    private int mBorderWidth = 20;//20px
    private int mStepTextSize;
    private int mStepTextColor;

    private Paint mOutPaint, mInnerPaint, mTextPaint;

    //总共的，当前的步数
    private int mStepMax = 0;
    private int mCurrentStep = 0;

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //1.分析效果；
        //2.确定自定义属性，编写attrs.xml
        //3.在布局中使用
        //4.在自定义view中获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mOuterColor = array.getColor(R.styleable.QQStepView_outerColor, mOuterColor);
        mInnerColor = array.getColor(R.styleable.QQStepView_innerColor, mInnerColor);
        mBorderWidth = (int) array.getDimension(R.styleable.QQStepView_borderWidth, mBorderWidth);
        mStepTextSize = array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize,mStepTextSize);
        mStepTextColor=array.getColor(R.styleable.QQStepView_stepTextColor,mStepTextColor);
        array.recycle();

        mOutPaint=new Paint();
        mOutPaint.setAntiAlias(true);
        mOutPaint.setStrokeWidth(mBorderWidth);
        mOutPaint.setColor(mOuterColor);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);
        mOutPaint.setStyle(Paint.Style.STROKE);//画笔空心

        mInnerPaint=new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerPaint.setStyle(Paint.Style.STROKE);//画笔空心

        mTextPaint=new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mStepTextColor);
        mTextPaint.setTextSize(mStepTextSize);
        //5onMeasure()
        //6 画外圆弧，内圆弧，文字
        //7 其他
    }
    //5 onMeasure()

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //调用者在布局文件中可能 wrap_content
        //获取模式AT_MOST 40dp

        //宽度高度不一致 取最小值，确保是个正方形
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width>height?height:width,width>height?height:width);
    }
    //6 画外圆弧，内圆弧，文字

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //6.1画外圆弧   分析：圆弧闭合了    思考：边缘没有显示完整 扫描有宽度 mBorderWidth 圆弧
        RectF rectF=new RectF(mBorderWidth/2,mBorderWidth/2,getWidth()-mBorderWidth/2,getHeight()-mBorderWidth/2);

        canvas.drawArc(rectF,135,270,false,mOutPaint);

        if(mStepMax==0)return;
        //6.2 画内圆弧 不能写死，百分比从外面传
        float sweepAngle=(float)mCurrentStep/mStepMax;
        canvas.drawArc(rectF,135,sweepAngle*270,false,mInnerPaint);

        //6.3画文字
        String stepText=mCurrentStep+"";
        Rect textBounds=new Rect();
        mTextPaint.getTextBounds(stepText,0,stepText.length(),textBounds);
        int dx=getWidth()/2-textBounds.width()/2;

        //基线 baseLine
        Paint.FontMetricsInt fontMetricsInt=mTextPaint.getFontMetricsInt();
        int dy=(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
        int baseLine=getHeight()/2+dy;
        canvas.drawText(stepText,dx,baseLine,mTextPaint);
    }
    //7.其他 写几个方法动起来
    public synchronized void setStepMax(int setMax){
        this.mStepMax=setMax;
    }
    public synchronized void setmCurrentStep(int currentStep){
        this.mCurrentStep=currentStep;
        //不断绘制 onDraw（）
        invalidate();
    }
}
