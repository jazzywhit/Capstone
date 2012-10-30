package com.capstone.ocelot;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.AbsSpinner;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.capstone.ocelot.SoundBoard.SoundBoardGridAdapter;
import com.capstone.ocelot.SoundBoard.SoundBoardItem;
import com.capstone.ocelot.SoundBoard.SoundBoardSequenceAdapter;


@SuppressWarnings("deprecation")
public class Default extends Activity {
	
	ArrayList<SoundBoardItem> mGridItems;
	ArrayList<SoundBoardItem> mSequenceItems;
	
	static Gallery sequenceView;
	static GridView gridView;
	
	public SoundBoardItem currentItem;
	SoundBoardSequenceAdapter seqAdapter;
	
    //TODO Rename all the classes to better explain their function
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//Set and load the soundboardItems
		//TODO This will need to be extended so that we can load from a database!
		mGridItems = LoadSoundBoard(); //Load the initial items to the grid
		
		//Setup the gridview adapter
		//TODO This might be better done through a page view for scrolling the images.
		gridView = (GridView) findViewById(R.id.gridview);
		((GridView) gridView).setAdapter(new SoundBoardGridAdapter(this, mGridItems));
	    
		//Set and load the soundboardItems
		//TODO This will need to be extended so that we can load from a database!
	    mSequenceItems = LoadSequenceBoard();
	    
	    //Setup the sequence adapter
	    //TODO Implement that horizontal List View: http://www.dev-smart.com/archives/34
		sequenceView = (Gallery) findViewById(R.id.seqgallery);
		seqAdapter = new SoundBoardSequenceAdapter(this, mSequenceItems);
		((Gallery) sequenceView).setAdapter(seqAdapter);
		sequenceView.setOnDragListener(new MyDragListener());
	   
	    
	    //TODO Get the width and height of the screen to set the size of the pictures.
	    //Will need to do this in order to set the columns/rows up properly.
    	//DisplayMetrics metrics = new DisplayMetrics();
    	//getWindowManager().getDefaultDisplay().getMetrics(metrics);
    	//int height = metrics.heightPixels;
    	//int width = metrics.widthPixels;
	}
	
	
	
	class MyDragListener implements OnDragListener {
//	    Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
//	    Drawable normalShape = getResources().getDrawable(R.drawable.shape);

		public boolean onDrag(View v, DragEvent event) {
			//Log.v("log_tag", event.toString());
			switch (event.getAction()) {
	//		      case DragEvent.ACTION_DRAG_STARTED:
	//		        // Do nothing
	//		        break;
	//		      case DragEvent.ACTION_DRAG_ENTERED:
	//		        v.setBackgroundDrawable(enterShape);
	//		        break;
	//		      case DragEvent.ACTION_DRAG_EXITED:
	//		        v.setBackgroundDrawable(normalShape);
	//		        break;
			case DragEvent.ACTION_DROP:
		        // Dropped, reassign View to ViewGroup
	//		        View view = (View) event.getLocalState();
	//		        ViewGroup owner = (ViewGroup) view.getParent();
	//		        owner.removeView(view);
	//		        LinearLayout container = (LinearLayout) v;
	//		        container.addView(view);
	//		        view.setVisibility(View.VISIBLE);
		    	//Toast.makeText(mContext, "ACTION DROP", Toast.LENGTH_SHORT).show();
					Log.v("log_tag", v.toString());
					//TODO This is a hack. The data should always be gathered from the same location. It is now represented in the Activity and the Adapters.
					seqAdapter.addItem(mSequenceItems.get(0));
					((Gallery) sequenceView).setAdapter(seqAdapter);
					//sequenceView.invalidate();
				break;
	//		      case DragEvent.ACTION_DRAG_ENDED:
	//		        v.setBackgroundDrawable(normalShape);
			default:
				break;
	      }
	      return true;
	    }
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
	
	private ArrayList<SoundBoardItem> LoadSequenceBoard(){
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
		s.setDescription("Chicken");
		s.setIconResourceId(R.drawable.chicken);
		s.setSoundResourceId(R.raw.chicken);
		mLoadItems.add(s);
	
		return mLoadItems;
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
