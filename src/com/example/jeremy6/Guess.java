package com.example.jeremy6;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

//
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
//

public class Guess extends Activity {
	private SharedPreferences preference;
	private EditText edtguess;
	private TextView mTextView;//倒數計時
	//
	private ImageView iv;
	private Bitmap baseBitmap;
	private Canvas canvas;
	private Paint paint;
	private TextView txtAns;
	private String readAns;
	//
	String file;
	String FILENAME = file+".json";
	ArrayList<Integer> gameArray;
	
/**	public void readJsonStream() throws IOException {
		FileInputStream get = openFileInput(FILENAME);
	     JsonReader reader = new JsonReader(new InputStreamReader(get, "UTF-8"));
	      
	}
	**/
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guess);
		
		//-------
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
		
		for(int i = 0; i < gameArray.size(); i+=4)  // <---- 將拿到的陣列畫成圖形
		{
			canvas.drawLine(gameArray.get(i), gameArray.get(i+1), gameArray.get(i+3), gameArray.get(i+4), paint); 
		}
		
		
		
		//-------
		
		//取得介面元件
		edtguess=(EditText)findViewById(R.id.editText6);
		
		preference=getSharedPreferences("guess",MODE_PRIVATE);
		
		try{
			 FileOutputStream out = openFileOutput(FILENAME, MODE_WORLD_READABLE);
		 
			 final JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
			 writer.setIndent("  ");
			 writer.beginObject();
			 writer.beginArray();
			 writer.name("title").value("猜題");  //  <------ 引入題目變數
			 writer.endArray();
			 writer.endObject();
			 writer.flush();
			 writer.close();
		}catch(Exception e) {
			  Log.e("log_tag", "Error saving string "+e.toString());
			  }
			
		
		//倒數計時
		time();
	}
	
	public void time(){	
		// 倒數計時     
		mTextView = (TextView)findViewById(R.id.timeView3);
		new CountDownTimer(5000,1000){
		            
			@Override
			public void onFinish() {
			// TODO Auto-generated method stub
				mTextView.setText("Time is up");
				//跳轉業面
				Intent intent=new Intent();
				intent.setClass(Guess.this,EndGame.class);
				startActivity(intent);
			}

			@Override
			public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			mTextView.setText("seconds remaining:"+millisUntilFinished/1000);
			}
			            
			}.start();
		}
	
	//儲存資料(使用者名稱)
	protected void onStop(){
		super.onStop();
			preference.edit()
			.putString("name",edtguess.getText().toString())
			.commit();
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.guess, menu);
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

