package com.hua.librarytools.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.hua.librarytools.R;

/**
 * Created by Administrator on 2018/7/5 0005.
 */

public class CircleTextView extends TextView{
    public boolean isToday = false;
    private Paint mPaint;

    public CircleTextView(Context context) {
        super(context);
        initControl();
    }

    public CircleTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initControl();
    }

    public CircleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl();
    }

    private void initControl() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#ff0000"));


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isToday){
            //将画布移动到中间
            canvas.translate(getWidth() / 2, getHeight() / 2);
            canvas.drawCircle(0f, 0f, (getWidth() / 2), mPaint);
        }
    }

    public void setToday(boolean isToday){
        if (isToday){
            this.setBackgroundResource(R.drawable.calendar_ellipse_0b9de1_bg_8);
        }
    }

    public void setSelectedDay() {
        this.setBackgroundResource(R.drawable.calendar_ellipse_0b9de1_8);
    }
}

