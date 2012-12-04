package com.capstone.ocelot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnDragListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.capstone.ocelot.SoundBoardActivities.SoundBoardGridAdapter;
import com.capstone.ocelot.SoundBoardActivities.SoundBoardItem;
import com.capstone.ocelot.SoundBoardActivities.SoundBoardSequenceAdapter;


@TargetApi(15)
@SuppressWarnings("deprecation")
public class SoundBoardActivity extends Activity implements OnInitListener{
	
	ArrayList<SoundBoardItem> mGridItems;
	ArrayList<SoundBoardItem> mSequenceItems;
	SoundBoardItem mCurrentItem;
	Iterator<SoundBoardItem> sequenceIterator;
	MediaPlayer mPlayer;
	int currentLocation = 0;
	int MY_DATA_CHECK_CODE = 0;
	
	String userName = "Jesse";
	
	
//	Intent checkIntent = new Intent();
//	checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
//	startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
	
	private TextToSpeech ttsPlayer;
	
//	protected void onActivityResult(
//	        int requestCode, int resultCode, Intent data) {
//	    if (requestCode == MY_DATA_CHECK_CODE) {
//	        if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
//	            // success, create the TTS instance
//	            ttsPlayer = new TextToSpeech(this, (OnInitListener) this);
//	        } else {
//	            // missing data, install it
//	            Intent installIntent = new Intent();
//	            installIntent.setAction(
//	                TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
//	            startActivity(installIntent);
//	        }
//	    }
//	}
	
	public void onInit(int status) {
		// TODO Auto-generated method stub
		//ttsPlayer.speak("Are you ready to play " + userName + "?", TextToSpeech.QUEUE_FLUSH, null);
	}
	
	ScrollView scrollView;
	Gallery sequenceView;
	GridView gridView;
	
	SoundBoardSequenceAdapter seqAdapter;
	
    //TODO Rename all the classes to better explain their function
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//Text To Speech Player
		ttsPlayer = new TextToSpeech(this, this);
		ttsPlayer.setOnUtteranceProgressListener(TTSProgressListener);
		
		//Set and load the soundboardItems
		//TODO This will need to be extended so that we can load from a database!
		mGridItems = LoadSoundBoard(); //Load the initial items to the grid
		
		//Setup the gridview adapter
		//TODO This might be better done through a page view for scrolling the images.
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(new SoundBoardGridAdapter(this));
	    
		//Set and load the soundboardItems
		//TODO This will need to be extended so that we can load from a database!
	    mSequenceItems = LoadSequenceBoard();
	    
	    //Setup the Listener for the SequenceBar Container
	    scrollView = (ScrollView) findViewById(R.id.seqscrollview);
	    scrollView.setOnDragListener(new MyDragListener());
	    
	    //TODO Implement that horizontal List View: http://www.dev-smart.com/archives/34
		sequenceView = (Gallery) findViewById(R.id.seqgallery);
		seqAdapter = new SoundBoardSequenceAdapter(this); //TODO There must be a better way to update the adapter than having a global
		sequenceView.setAdapter(seqAdapter);
		sequenceView.setOnItemClickListener(new OnItemClickListener() {  //Play the sound associated with the object.
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				currentLocation = 0;
				sequenceView.setSelection(currentLocation);
				sequenceView.invalidate();
				sequenceIterator = mSequenceItems.iterator();
//				if(mPlayer.isPlaying()){ //TODO This might be necessary, but until this is fixed it crashes the program
//					mPlayer.stop();
//					mPlayer.release();
//				}
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
	}
	
	public SoundBoardItem getCurrentItem(){
		return mCurrentItem;
	}
	
	OnCompletionListener MediaCompletionListener = new OnCompletionListener() {
	    public void onCompletion(MediaPlayer mp) {
	    	AdvanceCurrentLocation();
	    	LoadNextSound();
	    }
	};
	
	
	//TODO Get this working properly.
	UtteranceProgressListener TTSProgressListener = new UtteranceProgressListener(){

		@Override
		public void onStart(String utteranceId) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDone(String utteranceId) {
			AdvanceCurrentLocation();
	    	LoadNextSound();
		}

		@Override
		public void onError(String utteranceId) {
			// TODO Auto-generated method stub
			
		}

	};
	
	public void AdvanceCurrentLocation(){
		if (currentLocation < mSequenceItems.size() - 1){
			currentLocation++;
			sequenceView.setSelection(currentLocation);
			sequenceView.invalidate();
		}		
	}
	
	class MyDragListener implements OnDragListener {
		public boolean onDrag(View v, DragEvent event) {
			//Log.v(NOTIFICATION_SERVICE, event.toString());
			switch (event.getAction()) {
			case DragEvent.ACTION_DROP: //If the drag ended on the sequence bar
					mSequenceItems.add(mCurrentItem);
					sequenceView.setAdapter(seqAdapter);
				break;
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
	
	@TargetApi(15)
	public void LoadNextSound(){
		SoundBoardItem item = new SoundBoardItem(null);
		
		if (sequenceIterator.hasNext()){
			item = sequenceIterator.next();
			if (item.getHasSound()){
				mPlayer = MediaPlayer.create(getBaseContext(), getUriForId(item.getSoundResourceId()));
				mPlayer.setOnCompletionListener(MediaCompletionListener);
				mPlayer.start();
			} else {				
//				HashMap<String, String> myHash = new HashMap<String, String>();
//              myHash.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, Long.toString(item.getItemId()));
				ttsPlayer.speak(item.getDescription(), TextToSpeech.QUEUE_ADD, null);
				//AdvanceCurrentLocation();
				LoadNextSound();
				
//				check for the presence of the TTS resources with the corresponding intent
//		        Intent checkIntent = new Intent();
//		        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
//		        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
			}
		} else {
			if (item.getHasSound())
				mPlayer.release();
			//else
				//ttsPlayer.shutdown();
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
	        	mSequenceItems = new ArrayList<SoundBoardItem>();
	        	sequenceView.setAdapter(seqAdapter);
	        	scrollView.invalidate();
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
		
	    //SoundBoardItem s = new SoundBoardItem(" ");
		//mLoadItems.add(s);
	
		return mLoadItems;
	}
	
	private ArrayList<SoundBoardItem> LoadSoundBoard(){
		
		ArrayList<SoundBoardItem> mLoadItems = new ArrayList<SoundBoardItem>();
		
	    SoundBoardItem s = new SoundBoardItem("Cougar");
		s.setIconResourceId(R.drawable.cougar);
		//s.setSoundResourceId(R.raw.cougar);
		mLoadItems.add(s);
		
		s = new SoundBoardItem("Chicken");
		s.setIconResourceId(R.drawable.chicken);
		//s.setSoundResourceId(R.raw.chicken);
		mLoadItems.add(s);
		
		s = new SoundBoardItem("Dog");
		s.setIconResourceId(R.drawable.dog);
		s.setSoundResourceId(R.raw.dog);
		mLoadItems.add(s);
		
		s = new SoundBoardItem("Elephant");
		s.setIconResourceId(R.drawable.elephant);
		//s.setSoundResourceId(R.raw.elephant);
		mLoadItems.add(s);
		
		s = new SoundBoardItem("Hug Me");
		//s.setIconResourceId(R.drawable.elephant);
		//s.setSoundResourceId(R.raw.elephant);
		mLoadItems.add(s);
		
		s = new SoundBoardItem("Please");
		//s.setIconResourceId(R.drawable.elephant);
		//s.setSoundResourceId(R.raw.elephant);
		mLoadItems.add(s);
		
		s = new SoundBoardItem("I Like");
		//s.setIconResourceId(R.drawable.elephant);
		//s.setSoundResourceId(R.raw.elephant);
		mLoadItems.add(s);
		
		s = new SoundBoardItem("Make me");
		//s.setIconResourceId(R.drawable.elephant);
		//s.setSoundResourceId(R.raw.elephant);
		mLoadItems.add(s);
		
		s = new SoundBoardItem("Drawing");
		//s.setIconResourceId(R.drawable.elephant);
		//s.setSoundResourceId(R.raw.elephant);
		mLoadItems.add(s);
		
		s = new SoundBoardItem("Playing");
		//s.setIconResourceId(R.drawable.elephant);
		//s.setSoundResourceId(R.raw.elephant);
		mLoadItems.add(s);
		
		s = new SoundBoardItem("and");
		//s.setIconResourceId(R.drawable.elephant);
		//s.setSoundResourceId(R.raw.elephant);
		mLoadItems.add(s);
		
		s = new SoundBoardItem("You");
		//s.setIconResourceId(R.drawable.elephant);
		//s.setSoundResourceId(R.raw.elephant);
		mLoadItems.add(s);
		
		s = new SoundBoardItem("Hug Me");
		//s.setIconResourceId(R.drawable.elephant);
		//s.setSoundResourceId(R.raw.elephant);
		mLoadItems.add(s);
		
		return mLoadItems;
	}
}
