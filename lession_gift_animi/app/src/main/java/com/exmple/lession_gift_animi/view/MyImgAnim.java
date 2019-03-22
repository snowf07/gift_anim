package com.exmple.lession_gift_animi.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.widget.Button;
import android.widget.ImageView;

import com.exmple.lession_gift_animi.R;

/**
 * Created by lang.chen on 2019/3/22
 */
public class MyImgAnim extends AppCompatImageView {

    private PointF pointF=new PointF();

    public MyImgAnim(Context context) {
        super(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Paint paint=new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawCircle(pointF.x,pointF.y,0,paint);

    }


    public void setPointF(float x, float y){
        pointF.set(x,y);
        invalidate();
    }

}