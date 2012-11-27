package com.capstone.ocelot.SoundBoardActivities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.capstone.ocelot.SoundBoardActivity;

@SuppressWarnings("deprecation")
public class SoundBoardSequenceAdapter extends BaseAdapter {

		Context mContext;

		public SoundBoardSequenceAdapter(Context c){
			mContext = c;
		}

		public int getCount() {
			return ((SoundBoardActivity)mContext).getSequenceSize();
		}

		public Object getItem(int position) {
			return ((SoundBoardActivity)mContext).getSequenceItem(position);
		}
		
		public long getItemId(int position) {
			return 0;
		}

		public long getItemIconId(int position) {
			return ((SoundBoardItem)getItem(position)).getIconResourceId();
		}
		
		public long getItemSoundId(int position) {
			return ((SoundBoardItem)getItem(position)).getSoundResourceId();
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
			
			imageView.setImageResource(((SoundBoardItem)getItem(position)).getIconResourceId());
			
			return imageView;
		}
}