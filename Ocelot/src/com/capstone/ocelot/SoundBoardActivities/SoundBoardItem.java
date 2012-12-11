package com.capstone.ocelot.SoundBoardActivities;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class SoundBoardItem {
	private long mItemId = 0;
	private String mDescription = "";
	private int mSoundResourceId = -1;
	private String mSoundResourceLocation = "";
	private int mIconResourceId = -1;
	private boolean mhasSound = false;
	private boolean mhasImage = false;
	private boolean isExternalAudio = false;
	public SoundBoardItem(String description) { mDescription = description; } //You must have a description to create a SoundBoardItem
	public void setItemId(long itemId) { mItemId = itemId; }
	public void setHasSound(boolean hasSound) { mhasSound = hasSound; }
	public boolean getHasImage() { return mhasImage; }
	public void setHasImage(boolean hasImage) { mhasImage = hasImage; }
	public boolean getHasSound() { return mhasSound; }
	public boolean isExternalSound() { return isExternalAudio; }
	public void setExternalSound(boolean isExternal) { isExternalAudio = isExternal; }
	public long getItemId() { return mItemId; }
	public void setDescription(String description) { mDescription = description;}
	public String getDescription() { return mDescription; }
	public void setIconResourceId(int id) { mIconResourceId = id; setHasImage(true);}
	public int getIconResourceId() { return mIconResourceId; }
	
	public void setSoundResourceId(int id) { mSoundResourceId = id; setHasSound(true);}
	public void setSoundResourceId(String location) { mSoundResourceLocation = location; setHasSound(true); setExternalSound(true);}
	public int getSoundResourceId() { return mSoundResourceId; }
	public String getSoundResourceLocation() { return mSoundResourceLocation; }
	public MediaPlayer getMediaPlayer(Context context){
		if (isExternalSound() && mhasSound)
			return MediaPlayer.create(context, Uri.parse(getSoundResourceLocation()));
		else if(!isExternalSound() && mhasSound)
			return MediaPlayer.create(context, getSoundResourceId());
		return null;
	}
}
