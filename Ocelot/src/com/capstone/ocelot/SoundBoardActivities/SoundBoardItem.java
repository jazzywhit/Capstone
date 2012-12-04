package com.capstone.ocelot.SoundBoardActivities;

public class SoundBoardItem {
	private long mItemId = 0;
	private String mDescription = "";
	private int mSoundResourceId = -1;
	private int mIconResourceId = -1;
	private boolean mhasSound = false;
	private boolean mhasImage = false;
	public SoundBoardItem(String description) { mDescription = description; } //You must have a description to create a SoundBoardItem
	public void setItemId(long itemId) { mItemId = itemId; }
	public void setHasSound(boolean hasSound) { mhasSound = hasSound; }
	public boolean getHasImage() { return mhasImage; }
	public void setHasImage(boolean hasImage) { mhasImage = hasImage; }
	public boolean getHasSound() { return mhasSound; }
	public long getItemId() { return mItemId; }
	public void setDescription(String description) { mDescription = description;}
	public String getDescription() { return mDescription; }
	public void setSoundResourceId(int id) { mSoundResourceId = id; setHasSound(true);}
	public int getSoundResourceId() { return mSoundResourceId; }
	public void setIconResourceId(int id) { mIconResourceId = id; setHasImage(true);}
	public int getIconResourceId() { return mIconResourceId; }
}
