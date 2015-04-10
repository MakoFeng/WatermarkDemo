package com.example.mako.watermarkdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Administrator on 2015/4/10.
 */
public class WatermarkView extends View {

    private Paint mPaint;
    /**
     * 内存中创建的Canvas
     */
    private Canvas mCanvas;
    /**
     * mCanvas绘制内容在其上
     */
    private Bitmap mBitmap;

    private Rect mTextBound;

    private float textSize = 24;

    private int backgroundResId = R.drawable.ic_watermark1;

    private String mText;

    private int width;

    private int height;

    public WatermarkView(Context context) {
        this(context, null);
    }

    public WatermarkView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WatermarkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        DisplayMetrics dm = getResources().getDisplayMetrics();

        textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize, dm);

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.watermark);
        mText = mTypedArray.getString(R.styleable.watermark_text);
        textSize = mTypedArray.getDimension(R.styleable.watermark_textSize, textSize);
        backgroundResId = mTypedArray.getResourceId(R.styleable.watermark_backgroundResId, backgroundResId);

        mTypedArray.recycle();

        init();

    }

    private void init() {
        //初始化一个抗锯齿的Paint
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(textSize);
        mPaint.setFakeBoldText(true);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
        mTextBound = new Rect();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getMeasuredWidth();
        height = getMeasuredHeight();

        // 初始化bitmap
        if (!TextUtils.isEmpty(mText))
            mPaint.getTextBounds(mText, 0, mText.length(), mTextBound);

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), backgroundResId);

        // 定义矩阵对象
        Matrix matrix = new Matrix();

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        float sx = (float) width / w;
        float sy = (float) height / h;

        matrix.postScale(sx, sy); // 长和宽放大缩小的比例

        bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h,
                matrix, true);

        mCanvas.drawBitmap(bitmap, 0, 0, null);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        mCanvas.drawText(mText, getWidth() / 2 - mTextBound.width() / 2,
                getHeight() / 2 + mTextBound.height() / 2, mPaint);

        canvas.drawBitmap(mBitmap, 0, 0, null);

    }

    public void setText(String mText) {
        this.mText = mText;
        requestLayout();
    }



    public void setTextSize(int size) {
        this.textSize = size;
        invalidate();
    }

    public void setBackgroundResId(int resId) {
        this.backgroundResId = resId;
        invalidate();
    }

}
