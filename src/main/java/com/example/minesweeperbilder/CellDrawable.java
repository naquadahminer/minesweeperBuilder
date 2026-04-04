package com.example.minesweeperbilder;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

public class CellDrawable extends Drawable {
    private Paint paint = new Paint();
    private int borderSize = 10;

    @Override
    public void draw(Canvas canvas) {
        int w = getBounds().width();
        int h = getBounds().height();
        int b = borderSize;

        paint.setColor(0xFFe5e5e5);
        Path top = new Path();
        top.moveTo(0,0);
        top.lineTo(w,0);
        top.lineTo(w-b,b);
        top.lineTo(b,b);
        top.lineTo(b,h-b);
        top.lineTo(0,h);
        top.close();
        canvas.drawPath(top,paint);

        paint.setColor(0xFF737373);
        Path bottom = new Path();
        bottom.moveTo(w,h);
        bottom.lineTo(0,h);
        bottom.lineTo(b,h-b);
        bottom.lineTo(w-b,h-b);
        bottom.lineTo(w-b,b);
        bottom.lineTo(w,0);
        bottom.close();
        canvas.drawPath(bottom,paint);

        paint.setColor(0xFFb2b2b2);
        canvas.drawRect(b, b, w-b, h-b, paint);
    }

    @Override
    public void setAlpha(int alpha) {}

    @Override
    public void setColorFilter(ColorFilter colorFilter) {}

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
