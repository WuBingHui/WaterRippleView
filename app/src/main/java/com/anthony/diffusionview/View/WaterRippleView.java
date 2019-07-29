package com.anthony.diffusionview.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.anthony.diffusionview.R;

public class WaterRippleView extends View {


  private boolean running = false;
  private int[] strokeWidthArr;
  private int maxStrokeWidth;
  private int rippleCount;
  private int rippleSpacing;
  private Paint paint;
  private Bitmap bitmap;
  private int width;
  private int height;

  public WaterRippleView(Context context) {
	this(context,null);
  }

  public WaterRippleView(Context context, AttributeSet attrs) {
	this(context,attrs,0);
  }

  public WaterRippleView(Context context, AttributeSet attrs, int defStyleAttr) {
	super(context, attrs, defStyleAttr);
	initAttrs(context, attrs);
  }
  private void initAttrs(Context context, AttributeSet attrs) {
	TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaterRippleView);
	int waveColor = typedArray.getColor(R.styleable.WaterRippleView_rippleColor,
		ContextCompat.getColor(context, R.color.colorPrimary));
	Drawable drawable = typedArray.getDrawable(R.styleable.WaterRippleView_rippleCenterIcon);
	rippleCount = typedArray.getInt(R.styleable.WaterRippleView_rippleCount, 2);
	rippleSpacing = typedArray.getDimensionPixelSize(R.styleable.WaterRippleView_rippleSpacing,
		16);
	running = typedArray.getBoolean(R.styleable.WaterRippleView_rippleAutoRunning, false);
	typedArray.recycle();
	bitmap = ((BitmapDrawable) drawable).getBitmap();
	paint = new Paint();
	paint.setAntiAlias(true);
	paint.setStyle(Paint.Style.STROKE);
	paint.setColor(waveColor);
  }
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	int size = (rippleCount * rippleSpacing + bitmap.getWidth() / 2) * 2;
	width = resolveSize(size, widthMeasureSpec);
	height = resolveSize(size, heightMeasureSpec);
	setMeasuredDimension(width, height);
	maxStrokeWidth = (width - bitmap.getWidth()) / 2;
	initArray();
  }
  private void initArray() {
	strokeWidthArr = new int[rippleCount];
	for (int i = 0; i < strokeWidthArr.length; i++) {
	  strokeWidthArr[i] = - maxStrokeWidth / rippleCount * i;
	}
  }
  @Override
  protected void onDraw(Canvas canvas) {
	drawBitmap(canvas);
	if (running) {
	  drawRipple(canvas);
	  postInvalidateDelayed(50);
	}
  }
  private void drawBitmap(Canvas canvas) {
	int left = (width - bitmap.getWidth()) / 2;
	int top = (height - bitmap.getHeight()) / 2;
	canvas.drawBitmap(bitmap, left, top, null);
  }
  private void drawRipple(Canvas canvas) {
	for (int strokeWidth : strokeWidthArr) {
	  if (strokeWidth < 0) {
		continue;
	  }
	  paint.setStrokeWidth(strokeWidth);
	  paint.setAlpha(255 - 255 * strokeWidth / maxStrokeWidth);
	  canvas.drawCircle(width / 2, height / 2, bitmap.getWidth() / 2 + strokeWidth/2,
		  paint);
	}
	for (int i = 0; i < strokeWidthArr.length; i++) {
	  if ((strokeWidthArr[i] += 4) > maxStrokeWidth) {
		strokeWidthArr[i] = 0;
	  }
	}
  }
  public void start() {
	running = true;
	postInvalidate();
  }
  public void stop() {
	running = false;
	initArray();
	postInvalidate();
  }

  public boolean getStatus(){
    return running;
  }


}
