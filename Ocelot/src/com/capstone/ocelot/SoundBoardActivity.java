package com.capstone.ocelot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.capstone.ocelot.SoundBoardActivities.SoundBoardGridAdapter;
import com.capstone.ocelot.SoundBoardActivities.SoundBoardItem;
import com.capstone.ocelot.SoundBoardActivities.SoundBoardSequenceAdapter;


@TargetApi(15)
@SuppressWarnings("deprecation")
public class SoundBoardActivity extends Activity implements OnInitListener{

	ArrayList<SoundBoardItem> mGridItems;
	ArrayList<SoundBoardItem> mSequenceItems;
	int mSequenceLocation = 0;
	
	SoundBoardItem mCurrentItem;
	Iterator<SoundBoardItem> sequenceIterator;
	MediaPlayer mPlayer;
	//MediaRecorder mRecorder;
	
	
	int MY_DATA_CHECK_CODE = 0;
	String userName = "Jesse";
	private TextToSpeech ttsPlayer;

	ScrollView scrollView;
	Gallery sequenceView;
	GridView gridView;
	
	boolean isDeleteMode = false;

	SoundBoardSequenceAdapter seqAdapter;

	void SequenceNext(){
		AdvanceCurrentLocation();
		LoadNextSound();
	}

	OnCompletionListener MediaCompletionListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mp) {
			SequenceNext();
		}
	};

	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			int result = ttsPlayer.setLanguage(Locale.US);
			if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Toast.makeText(this, "Language not supported", Toast.LENGTH_LONG).show();
				Log.e("TTS", "Language is not supported");
				//TODO Let the user know they can begin.
			} 
		} else {    //TTS is not initialized properly
			Toast.makeText(this, "TTS Initilization Failed", Toast.LENGTH_LONG).show();
			Log.e("TTS", "Initilization Failed");
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Log.v(Context.AUDIO_SERVICE, data.toString());
		if (requestCode == MY_DATA_CHECK_CODE)
		{
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
			{
				// success, create the TTS instance
				ttsPlayer = new TextToSpeech(this, this);
			}
			else
			{
				// missing data, install it
				Intent installIntent = new Intent();
				installIntent.setAction(
						TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
			}
		}
	}

	//TODO Rename all the classes to better explain their function
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//TODO Get the width and height of the screen to set the size of the pictures.
		//Will need to do this in order to set the columns/rows up properly.
		//    	DisplayMetrics metrics = new DisplayMetrics();
		//    	getWindowManager().getDefaultDisplay().getMetrics(metrics);
		//    	@SuppressWarnings("unused")
		//		int height = metrics.heightPixels;
		//    	@SuppressWarnings("unused")
		//		int width = metrics.widthPixels;

		//Install an Intent for using the TTS
		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);

		//Set and load the soundboardItems
		//TODO This will need to be extended so that we can load from a database!
		mGridItems = LoadSoundBoard(); //Load the initial items to the grid

		//Setup the gridview adapter
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(new SoundBoardGridAdapter(this));

		//Set and load the soundboardItems
		//TODO This will need to be extended so that we can load from a database!
		mSequenceItems = LoadSequenceBoard();

		//Setup the Listener for the SequenceBar Container
		scrollView = (ScrollView) findViewById(R.id.seqscrollview);
		scrollView.setOnDragListener(new MyDragListener());

		sequenceView = (Gallery) findViewById(R.id.seqgallery);
		seqAdapter = new SoundBoardSequenceAdapter(this); //TODO There must be a better way to update the adapter than having a global
		sequenceView.setAdapter(seqAdapter);
		sequenceView.setOnItemClickListener(new OnItemClickListener() {  //Play the sound associated with the object.
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//Reset the position of the sequence bar
				mSequenceLocation = 0;
				sequenceView.setSelection(mSequenceLocation);
				
				//sequenceView.invalidate();
				sequenceIterator = mSequenceItems.iterator();
				LoadNextSound();
			}
		});	   
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

	public void AdvanceCurrentLocation(){
		if (mSequenceLocation < mSequenceItems.size() - 1){
			mSequenceLocation++;
			sequenceView.setSelection(mSequenceLocation);
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

	@TargetApi(15)
	public void LoadNextSound(){
		SoundBoardItem item = new SoundBoardItem(null);

		if (sequenceIterator.hasNext()){
			item = sequenceIterator.next();
			if (item.getHasSound()){
				mPlayer = item.getMediaPlayer(getBaseContext());
				mPlayer.setOnCompletionListener(MediaCompletionListener);
				mPlayer.start();
			} else {				
				ttsPlayer.speak(item.getDescription(), TextToSpeech.QUEUE_ADD, null);
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
		case R.id.new_game: //TODO Add 'shake and clear' http://stackoverflow.com/questions/2317428/android-i-want-to-shake-it
			showNewGameDialog();
			return true;
		case R.id.new_item:
			showNewItemDialog();
			return true;
		case R.id.delete_mode:
			showDeleteModeDialog();
		case R.id.help:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	String sanitizePath(String path) {
	    if (!path.startsWith("/")) {
	      path = "/" + path;
	    }
	    if (!path.contains(".")) {
	      path += ".3gp";
	    }
	    return Environment.getExternalStorageDirectory().getAbsolutePath() + path;
	}
	
	void showNewItemDialog(){
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = getLayoutInflater();		
		ViewGroup parent = (ViewGroup) inflater.inflate(R.layout.popup, null);
		
		final EditText dItemName = (EditText) parent.findViewById(R.id.item_name);
		final ImageButton recordButton = (ImageButton) parent.findViewById(R.id.record_button);
		recordButton.setEnabled(false);
		final ImageButton playButton = (ImageButton) parent.findViewById(R.id.play_button);
		playButton.setEnabled(false);
		
		//Setup Description Text
		dItemName.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.length() > 0){
					recordButton.setEnabled(true);
					playButton.setEnabled(true);
					//builder.getButton(builder.BUTTON1).setEnabled(false);
					mCurrentItem = new SoundBoardItem(dItemName.getText().toString());
				} else {
					recordButton.setEnabled(false);
					playButton.setEnabled(false);
				}
			}
		});
		
		//Setup Media Recorder
		final MediaRecorder mediaRecorder = new MediaRecorder();
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mediaRecorder.setAudioChannels(1);
		mediaRecorder.setAudioSamplingRate(8000);
		mediaRecorder.setMaxDuration(3000); //TODO Must add ability to stop the recording.
		
		File directory = new File(Environment.getExternalStorageDirectory() + File.separator + "sounds");
		directory.mkdirs();
		
		//TODO http://www.benmccann.com/dev-blog/android-audio-recording-tutorial/
		recordButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String finalPath = sanitizePath("/sounds/" + dItemName.getText().toString());
				mediaRecorder.setOutputFile(finalPath);
				mCurrentItem.setSoundResourceId(finalPath);
				try {
					mediaRecorder.prepare();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				mediaRecorder.start();
			}
		}); //TODO This should enable the play button, not necessarily the description field.
		
		//Setup Media Player
		playButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mCurrentItem.getHasSound()){
					mPlayer = mCurrentItem.getMediaPlayer(getBaseContext());
					mPlayer.start();
				}
			}
		});
		
		builder.setView(parent);
		builder.setTitle("Create a New Item");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				mGridItems.add(mCurrentItem);
			}
		});
		builder.setNegativeButton("CANCEL", null);
		builder.create().show();
	}
	
	//NEW GAME CREATION
	void showNewGameDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		//builder.setIcon(R.drawable.elephant);
		builder.setTitle("New Game");
		builder.setInverseBackgroundForced(true);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				mSequenceItems = new ArrayList<SoundBoardItem>();
				sequenceView.setAdapter(seqAdapter);
				scrollView.invalidate();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	

	//NEW GAME CREATION
	void showDeleteModeDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle("Delete Items?");
		builder.setInverseBackgroundForced(true);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				isDeleteMode = true;
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				isDeleteMode = false;
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	
	
	public void AddItemToGrid(SoundBoardItem newItem){
		mGridItems.add(newItem);
	}

	private ArrayList<SoundBoardItem> LoadSequenceBoard(){
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
		//s.setSoundResourceId(R.raw.dog);
		mLoadItems.add(s);

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
		//s.setSoundResourceId(R.raw.dog);
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
