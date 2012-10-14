package com.capstone.ocelot;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
//import android.widget.ListView;
import android.widget.Toast;

public class Default extends Activity {
	private ArrayList<Sound> mSounds = null;
	private SoundAdapter mAdapter = null;
	static MediaPlayer mMediaPlayer = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(this));
	    
	    //Get the width and height of the screen to set the size of the pictures.
    	//DisplayMetrics metrics = new DisplayMetrics();
    	//getWindowManager().getDefaultDisplay().getMetrics(metrics);

    	//int height = metrics.heightPixels;
    	//int width = metrics.widthPixels;
		
		//create a simple list
//		mSounds = new ArrayList<Sound>();
//		Sound s = new Sound();
		
//		s.setDescription("Cougar");
//		s.setIconResourceId(R.drawable.cougar);
//		s.setSoundResourceId(R.raw.cougar);
//		mSounds.add(s);
//		
//		s = new Sound();
//		s.setDescription("Chicken");
//		s.setIconResourceId(R.drawable.chicken);
//		s.setSoundResourceId(R.raw.chicken);
//		mSounds.add(s);
//		
//		s = new Sound();
//		s.setDescription("Dog");
//		s.setIconResourceId(R.drawable.dog);
//		s.setSoundResourceId(R.raw.dog);
//		mSounds.add(s);
//		
//		s = new Sound();
//		s.setDescription("Elephant");
//		s.setIconResourceId(R.drawable.elephant);
//		s.setSoundResourceId(R.raw.elephant);
//		mSounds.add(s);
		
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            Toast.makeText(Default.this, "" + position, Toast.LENGTH_SHORT).show();
	        }
	    });
	    
		//mAdapter = new SoundAdapter(this, R.layout.main, mSounds);
		//setAdapter(mAdapter);
	}
	
//	@Override
//	public void onListItemClick(Gri parent, View v, int position, long id){
//	Sound s = (Sound) mSounds.get(position);
//	MediaPlayer mp = MediaPlayer.create(this, s.getSoundResourceId());
//	mp.start();
//	}
//	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.activity_default, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.new_game:
	            //newGame();
	            return true;
	        case R.id.help:
	            //showHelp();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
