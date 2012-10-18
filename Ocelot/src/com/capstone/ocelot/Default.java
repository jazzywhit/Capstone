package com.capstone.ocelot;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.capstone.ocelot.SoundBoard.SoundBoardGridAdapter;
import com.capstone.ocelot.SoundBoard.SoundBoardItem;
import com.capstone.ocelot.SoundBoard.SoundBoardSequenceAdapter;


public class Default extends Activity {
	ArrayList<SoundBoardItem> mGridItems = null;
	ArrayAdapter<SoundBoardItem> mSequenceItemAdapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//Set and load the soundboardItems
		//TODO This will need to be extended so that we can load from a database!
		mGridItems = LoadSoundBoard(); //Load the initial items to the grid
		
		//Setup the gridview adapter
		GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new SoundBoardGridAdapter(this, mGridItems));
	    
	    //Setup the sequence adapter
	    //TODO Rename all the classes to better explain their function
	    //TODO Implement that horizontal List View: http://www.dev-smart.com/archives/34
		HorizontalScrollView sequenceView = (HorizontalScrollView) findViewById(R.id.sequenceview);
		sequenceView.setAdapter(new SoundBoardSequenceAdapter(this, mSequenceItems));
	   
	    
	    //TODO Get the width and height of the screen to set the size of the pictures.
	    //Will need to do this in order to set the columns/rows up properly.
    	//DisplayMetrics metrics = new DisplayMetrics();
    	//getWindowManager().getDefaultDisplay().getMetrics(metrics);
    	//int height = metrics.heightPixels;
    	//int width = metrics.widthPixels;
	}
	
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
		
		s = new SoundBoardItem();
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
		
		s = new SoundBoardItem();
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
