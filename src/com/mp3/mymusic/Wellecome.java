package com.mp3.mymusic;

import java.util.Timer;
import java.util.TimerTask;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Wellecome extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.wellecome);
		Timer timer=new Timer();
		TimerTask task=new TimerTask() {
			@Override
			public void run() {
				Intent intent =new Intent(Wellecome.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		};
		timer.schedule(task, 2000);//是表示执行一次的任务
	
	}
	
	
	
}
