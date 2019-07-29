package com.anthony.diffusionview.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import android.view.View;
import androidx.core.content.ContextCompat;
import com.anthony.diffusionview.R;
import java.util.ArrayList;
import java.util.List;

public class DiffusionView  extends View {

  private Paint centerPaint; //中心圓paint
  private int radius = 80; //中心圓半徑
  private Paint diffusionPaint; //擴散圓paint
  private float centerX;//圓心x
  private float centerY;//圓心y
  private int distance = 5; //每次圓遞增的間距
  private int maxRadius = 80; //最大圓半徑
  private int delayMilliseconds = 33;//擴散延遲間隔，越大擴散越慢
  private List<Integer> diffusionRadius = new ArrayList<>();//擴散圓層級數，元素為擴散的距離
  private List<Integer> alphas = new ArrayList<>();//對應每層圓的透明度

  public DiffusionView(Context context) {
	this(context, null, 0);
  }
  public DiffusionView(Context context, AttributeSet attrs) {
	this(context, attrs, 0);
  }

  public DiffusionView(Context context, AttributeSet attrs, int defStyleAttr) {
	super(context, attrs, defStyleAttr);
	init(context,attrs,defStyleAttr);

  }
  private void init(Context context,AttributeSet attrs,int defStyleAttr){
	TypedArray typedArray= context.obtainStyledAttributes(attrs, R.styleable.DiffusionView, defStyleAttr, 0);
	radius = typedArray.getInt(R.styleable.DiffusionView_diffusion_radius, radius);
	maxRadius = typedArray.getInt(R.styleable.DiffusionView_diffusion_max_radius, maxRadius);
	int centerColor = typedArray.getColor(R.styleable.DiffusionView_diffusion_center_color, ContextCompat.getColor(context, R.color.colorAccent));
	int spreadColor = typedArray.getColor(R.styleable.DiffusionView_diffusion_spread_color, ContextCompat.getColor(context, R.color.colorAccent));
	distance = typedArray.getInt(R.styleable.DiffusionView_diffusion_distance, distance);
	typedArray.recycle();

	centerPaint = new Paint();
	centerPaint.setColor(centerColor);
	centerPaint.setAntiAlias(true);
	//最開始不透明且擴散距離為0
	alphas.add(255);
	diffusionRadius.add(0);
	diffusionPaint = new Paint();
	diffusionPaint.setAntiAlias(true);
	diffusionPaint.setAlpha(255);
	diffusionPaint.setColor(spreadColor);
  }

  @Override
  protected void onSizeChanged(int width, int high, int oldWidth, int oldHigh) {
	super.onSizeChanged(width, high, oldWidth, oldHigh);
	//圓心位置
	centerX = (float) width/2;
	centerY = (float) high/2;
  }

  @Override
  protected void onDraw(Canvas canvas) {
	super.onDraw(canvas);

	for (int i = 0; i < diffusionRadius.size(); i++) {
	  int alpha = alphas.get(i);
	  diffusionPaint.setAlpha(alpha);
	  int width = diffusionRadius.get(i);
	  //繪製擴散的圓
	  canvas.drawCircle(centerX, centerY, radius + width, diffusionPaint);

	  //每次擴散圓半徑遞增，圓透明度遞減
	  if (alpha > 0 && width < 300) {
		alpha = alpha - distance > 0 ? alpha - distance : 1;
		alphas.set(i, alpha);
		diffusionRadius.set(i, width + distance);
	  }
	}
	//當最外層擴散圓半徑達到最大半徑時添加新擴散圓
	if ( diffusionRadius.get(diffusionRadius.size()-1) > maxRadius) {
	  diffusionRadius.add(0);
	  alphas.add(255);
	}
	//超過8個擴散圓，刪除最外圈的圓
	if (diffusionRadius.size() >= 8) {
	  alphas.remove(0);
	  diffusionRadius.remove(0);
	}
	//中間的圓
	canvas.drawCircle(centerX, centerY, radius, centerPaint);
	//TODO Anthony 可以在中間繪製文字或圖片

	//延遲更新，達到擴散效果
	postInvalidateDelayed(delayMilliseconds);
  }



}
