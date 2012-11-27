package com.capstone.ocelot;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.Toast;

import com.capstone.ocelot.SoundBoardActivities.SoundBoardGridAdapter;
import com.capstone.ocelot.SoundBoardActivities.SoundBoardItem;
import com.capstone.ocelot.SoundBoardActivities.SoundBoardSequenceAdapter;


@SuppressWarnings("deprecation")
public class SoundBoardActivity extends Activity {
	
	ArrayList<SoundBoardItem> mGridItems;
	ArrayList<SoundBoardItem> mSequenceItems;
	SoundBoardItem mCurrentItem;
	Iterator<SoundBoardItem> sequenceIterator;
	MediaPlayer mPlayer;
	
	Gallery sequenceView;
	GridView gridView;
	
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
		((GridView) gridView).setAdapter(new SoundBoardGridAdapter(this));
	    
		//Set and load the soundboardItems
		//TODO This will need to be extended so that we can load from a database!
	    mSequenceItems = LoadSequenceBoard();
	    
	    //Setup the sequence adapter
	    //TODO Implement that horizontal List View: http://www.dev-smart.com/archives/34
		sequenceView = (Gallery) findViewById(R.id.seqgallery);
		seqAdapter = new SoundBoardSequenceAdapter(this);
		((Gallery) sequenceView).setAdapter(seqAdapter);
		sequenceView.setOnDragListener(new MyDragListener());
		sequenceView.setOnItemClickListener(new OnItemClickListener() {  //Play the sound associated with the object.
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				sequenceView.setSelection(0);
				sequenceIterator = mSequenceItems.iterator();
				LoadNextSound();
			}
		});	   
	    
	    //TODO Get the width and height of the screen to set the size of the pictures.
	    //Will need to do this in order to set the columns/rows up properly.
    	//DisplayMetrics metrics = new DisplayMetrics();
    	//getWindowManager().getDefaultDisplay().getMetrics(metrics);
    	//int height = metrics.heightPixels;
    	//int width = metrics.widthPixels;
	}
	
	public ArrayList<SoundBoardItem> getGridItems(){
		return mGridItems;
	}
	
	public SoundBoardItem getGridItem(int index){
		return mGridItems.get(index);
	}
	
	public ArrayList<SoundBoardItem> getSequenceitems(){
		return mSequenceItems;
	}
	
	public SoundBoardItem getSequenceItem(int index){
		return mSequenceItems.get(index);
	}
	
	public int getGridSize(){
		return mGridItems.size();
	}
	
	public int getSequenceSize(){
		return mSequenceItems.size();
	}
	
	public void removeSequenceItem(int position){
		mSequenceItems.remove(position);
	}
	
	public void setCurrentItemFromGrid(int position){
		setCurrentItem(mGridItems.get(position));
	}
	
	public void setCurrentItemFromSequence(int position){
		setCurrentItem(mSequenceItems.get(position));
	}
	
	public void setCurrentItem(SoundBoardItem currentItem){
		mCurrentItem = currentItem;
		Toast.makeText(getBaseContext(), "Current Item: " + mCurrentItem.getDescription(), Toast.LENGTH_SHORT).show();
	}
	
	public SoundBoardItem getCurrentItem(){
		return mCurrentItem;
	}
	
	OnCompletionListener MyCompletionListener = new OnCompletionListener() {
	    public void onCompletion(MediaPlayer mp) {
	    	LoadNextSound();
	    }
	};
	
	class MyDragListener implements OnDragListener {
//	    Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
//	    Drawable normalShape = getResources().getDrawable(R.drawable.shape);

		public boolean onDrag(View v, DragEvent event) {
			Log.v("log_tag", event.toString());
			
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
    //		       	Dropped, reassign View to ViewGroup
	//		        View view = (View) event.getLocalState();
	//		        ViewGroup owner = (ViewGroup) view.getParent();
	//		        owner.removeView(view);
	//		        LinearLayout container = (LinearLayout) v;
	//		        container.addView(view);
	//		        view.setVisibility(View.VISIBLE);
					mSequenceItems.add(mCurrentItem);
					//ViewGroup vg = (ViewGroup) findViewById(R.id.seqgallery);
					//vg.invalidate();
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
	
//	private void myslideshow()
//    {
//                    itemPosition = ((Gallery)this).getSelectedItemPosition();             
//                    if (PicPosition >=  Pictures.size())            
//                    PicPosition =  gallery.getSelectedItemPosition(); //stop 
//                                    else
//                    gallery.setSelection(PicPosition);//move to the next gallery element.
//    }
	
	public void LoadNextSound(){
		if (sequenceIterator.hasNext()){
			SoundBoardItem item = sequenceIterator.next();
			//sequenceView.setSelection(sequenceView.getSelectedItemPosition() + 1); //Advance the view by 1
			mPlayer = MediaPlayer.create(getBaseContext(), getUriForId(item.getSoundResourceId()));
			mPlayer.setOnCompletionListener(MyCompletionListener);
			mPlayer.start();
			//Toast.makeText(getBaseContext(), item.getDescription(), Toast.LENGTH_SHORT).show();
		} else {
			mPlayer.release();
		}
	}
	
	public Uri getUriForId(int resId){
		Resources resources = getBaseContext().getResources();
		return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
				resources.getResourcePackageName(resId) + '/' +
				resources.getResourceTypeName(resId) + '/' +
				resources.getResourceEntryName(resId) );
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
