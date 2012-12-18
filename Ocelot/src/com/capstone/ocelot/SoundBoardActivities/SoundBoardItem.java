package com.capstone.ocelot.SoundBoardActivities;

import java.util.HashMap;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;

public class SoundBoardItem{
	private long mItemId = 0;
	private String mDescription = "";
	private int mSoundResourceId = -1;
	private String mSoundResourceLocation = "";
	private int mIconResourceId = -1;
	private boolean mhasImage = false;
	private boolean isExternalAudio = false;
	Context mContext;
	TextToSpeech ttsPlayer;
	
	public SoundBoardItem(Context context, String description) { mDescription = description; mContext = context; } //You must have a description to create a SoundBoardItem
	public void setItemId(long itemId) { mItemId = itemId; }
	public boolean getHasImage() { return mhasImage; }
	public void setHasImage(boolean hasImage) { mhasImage = hasImage; }
	public boolean isExternalSound() { return isExternalAudio; }
	public void setExternalSound(boolean isExternal) { isExternalAudio = isExternal; }
	public long getItemId() { return mItemId; }
	public void setDescription(String description) { mDescription = description;}
	public String getDescription() { return mDescription; }
	public void setIconResourceId(int id) { mIconResourceId = id; setHasImage(true);}
	public int getIconResourceId() { return mIconResourceId; }
	public void setSoundResourceId(int id) { mSoundResourceId = id; setExternalSound(false); }
	public void setSoundResourceId(String location) { mSoundResourceLocation = location; setExternalSound(true); }
	public int getSoundResourceId() { return mSoundResourceId; }
	public String getSoundResourceLocation() { return mSoundResourceLocation; }
	
//	public void Init(){
//		if (!mhasSound){
//			ttsPlayer = new TextToSpeech(mContext, this);
//			
//			//HashMap<String, String> myHashRender = new HashMap<String, String>();
//			String destFileName = sanitizePath("/sounds/" + mDescription).replaceAll("\\s",""); //Remove all white space on the file name as well
//			//myHashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, mDescription);
//			ttsPlayer.synthesizeToFile(mDescription, null, destFileName);
//			setSoundResourceId(destFileName);
//		}
//	}
	
	//TODO Make sure that there is a sound by default. WHEN IT IS CREATED
	public MediaPlayer getMediaPlayer(Context context){
		if (isExternalSound())
			return MediaPlayer.create(context, Uri.parse(getSoundResourceLocation()));
		else
			return MediaPlayer.create(context, getSoundResourceId());
		//return null;
	}
	
//	@Override
//	public void onInit(int status) {
//		if (status == TextToSpeech.SUCCESS) {
//			int result = ttsPlayer.setLanguage(Locale.US);
//			if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//				//Toast.makeText(this, "Language not supported", Toast.LENGTH_LONG).show();
//				Log.e("TTS", "Language is not supported");
//				//TODO Let the user know they can begin.
//			} 
//		} else {    //TTS is not initialized properly
//			//Toast.makeText(this, "TTS Initilization Failed", Toast.LENGTH_LONG).show();
//			Log.e("TTS", "Initilization Failed");
//		}
//	}
	
//	//@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data)
//	{
//		Log.v(Context.AUDIO_SERVICE, data.toString());
//		if (requestCode == MY_DATA_CHECK_CODE)
//		{
//			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
//			{
//				// success, create the TTS instance
//				ttsPlayer = new TextToSpeech(this, this);
//			}
//			else
//			{
//				// missing data, install it
//				Intent installIntent = new Intent();
//				installIntent.setAction(
//						TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
//				startActivity(installIntent);
//			}
//		}
//	}
	
//	String sanitizePath(String path) {
//	    if (!path.startsWith("/")) {
//	      path = "/" + path;
//	    }
//	    if (!path.contains(".")) {
//	      path += ".wav";
//	    }
//	    return Environment.getExternalStorageDirectory().getAbsolutePath() + path;
//	}
}
