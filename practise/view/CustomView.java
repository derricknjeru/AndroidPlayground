package com.derrick.aad.practise.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import com.derrick.aad.R;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;

/**
 * @author derrick
 */
public class CustomView extends View {
    private static final String LOG_TAG = CustomView.class.getSimpleName();
    Rect mRect;
    Paint mPaint;
    private int mSquareSize;
    private int mSquareColor;
    private Paint mPaintCircle;

    private float mCircleX;
    private float mCircleY;
    private float mCircleRadius = 100f;

    private Bitmap mImage;


    public CustomView(Context context) {
        super(context);
        init(null);
    }


    public CustomView(Context context, @androidx.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, @androidx.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @SuppressLint("NewApi")
    public CustomView(Context context, @androidx.annotation.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //setting the points of the square
        mRect.left = 10;
        mRect.top = 10;
        mRect.right = mRect.left + mSquareSize;
        mRect.bottom = mRect.top + mSquareSize;

        //Draw rectange shape on a canvas it takes a rect object and paint(for the color)
        canvas.drawRect(mRect, mPaint);

        /*float cx, cy;
        float radius = 100f;

        cx = getWidth() - radius - 50f;
        cy = mRect.top + (mRect.height() / 2);*/

        if (mCircleX == 0f || mCircleY == 0f) {

            mCircleX = getWidth() / 2;
            mCircleY = getHeight() / 2;

        }

        canvas.drawCircle(mCircleX, mCircleY, mCircleRadius, mPaintCircle);

        float imageX = (getWidth() - mImage.getWidth()) / 2;
        float imageY = (getHeight() - mImage.getHeight()) / 2;

        canvas.drawBitmap(mImage, imageX, imageY, null);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {

                float x = event.getX();
                float y = event.getY();

                //if it is within square bounds
                if (mRect.left < x && mRect.right > x)
                    if (mRect.top < y && mRect.bottom > y) {
                        mCircleRadius += 10f;
                        postInvalidate();
                    }

                return true;
            }
            case MotionEvent.ACTION_MOVE: {

                float x = event.getX();
                float y = event.getY();

                double dx = Math.pow(x - mCircleX, 2);
                double dy = Math.pow(y - mCircleY, 2);

                if (dx + dy < Math.pow(mCircleRadius, 2)) {
                    //Touched

                    mCircleX = x;
                    mCircleY = y;

                    postInvalidate();

                    return true;

                }

                return value;
            }
        }

        return value;
    }

    private void init(@Nullable AttributeSet set) {

        mRect = new Rect();

        //Paint.ANTI_ALIAS_FLAG makes sure our shapes are not pixelated
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setColor(Color.parseColor("#008577"));

        mImage = BitmapFactory.decodeResource(getResources(), R.drawable.android_image);


        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int padding = 50;
                mImage = getResizedBitmap(mImage, getWidth() - padding, getHeight() - padding);

                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        int newWidth = mImage.getWidth() - 50;
                        int newHeight = mImage.getHeight() - 50;

                        if (newWidth <= 0 && newHeight <= 0) {
                            cancel();
                            return;

                        }
                        mImage = getResizedBitmap(mImage, newWidth, newHeight);

                        postInvalidate();

                    }
                }, 20001, 5001);

            }
        });


        if (set == null) return;

        TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.CustomView);

        mSquareSize = ta.getDimensionPixelSize(R.styleable.CustomView_square_size, 200);
        mSquareColor = ta.getColor(R.styleable.CustomView_square_color, Color.GREEN);

        mPaint.setColor(mSquareColor);

        ta.recycle();//gabbage collection

    }

    private Bitmap getResizedBitmap(Bitmap mImage, int width, int height) {

        Matrix matrix = new Matrix();

        RectF src = new RectF(0, 0, mImage.getWidth(), mImage.getHeight());

        RectF dest = new RectF(0, 0, width, height);

        matrix.setRectToRect(src, dest, Matrix.ScaleToFit.CENTER);

        return Bitmap.createBitmap(mImage, 0, 0, mImage.getWidth(), mImage.getHeight(), matrix, true);
    }

    public void swapColor() {

        int color = mPaint.getColor() == mSquareColor ? Color.RED : mSquareColor;

        mPaint.setColor(color);

        /**
         * If you want to re draw your view from UI Thread you can call invalidate() method.
         *
         * If you want to re draw your view from Non UI Thread you can call postInvalidate() method.
         */
        postInvalidate();


    }


}
