package com.example.jeremy6;


import com.example.jeremy6.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.os.Message;
import android.util.JsonWriter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View.OnClickListener;

import android.widget.Toast;

public class Palette extends Activity {
	String file;
	String FILENAME = file+".json";
	
	private ImageView iv;
	private Bitmap baseBitmap;
	private Canvas canvas;
	private Paint paint;
	private TextView txtAns;
	private SharedPreferences preference;
	private String readAns;
	private TextView mTextView;//倒數計時
	public ArrayList<Integer> coordinate; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_palette);
		
		//取得介面元素
		txtAns=(TextView)findViewById(R.id.textView12);
		
		//尋找儲存檔
		preference=getSharedPreferences("ans",MODE_PRIVATE);
		//拿資料
	    readAns=preference.getString("data","unknown");
		//顯示
		txtAns.setText(readAns);
		
		//倒數計時
		time();
		
		this.iv = (ImageView) this.findViewById(R.id.imageView3);
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
		
		/**	 try{
		  String FILENAME = file+".json";
		  //gives file name
		  FileOutputStream out = openFileOutput(FILENAME, MODE_WORLD_READABLE);
		  JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
		  writer.setIndent("  ");
		  writer.close();
		      
	  }catch(Exception e) {
		  Log.e("log_tag", "Error saving string "+e.toString());
		  }
**/	  

		try{
			 FileOutputStream out = openFileOutput(FILENAME, MODE_WORLD_READABLE);
		 
			 final JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
			 writer.setIndent("  ");
			 
			 writer.beginObject();
			 writer.beginArray();
			 writer.name("target");
			 if(self == MAX)  // <------ 要取得self變數及人數
			 {
				 writer.value(0);
			 }
			 else
			 {
			 writer.value(self+1); // <------ 要取得self變數及人數
			 }
	//		 writer.endArray();
	//		 writer.beginArray();
			 writer.name("round");
			 writer.value();  //  <------ 要取得round變數
	//		 writer.endArray();
	//		 writer.beginArray();
		     writer.name("title");
			 writer.value("題目");  // <----- 改成題目變數
	//	     writer.endArray();
		     writer.beginArray();
		     writer.name("coordinates");

		iv.setOnTouchListener(new OnTouchListener() { 
			  int startX ; 
			  int startY ;
			  
			  
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
					  
					  coordinate.add(startX);
					  coordinate.add(startY); 
					  coordinate.add(stopX); 
					  coordinate.add(stopX); 
					  writer.value(startX);
					  writer.value(startY);
					  writer.value(stopX);
					  writer.value(stopY);
				 
					// 实时更新开始坐标
					  startX = (int) event.getX(); 
					  startY = (int) event.getY();  
					  iv.setImageBitmap(baseBitmap);  
					  break;  
					  
				  }
				  return true;
			  }
		  });
		writer.endArray();
		writer.endObject();
		writer.flush();
		writer.close();
		
		}catch(Exception e) {
	  Log.e("log_tag", "Error saving string "+e.toString());
	  }
	}
	
	public void time(){	
		// 倒數計時     
		mTextView = (TextView)findViewById(R.id.timeView2);
		new CountDownTimer(5000,1000){
		            
			@Override
			public void onFinish() {
			// TODO Auto-generated method stub
				mTextView.setText("Time is up");
				//儲存資料-未完成(任軒)
				
				//跳轉頁面
				Intent intent=new Intent();
				intent.setClass(Palette.this,Guess.class);
				startActivity(intent);
			}

			@Override
			public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			mTextView.setText("seconds remaining:"+millisUntilFinished/1000);
			}
			            
			}.start();
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