package com.example.saravananthangamari.donutgraph.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.saravananthangamari.donutgraph.model.DonutGraphData;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DonutView extends View {
    private static List<Integer> listAngles;
    float rectWidth,rectHeight;
    Paint paint,textPaint,shadowPaint;
    RectF rect;
    Bitmap bitmap;
    int midx,midy,radius,innerRadius;
    float currentStartArcPosition=270f;
    float currentSweep;
    int no_of_iteration=0;
    float totalValues=0;
    int shadowWidth=0;
    DonutGraphData data;
    private ValueAnimator mTimerAnimator;
    Canvas canvas1;
    public DonutView(Context context) {
        super(context);
    }

    public DonutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DonutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    private void init(@Nullable AttributeSet attrs){
        rect=new RectF();
        rect.left=0;
        rect.top=0;
        rect.bottom=rect.top+data.getCanvasWidth();
        rect.right=rect.left+data.getCanvasHeight();
        paint=new Paint(paint.ANTI_ALIAS_FLAG);
        textPaint=new Paint(textPaint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        shadowPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = Bitmap.createBitmap(1200, 1200, Bitmap.Config.ARGB_8888);
        canvas1=new Canvas(bitmap);
        mTimerAnimator=new ValueAnimator();
    }

    @Override
    protected void onDraw(Canvas canvas){

        if(data==null){
    return;
    }else {

            shadowWidth=50;
    canvas.drawColor(Color.TRANSPARENT);
    midx=canvas.getWidth()/2;
    midy=canvas.getHeight()/2;
    if(midx<midy) radius=midx; else radius=midy;
    radius=radius-shadowWidth;
    currentStartArcPosition=270f;
    innerRadius=radius-data.getDonutWidth();

    BlurMaskFilter bmf=new BlurMaskFilter(10000,BlurMaskFilter.Blur.OUTER);
            shadowPaint.setMaskFilter(bmf);
            shadowPaint.setColor(Color.BLACK);
            shadowPaint.setAlpha(51);
            shadowPaint.setStyle(Paint.Style.STROKE);
            shadowPaint.setStrokeWidth(shadowWidth);



            for (int i = 0; i <no_of_iteration; i++) {
        Path p=new Path();
        p.reset();
        paint.setColor(data.getColors().get(i));
        currentSweep=(data.getPercentage().get(i)/totalValues)*360;

        rect.set(midx-radius,midy-radius,midx+radius,midy+radius);
        drawArcLocal(p,currentSweep,currentStartArcPosition,currentSweep);
        canvas.drawArc(rect,currentStartArcPosition,currentSweep,false,shadowPaint);

        rect.set(midx-innerRadius,midy-innerRadius,midx+innerRadius,midy+innerRadius);
        drawArcLocal(p,currentSweep,currentStartArcPosition+currentSweep,-currentSweep);

        p.close();

        canvas.drawPath(p,paint);


        paint.setColor(Color.TRANSPARENT);
        rect.set(midx-radius+(data.getDonutWidth()/1.5f),midy-radius+(data.getDonutWidth()/1.5f),midx+radius-(data.getDonutWidth()/1.5f),midy+radius-(data.getDonutWidth()/1.5f));
        Path p1=new Path();

        drawArcLocal(p1,currentSweep,currentStartArcPosition,currentSweep);
        //canvas.drawPath(p1,paint);
        PathMeasure pm=new PathMeasure(p1,false);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(data.getDonutWidth()/4);

                while(pm.getLength()<textPaint.measureText(data.getFieldName().get(i))){
                 textPaint.setTextSize(textPaint.getTextSize()-2);
                }
                canvas.drawTextOnPath(data.getFieldName().get(i),p1,(int)(pm.getLength()-textPaint.measureText(data.getFieldName().get(i)))/2,-data.getDonutWidth()/5,textPaint);

        textPaint.setColor(Color.RED);
        textPaint.setTextSize(data.getDonutWidth()/4);
        while(pm.getLength()<textPaint.measureText(String.valueOf(data.getPercentage().get(i)))){
                    textPaint.setTextSize(textPaint.getTextSize()-2);
                }
        canvas.drawTextOnPath(String.valueOf(data.getPercentage().get(i)),p1,(int)(pm.getLength()-textPaint.measureText(String.valueOf(data.getPercentage().get(i))))/2,data.getDonutWidth()/4,textPaint);
        p1.close();

             currentStartArcPosition=currentStartArcPosition+currentSweep;
            }
            /*canvas1.drawColor(Color.TRANSPARENT);
            canvas1.drawBitmap(bitmap, 0, 0, null);*/


         /*  paint.setShadowLayer(shadowWidth,0.0f,0.0f,Color.BLUE);
            setLayerType(LAYER_TYPE_SOFTWARE,null);*/
   // canvas.drawCircle(midx,midy,radius,paint);
}

}
@Override
public boolean onTouchEvent(MotionEvent me){



return true;
}

    public void drawArcLocal(Path path,float currentSweep1,float startAngle,float sweepAngle){
        if(currentSweep1==360){
            path.addArc(rect,startAngle,sweepAngle);
        }else{
            path.arcTo(rect,startAngle,sweepAngle);
        }

    }

    public void setDonutGraphValues(DonutGraphData donutGraphData){
        data=donutGraphData;
        totalValues=0;
        for(int i=0;i<data.getPercentage().size();i++){
            totalValues+=data.getPercentage().get(i);
        }
        init(null);
       // postInvalidate();
    }


    public void start(int secs) {
        mTimerAnimator.setIntValues(0,secs);
        mTimerAnimator.setDuration(TimeUnit.SECONDS.toMillis(secs/2));
        mTimerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                no_of_iteration=(int)animation.getAnimatedValue();
                invalidate();
            }});
        mTimerAnimator.start();
    }

}


