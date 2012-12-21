package com.capstone.ocelot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.capstone.ocelot.SoundBoardActivities.SoundBoardGridAdapter;
import com.capstone.ocelot.SoundBoardActivities.SoundBoardItem;
import com.capstone.ocelot.SoundBoardActivities.SoundBoardSequenceAdapter;

@TargetApi(11)
public class SoundBoardActivity extends Activity implements TextToSpeech.OnInitListener{

	ArrayList<SoundBoardItem> mGridItems;
	ArrayList<SoundBoardItem> mSequenceItems;
	int mSequenceLocation = 0;
	
	Intent BrowsePictureIntent;
	
	SoundBoardItem mCurrentItem;
	Iterator<SoundBoardItem> sequenceIterator;
	MediaPlayer mPlayer;
	//MediaRecorder mRecorder;
	
	//New Item Window
	ImageButton picButton;
	Uri newItemURI;
	
	//int MY_DATA_CHECK_CODE = 0;
	static final int PICK_IMAGE = 1;
	String userName = "Jesse";
	private TextToSpeech ttsPlayer;
	ScrollView scrollView;
	Gallery sequenceView;
	GridView gridView;
	
	boolean isDeleteMode = false;
	
	SoundBoardSequenceAdapter seqAdapter;
	SoundBoardGridAdapter gridAdapter;

	void SequenceNext(){
		AdvanceCurrentLocation();
		LoadNextSound();
	}

	OnCompletionListener MediaCompletionListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mp) {
			SequenceNext();
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//Create Directory to store sounds in; ignore if already created
		File directory = new File(Environment.getExternalStorageDirectory() + File.separator + "sounds");
		directory.mkdirs();
		
		//Set and load the soundboardItems
		//TODO This will need to be extended so that we can load from a database!
		mGridItems = LoadSoundBoard(); //Load the initial items to the grid

		//Setup the gridview adapter
		gridView = (GridView) findViewById(R.id.gridview);
		gridAdapter = new SoundBoardGridAdapter(this);
		gridView.setAdapter(gridAdapter);

		//Set and load the soundboardItems
		//TODO This will need to be extended so that we can load from a database!
		mSequenceItems = LoadSequenceBoard();

		//Setup the Listener for the SequenceBar Container
		scrollView = (ScrollView) findViewById(R.id.seqscrollview);
		scrollView.setOnDragListener(new MyDragListener());
		
		//Create the TTS Device
		ttsPlayer = new TextToSpeech(this, this);

		sequenceView = (Gallery) findViewById(R.id.seqgallery);
		seqAdapter = new SoundBoardSequenceAdapter(this);
		sequenceView.setAdapter(seqAdapter);
		sequenceView.setOnItemClickListener(new OnItemClickListener() {  //Play the sound associated with the object.
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//Reset the position of the sequence bar
				mSequenceLocation = arg2;
				sequenceView.setSelection(mSequenceLocation);
				sequenceView.invalidate();
				sequenceIterator = mSequenceItems.iterator();
				LoadNextSound(arg2);
			}
		});	   
	}
	
	public void UpdateGrid() {
		Collections.sort(mGridItems);
		gridView.setAdapter(gridAdapter);
		gridView.invalidate();
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
	
	public void addSequenceItem(){
		if (mCurrentItem != null)
			mSequenceItems.add(mCurrentItem);
		updateSequenceBar();
	}
	
	public void addGridItem(){
		if (mCurrentItem != null)
			mGridItems.add(mCurrentItem);
		VerifySoundBank(mGridItems);
		UpdateGrid();
	}

	class MyDragListener implements OnDragListener {
		public boolean onDrag(View v, DragEvent event) {
			switch (event.getAction()) {
			case DragEvent.ACTION_DROP: //If the drag ended on the sequence bar
				addSequenceItem();
				break;
			default:
				break;
			}
			return true;
		}
	}
	
	public void updateSequenceBar(){
		sequenceView.setAdapter(seqAdapter);
		sequenceView.invalidate();
	}
	
	public void removeGridItem(int position){
		mGridItems.remove(position);
		UpdateGrid();
	}

	public void LoadNextSound(){
		SoundBoardItem item;
		if (sequenceIterator.hasNext()){
			item = sequenceIterator.next();
			mPlayer = item.getMediaPlayer(getBaseContext());
			mPlayer.setOnCompletionListener(MediaCompletionListener);
			mPlayer.start();
		} else {
			mPlayer.release();
		}
	}
	
	public boolean isDeleteMode(){
		return isDeleteMode;
	}
	
	//Advance a number of positions so that you are on the one that the user has clicked.
	public void LoadNextSound(int position){
		for (int i = 0; i < position; i++){
			sequenceIterator.next();
		}
		LoadNextSound();
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
			if (isDeleteMode) {
				endDeleteMode(item);
			} else {
				showDeleteModeDialog(item);
			}
		case R.id.help:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void endDeleteMode(MenuItem item){
		item.setTitle(R.string.delete_mode);
		isDeleteMode = false;
		UpdateGrid();
	}

	String sanitizePath3gp(String path) {
	    if (!path.startsWith("/")) {
	      path = "/" + path;
	    }
	    if (!path.contains(".")) {
	      path += ".3gp";
	    }
	    return Environment.getExternalStorageDirectory().getAbsolutePath() + path;
	}
	
	String sanitizePathWav(String path) {
	    if (!path.startsWith("/")) {
	      path = "/" + path;
	    }
	    if (!path.contains(".")) {
	      path += ".wav";
	    }
	    return Environment.getExternalStorageDirectory().getAbsolutePath() + path;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if (requestCode == PICK_IMAGE) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
//	        	newItemURI = data.getData();
	        	picButton.setImageURI(data.getData());
	        	//mCurrentItem.setIconResourceId(data.getData());
	            // The user picked a contact.
	            // The Intent's data Uri identifies which contact was selected.
	            // Do something with the contact here (bigger example below)
	        }
	    }
	}
	
	//NEW ITEM
	void showNewItemDialog(){
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		LayoutInflater inflater = getLayoutInflater();		
		ViewGroup parent = (ViewGroup) inflater.inflate(R.layout.popup, null);
		
		final EditText dItemName = (EditText) parent.findViewById(R.id.item_name);
		final ImageButton recordButton = (ImageButton) parent.findViewById(R.id.record_button);
		recordButton.setEnabled(false);
		final ImageButton playButton = (ImageButton) parent.findViewById(R.id.play_button);
		playButton.setEnabled(false);
		
		picButton = (ImageButton) parent.findViewById(R.id.image_button);
		picButton.setEnabled(false);
		
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
					picButton.setEnabled(true);
					mCurrentItem = new SoundBoardItem(getBaseContext(), dItemName.getText().toString());
				} else {
					recordButton.setEnabled(false);
					playButton.setEnabled(false);
					picButton.setEnabled(false);
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
		mediaRecorder.setMaxDuration(3000);
		
		picButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BrowsePictureIntent = new Intent();
				BrowsePictureIntent.setType("image/*");
//				intent.setDataAndType(Uri.parse(url), "image/*");
				BrowsePictureIntent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(BrowsePictureIntent, "Select Picture"), PICK_IMAGE);
				Log.v(Activity.ACCESSIBILITY_SERVICE, "Hello");
			}
		});
		
		//TODO http://www.benmccann.com/dev-blog/android-audio-recording-tutorial/
		recordButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String finalPath = sanitizePath3gp("/sounds/" + dItemName.getText().toString());
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
				mPlayer = mCurrentItem.getMediaPlayer(getBaseContext());
				mPlayer.start();
			}
		});
		
		builder.setView(parent);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				addGridItem();
			}
		});
		builder.setNegativeButton("CANCEL", null);
		builder.create().show();
	}
	
	//NEW GAME CREATION
	void showNewGameDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle("New Game");
		builder.setInverseBackgroundForced(true);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				mSequenceItems = new ArrayList<SoundBoardItem>();
				sequenceView.setAdapter(seqAdapter);
				scrollView.invalidate();
				dialog.dismiss();
				UpdateGrid(); //Update the grid, sorted by number of plays.
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	
	//DELETE MODE
	void showDeleteModeDialog(final MenuItem menuItem){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle("Delete Items?");
		builder.setInverseBackgroundForced(true);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				isDeleteMode = true;
				menuItem.setTitle(R.string.delete_mode_off);
				dialog.dismiss();
				UpdateGrid();
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				isDeleteMode = false;
				menuItem.setTitle(R.string.delete_mode);
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private ArrayList<SoundBoardItem> LoadSequenceBoard(){
		if (mSequenceItems != null)
			return mSequenceItems;
		ArrayList<SoundBoardItem> mLoadItems = new ArrayList<SoundBoardItem>();
		return mLoadItems;
	}
	
	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			int result = ttsPlayer.setLanguage(Locale.US);
			if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Toast.makeText(this, "Language not supported", Toast.LENGTH_LONG).show();
				Log.e("TTS", "Language is not supported");
			}
			//ttsPlayer.speak("Welcome" + userName, TextToSpeech.QUEUE_ADD, null);
			VerifySoundBank(mGridItems);
			VerifySoundBank(mSequenceItems);
		} else {    //TTS is not initialized properly
			Toast.makeText(this, "TTS Initilization Failed", Toast.LENGTH_LONG).show();
			Log.e("TTS", "Initilization Failed");
		}
		UpdateGrid(); //Do a final update once everything is loaded.
	}
	
	private void VerifySoundBank(ArrayList<SoundBoardItem> soundBank){
		for (SoundBoardItem item : soundBank){
			if (!item.hasSound()){
				HashMap<String, String> myHashRender = new HashMap<String, String>();
				String destFileName = sanitizePathWav("/sounds/" + item.getDescription()).replaceAll("\\s",""); //Remove all white space on the file name as well
				myHashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, item.getDescription());
				ttsPlayer.synthesizeToFile(item.getDescription(), myHashRender, destFileName);
				item.setSoundResourceId(destFileName);
			}
		}
	}

	private ArrayList<SoundBoardItem> LoadSoundBoard(){

		//Check if the Items are already created.
		if (mGridItems != null){
			return mGridItems;
		}
		
		ArrayList<SoundBoardItem> mLoadItems = new ArrayList<SoundBoardItem>();

		SoundBoardItem s = new SoundBoardItem(this, "Cougar");
		s.setIconResourceId(R.drawable.cougar);
		s.setSoundResourceId(R.raw.cougar);
		mLoadItems.add(s);

		s = new SoundBoardItem(this, "Chicken");
		s.setIconResourceId(R.drawable.chicken);
		s.setSoundResourceId(R.raw.chicken);
		mLoadItems.add(s);

		s = new SoundBoardItem(this, "Dog");
		s.setIconResourceId(R.drawable.dog);
		s.setSoundResourceId(R.raw.dog);
		mLoadItems.add(s);

		s = new SoundBoardItem(this, "Elephant");
		s.setIconResourceId(R.drawable.elephant);
		s.setSoundResourceId(R.raw.elephant);
		mLoadItems.add(s);

		s = new SoundBoardItem(this, "Hug Me");
		mLoadItems.add(s);
		
		s = new SoundBoardItem(this, "Friend");
		mLoadItems.add(s);
		
		s = new SoundBoardItem(this, "Love");
		mLoadItems.add(s);

		return mLoadItems;
	}
}
