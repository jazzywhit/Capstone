package com.capstone.ocelot.SoundBoardActivities;

public class SoundBoardItem {
	private long mItemId = 0;
	private String mDescription = "";
	private int mSoundResourceId = -1;
	private int mIconResourceId = -1;
	public void setItemId(long itemId) { mItemId = itemId; }
	public long getItemId() { return mItemId; }
	public void setDescription(String description) { mDescription = description;}
	public String getDescription() { return mDescription; }
	public void setSoundResourceId(int id) { mSoundResourceId = id; }
	public int getSoundResourceId() { return mSoundResourceId; }
	public void setIconResourceId(int id) { mIconResourceId = id; }
	public int getIconResourceId() { return mIconResourceId; }
}
