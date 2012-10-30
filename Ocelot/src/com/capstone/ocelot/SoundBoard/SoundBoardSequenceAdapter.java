package com.capstone.ocelot.SoundBoard;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class SoundBoardSequenceAdapter extends BaseAdapter {

		Context mContext;
		ArrayList<SoundBoardItem> mSequenceItems;
		int mGalleryItemBackground;
		boolean isPrepared = false;
		//SoundBoardPlayer currentSound;
		MediaPlayer mPlayer;
		Iterator<SoundBoardItem> seqIter;
		Resources resources;
		
		OnCompletionListener mListener = new OnCompletionListener() {
		    public void onCompletion(MediaPlayer mp) {
		    	LoadNextSound();
		    }
		};

		public SoundBoardSequenceAdapter(Context c, ArrayList<SoundBoardItem> mSequenceItems){
			this.mSequenceItems = mSequenceItems;
			mContext = c;
			resources = mContext.getResources();
		}

		public int getCount() {
			return mSequenceItems.size();
		}
		
		public void addItem(SoundBoardItem item){
			mSequenceItems.add(item);
		}

		public Object getItem(int position) {
			return mSequenceItems.get(position);
		}

		public long getItemIconId(int position) {
			return mSequenceItems.get(position).getIconResourceId();
		}
		
		public long getItemSoundId(int position) {
			return mSequenceItems.get(position).getSoundResourceId();
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			
			ImageView imageView = new ImageView(mContext);
			
			if (convertView == null) {  // if it's not recycled, initialize some attributes
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				imageView.setLayoutParams(new Gallery.LayoutParams(150, 150));
				imageView.setPadding(20, 20, 20, 20);
			} else {
				imageView = (ImageView) convertView;
			}
			
			imageView.setImageResource(mSequenceItems.get(position).getIconResourceId());
			
			
		    imageView.setOnClickListener(new OnClickListener() {  //Play the sound associated with the object.
				public void onClick(View v) {
					
					//Get an iterator for the Sequence Bar
					seqIter = mSequenceItems.iterator();
					LoadNextSound();
				}
			});

			return imageView;
		}
		
		public void LoadNextSound(){			
			if (seqIter.hasNext()){
				SoundBoardItem item = seqIter.next();
				mPlayer = MediaPlayer.create(mContext, getUriForId(item.getSoundResourceId()));
				mPlayer.setOnCompletionListener(mListener);
				mPlayer.start();
				Toast.makeText(mContext, item.getDescription(), Toast.LENGTH_SHORT).show();
			} else {
				mPlayer.release();
			}
		}
		
		public Uri getUriForId(int resId){
			return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
					resources.getResourcePackageName(resId) + '/' +
					resources.getResourceTypeName(resId) + '/' +
					resources.getResourceEntryName(resId) );
			
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
}