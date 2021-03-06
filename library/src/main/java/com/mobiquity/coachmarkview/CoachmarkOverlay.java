package com.mobiquity.coachmarkview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/**
 * Class responsible for drawing the darkened overlay and exposing the views showcased
 */
class CoachmarkOverlay {

    final Paint eraserPaint;
    final Paint paint;

    int backgroundColor;

    public CoachmarkOverlay(int backgroundColor) {
        this.backgroundColor = backgroundColor;

        eraserPaint = new Paint();
        eraserPaint.setColor(0xFFFFFF);
        eraserPaint.setAlpha(0);
        eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        eraserPaint.setAntiAlias(true);

        paint = new Paint();
    }

    public void drawCircleCoachmark(Bitmap buffer, float x, float y, float coachmarkRadius) {
        Canvas bufferCanvas = new Canvas(buffer);
        eraserPaint.setAlpha(0);
        bufferCanvas.drawCircle(x, y, coachmarkRadius, eraserPaint);
    }

    public void drawRectCoachmark(Bitmap buffer, float x, float y, float width, float height) {
        Canvas bufferCanvas = new Canvas(buffer);
        eraserPaint.setAlpha(0);
        bufferCanvas.drawRect(x, y, x+width, y+height, eraserPaint);
    }

    public void erase(Bitmap bitmapBuffer) {
        bitmapBuffer.eraseColor(backgroundColor);
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void drawToCanvas(Canvas canvas, Bitmap bitmapBuffer) {
        canvas.drawBitmap(bitmapBuffer, 0, 0, paint);
    }
}
