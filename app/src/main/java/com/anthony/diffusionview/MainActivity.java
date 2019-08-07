package com.anthony.diffusionview;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.anthony.diffusionview.View.DiffusionView;
import com.anthony.diffusionview.View.WaterRippleView;

public class MainActivity extends AppCompatActivity {
	private DiffusionView diffusionView;
  private DiffusionView diffusionView2;

  private WaterRippleView waterRippleView;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	diffusionView = findViewById(R.id.diffusionView);
	diffusionView2 = findViewById(R.id.diffusionView2);



  }


}
