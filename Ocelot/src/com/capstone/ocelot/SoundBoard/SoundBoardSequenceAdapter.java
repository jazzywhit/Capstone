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
		        //mp.release();
		    	LoadNextSound();
		    }
		};

		public SoundBoardSequenceAdapter(Context c, ArrayList<SoundBoardItem> mSequenceItems){
			this.mSequenceItems = mSequenceItems;
			mContext = c;
			resources = mContext.getResources();
			
			//TypedArray a = c.obtainStyledAttributes(R.styleable.Gallery1);
	        //mGalleryItemBackground = a.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 0);
	        //a.recycle();
//			mGalleryItemBackground = a.getResourceId(
//			         R.styleable.Theme_android_galleryItemBackground,
//			                   0);
//			a.recycle();
		}

		public int getCount() {
			return mSequenceItems.size();
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
			
			
//			if (convertView == null) {  // if it's not recycled, initialize some attributes
//				imageView = new ImageView(mContext);
//				imageView.setLayoutParams(new ScrollView.LayoutParams(parent.getWidth()/4, parent.getWidth()/4));
//				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//				imageView.setPadding(20, 20, 20, 20);
//			} else {
//				imageView = (ImageView) convertView;
//			}
			
//			i.setImageBitmap(BitmapFactory.decodeFile(mImageIds[position]));
//	        i.setScaleType(ImageView.ScaleType.FIT_XY);
//	        i.setBackgroundResource(mGalleryItemBackground);

			//ScrollView.LayoutParams layoutParams = new ScrollView.LayoutParams(150, 150);
			
			if (convertView == null) {  // if it's not recycled, initialize some attributes
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				imageView.setLayoutParams(new Gallery.LayoutParams(150, 150));
				imageView.setPadding(20, 20, 20, 20);
				//imageView.setLayoutParams(new ScrollView.LayoutParams(parent.getWidth()/4, parent.getWidth()/4));
				//imageView.setLayoutParams(layoutParams);
				//imageView.setBackgroundResource(mGalleryItemBackground)
			} else {
				imageView = (ImageView) convertView;
			}
			
			imageView.setImageResource(mSequenceItems.get(position).getIconResourceId());
			
			
		    imageView.setOnClickListener(new OnClickListener() {  //Play the sound associated with the object.
				public void onClick(View v) {
					
					//Get an iterator for the Sequence Bar
					seqIter = mSequenceItems.iterator();
					
					//Check if the player is already being used. If it is stop it and release it.
					if (mPlayer != null){
				        if(mPlayer.isPlaying()){
				            mPlayer.stop();
				        }
				        mPlayer.release();
					}
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
			}
		}
		
		
//		public void LoadNextSound(){
//			if (currentSound != null){
//				currentSound.dispose();
//			}
//			SoundBoardItem item = itemIter.next();
//			currentSound = new SoundBoardPlayer(mContext, getUriForId(item.getSoundResourceId()));
//			Toast.makeText(mContext, "Loaded: " + item.getDescription(), Toast.LENGTH_SHORT).show();
//	
//		}
		
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


//return imageView;

//View rowView = LayoutInflater
//		.from(parent.getContext())
//		.inflate(R.layout.row, parent, false);
//
//LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(150, 150);

//ImageView imageView = (ImageView)rowView.findViewById(R.id.seqimage);
//imageView.setImageResource(mSequenceItems.get(position).getIconResourceId());
//imageView.setLayoutParams(layoutParams);
//imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//imageView.setPadding(0, 0, 0, 0);

//TextView listTextView = (TextView)rowView.findViewById(R.id.itemtext);
//listTextView.setText(mSequenceItems.get(position).getDescription());

//iv.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT));
//iv.setScaleType(ScaleType.FIT_CENTER);