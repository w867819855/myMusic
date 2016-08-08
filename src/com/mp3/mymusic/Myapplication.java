package com.mp3.mymusic;

import android.app.Application;
import android.media.MediaPlayer;

public class Myapplication extends Application {

	private static MediaPlayer mediaPlayer;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mediaPlayer=new MediaPlayer();
	}
	public static MediaPlayer getPlayer() {
		return mediaPlayer;
	}
}
