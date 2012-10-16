package com.capstone.ocelot;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.ClipData;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.Toast;


public class Default extends Activity {
	ArrayList<SoundBoardItem> mSoundBoardItems = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//Set and load the soundboardItems
		mSoundBoardItems = LoadSoundBoard();
		
		//Setup the view
		GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(this, mSoundBoardItems));
	    
	    //Get the width and height of the screen to set the size of the pictures.
	    //Will need to do this in order to set the columns/rows up properly.
    	//DisplayMetrics metrics = new DisplayMetrics();
    	//getWindowManager().getDefaultDisplay().getMetrics(metrics);
    	//int height = metrics.heightPixels;
    	//int width = metrics.widthPixels;
	    
//	    Iterator<SoundBoardItem> itrItems = mSoundBoardItems.iterator();
//	    while(itrItems.hasNext()){
//	    	itrItems.next()..setOnTouchListener(new MyTouchListener());
//	    }
	    
	    //findViewById(R.id.gridview).setOnTouchListener(new MyTouchListener()); //It appears that this needs to be added to a view, not an object in the view.
		
//	    gridview.setOnItemClickListener(new OnItemClickListener() {  //Display the description associated with the object.
//	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//	        	MediaPlayer mp = MediaPlayer.create(parent.getContext(), mSoundBoardItems.get(position).getSoundResourceId());
//	        	mp.start();
//	        	Toast.makeText(parent.getContext(), "" + mSoundBoardItems.get(position).getDescription(), Toast.LENGTH_SHORT).show();
//	        }
//	    });
	    
//	    gridview.setOnItemLongClickListener(new OnItemLongClickListener() {  //Play the sound associated with the object.
//			public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
//	        	MediaPlayer mp = MediaPlayer.create(parent.getContext(), mSoundBoardItems.get(position).getSoundResourceId());
//	        	mp.start(); 
//	        	return true;
//			}
//		});
	}
	
//	//Touch Listener to interact with the SoundBoardItems
//	private final class MyTouchListener implements OnTouchListener {
//	    public boolean onTouch(View view, MotionEvent motionEvent) {
//	      if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//	        ClipData data = ClipData.newPlainText("", "");
//	        DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
//	        view.startDrag(data, shadowBuilder, view, 0);
//	        //view.setVisibility(View.INVISIBLE);
//	        return true;
//	      } else {
//	        return false;
//	      }
//	    }
//	}
	
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
	
	private ArrayList<SoundBoardItem> LoadSoundBoard(){
		
		ArrayList<SoundBoardItem> mLoadItems = new ArrayList<SoundBoardItem>();
		
	    SoundBoardItem s = new SoundBoardItem();
		s.setDescription("Cougar");
		s.setIconResourceId(R.drawable.cougar);
		s.setSoundResourceId(R.raw.cougar);
		mLoadItems.add(s);
		
		s = new SoundBoardItem();
		s.setDescription("Chicken");
		s.setIconResourceId(R.drawable.chicken);
		s.setSoundResourceId(R.raw.chicken);
		mLoadItems.add(s);
		
		s = new SoundBoardItem();
		s.setDescription("Dog");
		s.setIconResourceId(R.drawable.dog);
		s.setSoundResourceId(R.raw.dog);
		mLoadItems.add(s);
		
		s = new SoundBoardItem();
		s.setDescription("Elephant");
		s.setIconResourceId(R.drawable.elephant);
		s.setSoundResourceId(R.raw.elephant);
		mLoadItems.add(s);
		
		return mLoadItems;
	}
}
