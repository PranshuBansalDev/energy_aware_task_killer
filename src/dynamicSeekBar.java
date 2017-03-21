package com.example.ee202b.taskscheduler202b;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.SeekBar;

// ***THIS CODE IS ADAPTED FROM: http://stackoverflow.com/questions/41774963/android-seekbar-show-progress-value-along-the-seekbar

public class dynamicSeekBar extends SeekBar {
    public dynamicSeekBar (Context context) {
        super(context);
    }

    public dynamicSeekBar (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public dynamicSeekBar (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        int thumb_x = (int) (( (double)this.getProgress()/this.getMax() ) * (double)this.getWidth());
        float middle = (float) (this.getHeight());

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        c.drawText(""+this.getProgress(), thumb_x, middle, paint);
    }
}
