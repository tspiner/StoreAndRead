package com.example.jeremy6;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.example.jeremy6.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Picture extends Activity {
	private ImageView iv;
	private Bitmap baseBitmap;
	private Canvas canvas;
	private Paint paint;
	private Button btnSave;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_palette);
		
		 this.iv = (ImageView) this.findViewById(R.id.iv);
		  // 创建一张空白图片
		  baseBitmap = Bitmap.createBitmap(480, 640, Bitmap.Config.ARGB_8888);
		  // 创建一张画布
		  canvas = new Canvas(baseBitmap);
		  // 画布背景为白色
		  canvas.drawColor(Color.WHITE);
		  // 创建画笔
		  paint = new Paint();
		  // 画笔颜色为黑色
		  paint.setColor(Color.BLACK);
		  // 宽度5个像素
		  paint.setStrokeWidth(5);
		  // 先将灰色背景画上
		  canvas.drawBitmap(baseBitmap, new Matrix(), paint);
		  iv.setImageBitmap(baseBitmap);
		  
		  
		 
		  iv.setOnTouchListener(new OnTouchListener() { 
			  int startX; 
			  int startY;
			  
			  
			  @Override
			  public boolean onTouch(View v, MotionEvent event) { 
				  switch (event.getAction()) {  
				  case MotionEvent.ACTION_DOWN: 
					  // 获取手按下时的坐标 
					  startX = (int) event.getX();  
					  startY = (int) event.getY();
					  break; 
				  case MotionEvent.ACTION_MOVE:  
					// 获取手移动后的坐标
					  int stopX = (int) event.getX(); 
					  int stopY = (int) event.getY();  
					// 在开始和结束坐标间画一条线  
					  canvas.drawLine(startX, startY, stopX, stopY, paint); 
					// 实时更新开始坐标
					  startX = (int) event.getX(); 
					  startY = (int) event.getY();  
					  iv.setImageBitmap(baseBitmap);  
					  break;  
					  
				  }
				  return true;
			  }
		  });
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.palette, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}