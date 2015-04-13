package com.example.mako.watermarkdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by fenghao on 2015/4/13.
 */
public class WatermarkDrawable extends Drawable {


    private Paint textPaint;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Rect mTextBound;
    private String text = "";
    private int fontSize = 16;
    private Bitmap bitmap;
    private int height = 200;
    private int width = 200;


    public WatermarkDrawable(Context context,String str,int size,Bitmap bitmap,int w,int h) {

        this.bitmap = bitmap;
        this.text = str;
        this.fontSize = size;
        this.bitmap = bitmap;
        this.width = w;
        this.height = h;

        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        fontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, fontSize, dm);
        width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, dm);
        height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, dm);


        textPaint = new Paint();
        mTextBound = new Rect();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setFakeBoldText(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setTextSize(fontSize);

        if (!TextUtils.isEmpty(text))
            textPaint.getTextBounds(text, 0, text.length(), mTextBound);


    }


    @Override
    public void draw(Canvas canvas) {
        Rect r = getBounds();

        int width = this.width < 0 ? r.width() : this.width;
        int height = this.height < 0 ? r.height() : this.height;

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // 定义矩阵对象
        Matrix matrix = new Matrix();

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        float sx = (float) width / w;
        float sy = (float) height / h;

        matrix.postScale(sx, sy); // 长和宽放大缩小的比例

        bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h,
                matrix, true);

        mCanvas = new Canvas(mBitmap);

        mCanvas.drawBitmap(bitmap, 0, 0, null);

        textPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        mCanvas.drawText(text, width / 2 - mTextBound.width() / 2,
                height / 2 + mTextBound.height() / 2, textPaint);

        canvas.drawBitmap(mBitmap, 0, 0, null);

    }

    @Override
    public void setAlpha(int alpha) {
        textPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        textPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return width;
    }

    @Override
    public int getIntrinsicHeight() {
        return height;
    }







}
