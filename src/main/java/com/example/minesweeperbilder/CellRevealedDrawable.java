package com.example.minesweeperbilder;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

public class CellRevealedDrawable extends Drawable {
    private Paint paint = new Paint();
    private int borderSize = 2;

    @Override
    public void draw(Canvas canvas) {
        int w = getBounds().width();
        int h = getBounds().height();
        int b = borderSize;

        paint.setColor(0xFF737373);
        canvas.drawRect(0,0,w,h,paint);

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
