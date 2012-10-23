package com.capstone.ocelot.SoundBoard;

import java.util.ArrayList;
import java.util.Iterator;

import com.capstone.ocelot.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

@SuppressWarnings("deprecation")
public class SoundBoardSequenceAdapter extends BaseAdapter {

		Context mContext;
		ArrayList<SoundBoardItem> mSequenceItems;
		int mGalleryItemBackground;

		public SoundBoardSequenceAdapter(Context c, ArrayList<SoundBoardItem> mSequenceItems){
			this.mSequenceItems = mSequenceItems;
			mContext = c;
			//TypedArray a = c.obtainStyledAttributes(R.styleable.Gallery1);
	        //mGalleryItemBackground = a.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 0);
	        //a.recycle();
//			mGalleryItemBackground = a.getResourceId(
//			         R.styleable.Theme_android_galleryItemBackground,
//			                   0);
//			a.recycle();
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return mSequenceItems.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mSequenceItems.get(position).getIconResourceId();
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
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
					final Iterator<SoundBoardItem> itemIter = mSequenceItems.iterator();
					SoundBoardItem nextItem;
					MediaPlayer mPlayer;
					
					while(itemIter.hasNext()){
						nextItem = itemIter.next();
						mPlayer = MediaPlayer.create(mContext, nextItem.getSoundResourceId());
						mPlayer.setOnCompletionListener(new OnCompletionListener(){
							public void onCompletion(MediaPlayer mp) {
							}
						});
						while(mPlayer.isPlaying()){ try {
							wait(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} }
						mPlayer.start();
					}
				}
			});
			//return imageView;
			
//			View rowView = LayoutInflater
//					.from(parent.getContext())
//					.inflate(R.layout.row, parent, false);
//			
//			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(150, 150);
			
//			ImageView imageView = (ImageView)rowView.findViewById(R.id.seqimage);
//			imageView.setImageResource(mSequenceItems.get(position).getIconResourceId());
//			imageView.setLayoutParams(layoutParams);
//			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//			imageView.setPadding(0, 0, 0, 0);
			
//			TextView listTextView = (TextView)rowView.findViewById(R.id.itemtext);
//			listTextView.setText(mSequenceItems.get(position).getDescription());
			
//			iv.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT));
//			iv.setScaleType(ScaleType.FIT_CENTER);

			return imageView;
		}
}
