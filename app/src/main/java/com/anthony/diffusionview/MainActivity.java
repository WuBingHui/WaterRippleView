package com.anthony.diffusionview;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.anthony.diffusionview.View.DiffusionView;
import com.anthony.diffusionview.View.WaterRippleView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
	private DiffusionView diffusionView;
	private WaterRippleView waterRippleView;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	diffusionView = findViewById(R.id.diffusionView);
	waterRippleView = findViewById(R.id.waterRippleView);
	waterRippleView.setOnClickListener(this);

  }

  @Override
  public void onClick(View view) {
	switch (view.getId()){
	  case R.id.waterRippleView:
	    if(!waterRippleView.getStatus()){
		  waterRippleView.start();
		}else{
		  waterRippleView.stop();
		}
	    break;
	}
  }
}
