package com.mp3.mymusic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnInfoListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/**
 * ���ֲ�����
 * Author HONG
 * */
public class MainActivity extends Activity {

	private SimpleAdapter mAdapter;
	private ListView mMusiclist;
	private Button startmp3;
	private boolean isplaying = true;
	private List<Mp3Info> mp3Infos;

	private MediaPlayer  mediaPlayer;
	private int poisong=0;
	private int countsong=0;
	
	private Context context;
	
	//ֻ�ڵ�һ�ν����ʱ�򴴽������ߡ���ȡ����ʱ�򲻻��ظ����ôη���
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		context=MainActivity.this;
		initview();
	}

	private void initview() {
		// TODO Auto-generated method stub
		
		mMusiclist = (ListView) findViewById(R.id.mp3list);
		startmp3 = (Button) findViewById(R.id.startmp3);
		startmp3.setText("��ʼ");
		startmp3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isplaying) {
					mediaPlayer.pause();
					startmp3.setText("��ʼ");
					isplaying=false;
				}else {
					mediaPlayer.start();
					startmp3.setText("��ͣ");
					isplaying=true;
				}
				//��������Ҫ���ŵ�����
				
			}
		});
		mMusiclist.setDividerHeight(2);
		mMusiclist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Message msg=Message.obtain();
				msg.what=position;
				mHandler.sendMessage(msg);
			}
		});
		//��ȡ������
		Myapplication myal=(Myapplication) getApplication();
		mediaPlayer= myal.getPlayer();
		//��ȡ�б�
		mp3Infos = getMp3Infos();
		//��ʼ��������һ������
		nextsong();
	}
	//activity��״̬�ı�ʱ���óԷ��������ظ����ô˷���
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	private Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
		//	Toast.makeText(Mp3action.this, "msg:"+msg.what, 0).show();
			if (msg.what>-1) {
				startmp3.setText("��ͣ");
				poisong=msg.what;
//				Mp3Info mp3Info = mp3Infos.get(poisong);
//				Log.e("mp3Info-->", mp3Info.getUrl());
//				play(mp3Info.getUrl());
				playOrder();
			}
		}
		
	};
	
	private void playOrder(){
		Mp3Info mp3Info = null;
		if (poisong>mp3Infos.size()) {
		//mp3Info=  mp3Infos.get(0);
		}else {
		mp3Info=  mp3Infos.get(poisong);
		play(mp3Info.getUrl());
		}
	
	
		
		
	}
	//�������״̬
	private void nextsong(){
		//�������һ�׸��ʱ����ôη���
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				 Tonext();
			}
		});
		
	}
	//������һ�׵���Ϣ
	private void Tonext(){
		Message msg=Message.obtain();
		msg.what=poisong+1;
		mHandler.sendMessage(msg);
		int aw=countsong+1;
		Log.e("����ִ�еĴ�����", "***************"+aw);
		
		
	}
	  private void play(String pString){
	        try{
	        	
	            mediaPlayer.reset();//��������Ҫ���ŵ�����
	            mediaPlayer.setDataSource(pString);
	            mediaPlayer.prepare();//Ԥ������Ƶ
	            mediaPlayer.start();//��������
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            Log.e("err",e.getMessage().toString());
	           // Toast.makeText(context, "�ļ��쳣", 0).show();
	           //�η���ֻ����ʱʹ�ã���ʼ�����ŵ�һ������
	            Tonext();
	        }
	        return ;
	    }
//
//		mediaPlayer.setOnInfoListener(new OnInfoListener(){
//
//			@Override
//			public boolean onInfo(MediaPlayer mp, int what,
//					int extra) {
//				// TODO Auto-generated method stub
//				Log.e("what:>>>", what+"");
//				Log.e("extra:>>>", extra+"");
//				
//				return false;
//			}
//			
//			
//		});
	  
	// ɨ��mp3
		public List<Mp3Info> getMp3Infos() {
			Cursor cursor = getContentResolver().query(
					MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
					MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
			List<Mp3Info> mp3Infos = new ArrayList<Mp3Info>();
			for (int i = 0; i < cursor.getCount(); i++) {
				Mp3Info mp3Info = new Mp3Info();
				cursor.moveToNext();
				long id = cursor.getLong(cursor
						.getColumnIndex(MediaStore.Audio.Media._ID)); // ����id
				String title = cursor.getString((cursor
						.getColumnIndex(MediaStore.Audio.Media.TITLE)));// ���ֱ���
				String artist = cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Media.ARTIST));// ������
				long duration = cursor.getLong(cursor
						.getColumnIndex(MediaStore.Audio.Media.DURATION));// ʱ��
				long size = cursor.getLong(cursor
						.getColumnIndex(MediaStore.Audio.Media.SIZE)); // �ļ���С
				String url = cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Media.DATA)); // �ļ�·��
				int isMusic = cursor.getInt(cursor
						.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));// �Ƿ�Ϊ����
				if (isMusic != 0) { // ֻ��������ӵ����ϵ���
					mp3Info.setId((int) id);
					mp3Info.setTitle(title);
					mp3Info.setArtist(artist);
					mp3Info.setDuration(((int) duration) + "");
					mp3Info.setSize(((int) size) + "");
					mp3Info.setUrl(url);
					mp3Infos.add(mp3Info);
				}
			}
			setListAdpter(mp3Infos);
			return mp3Infos;
		}

		// ���mp3�б�
		public void setListAdpter(List<Mp3Info> mp3Infos) {
			List<HashMap<String, String>> mp3list = new ArrayList<HashMap<String, String>>();
			for (Iterator iterator = mp3Infos.iterator(); iterator.hasNext();) {
				Mp3Info mp3Info = (Mp3Info) iterator.next();
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("title", mp3Info.getTitle());
				map.put("Artist", mp3Info.getArtist());
				map.put("duration", String.valueOf(mp3Info.getDuration()));
				map.put("size", String.valueOf(mp3Info.getSize()));
				map.put("url", mp3Info.getUrl());
				// Log.e("path:", mp3Info.getUrl());211007
				mp3list.add(map);
			}
			mAdapter = new SimpleAdapter(this, mp3list,
					R.layout.music_list_item_layout, new String[] { "title",
							"Artist", "duration" }, new int[] { R.id.music_title,
							R.id.music_Artist, R.id.music_duration });
			mMusiclist.setAdapter(mAdapter);
		}

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {

			if (keyCode == KeyEvent.KEYCODE_BACK) {
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
				this.startActivity(intent);
				System.exit(0);
			}
			return true;
		}
}