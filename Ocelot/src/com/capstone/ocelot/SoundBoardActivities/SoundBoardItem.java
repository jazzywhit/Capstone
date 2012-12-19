package com.capstone.ocelot.SoundBoardActivities;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.speech.tts.TextToSpeech;

public class SoundBoardItem implements Comparable<SoundBoardItem>{
	
	@Override
	public int compareTo(SoundBoardItem another) {
		return another.getNumPlays().compareTo(this.getNumPlays());
	}
	
	private long mItemId = 0;
	private String mDescription = "";
	private int mSoundResourceId = -1;
	private String mSoundResourceLocation = "";
	private int mIconResourceId = -1;
	private boolean mHasImage = false;
	private boolean mHasSound = false;
	private boolean isExternalAudio = false;
	private long numPlays = 0;
	
	Context mContext;
	TextToSpeech ttsPlayer;
	
	public SoundBoardItem(Context context, String description) { mDescription = description; mContext = context; } //You must have a description to create a SoundBoardItem
	public void setItemId(long itemId) { mItemId = itemId; }
	public boolean hasImage() { return mHasImage; }
	public boolean hasSound() { return mHasSound; }
	public boolean isExternalSound() { return isExternalAudio; }
	public void setExternalSound(boolean isExternal) { isExternalAudio = isExternal; }
	public long getItemId() { return mItemId; }
	public void setDescription(String description) { mDescription = description;}
	public String getDescription() { return mDescription; }
	public void setIconResourceId(int id) { mIconResourceId = id; mHasImage = true;}
	public int getIconResourceId() { return mIconResourceId; }
	public void setSoundResourceId(int id) { mSoundResourceId = id; isExternalAudio = false; mHasSound = true;}
	public void setSoundResourceId(String location) { mSoundResourceLocation = location; isExternalAudio = true; mHasSound = false; }
	public int getSoundResourceId() { return mSoundResourceId; }
	public String getSoundResourceLocation() { return mSoundResourceLocation; }
	public Long getNumPlays() { return numPlays; }
	
	public MediaPlayer getMediaPlayer(Context context){
		//Increment the number of plays
		numPlays++;
		
		 //Return the MediaPlayer
		if (isExternalSound())
			return MediaPlayer.create(context, Uri.parse(getSoundResourceLocation()));
		else
			return MediaPlayer.create(context, getSoundResourceId());
	}
	
	String sanitizePath(String path) {
	    if (!path.startsWith("/")) {
	      path = "/" + path;
	    }
	    if (!path.contains(".")) {
	      path += ".wav";
	    }
	    return Environment.getExternalStorageDirectory().getAbsolutePath() + path;
	}
}
