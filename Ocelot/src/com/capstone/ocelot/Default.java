package com.capstone.ocelot;

import java.util.ArrayList;
import android.app.ListActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class Default extends ListActivity {
	private ArrayList<Sound> mSounds = null;
	private SoundAdapter mAdapter = null;
	static MediaPlayer mMediaPlayer = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//create a simple list
		mSounds = new ArrayList<Sound>();
		Sound s = new Sound();
		
		s.setDescription("Cougar");
		s.setIconResourceId(R.drawable.cougar);
		s.setSoundResourceId(R.raw.cougar);
		mSounds.add(s);
		
		s = new Sound();
		s.setDescription("Chicken");
		s.setIconResourceId(R.drawable.chicken);
		s.setSoundResourceId(R.raw.chicken);
		mSounds.add(s);
		
		s = new Sound();
		s.setDescription("Dog");
		s.setIconResourceId(R.drawable.dog);
		s.setSoundResourceId(R.raw.dog);
		mSounds.add(s);
		mAdapter = new SoundAdapter(this, R.layout.list_row, mSounds);
		setListAdapter(mAdapter);
	}
	
	@Override
	public void onListItemClick(ListView parent, View v, int position, long id){
	Sound s = (Sound) mSounds.get(position);
	MediaPlayer mp = MediaPlayer.create(this, s.getSoundResourceId());
	mp.start();
	}
}
