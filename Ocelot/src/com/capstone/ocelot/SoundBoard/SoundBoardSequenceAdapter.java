package com.capstone.ocelot.SoundBoard;

import java.util.ArrayList;

import com.capstone.ocelot.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

@SuppressWarnings("deprecation")
public class SoundBoardSequenceAdapter extends BaseAdapter {

		Context mContext;
		ArrayList<SoundBoardItem> mSequenceItems;

		public SoundBoardSequenceAdapter(Context c, ArrayList<SoundBoardItem> mSequenceItems){
			this.mSequenceItems = mSequenceItems;
			mContext = c;
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

		public View getView(int position, View convertView, ViewGroup parent) {
			
			//ImageView imageView = new ImageView(mContext);
			
			
//			if (convertView == null) {  // if it's not recycled, initialize some attributes
//				imageView = new ImageView(mContext);
//				imageView.setLayoutParams(new Gallery.LayoutParams(parent.getWidth()/4, parent.getWidth()/4));
//				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//				imageView.setPadding(20, 20, 20, 20);
//			} else {
//				imageView = (ImageView) convertView;
//			}

			
			//return imageView;
			
			View rowView = LayoutInflater
					.from(parent.getContext())
					.inflate(R.layout.row, parent, false);
			
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(150, 150);
			
			ImageView imageView = (ImageView)rowView.findViewById(R.id.seqimage);
			imageView.setImageResource(mSequenceItems.get(position).getIconResourceId());
			imageView.setLayoutParams(layoutParams);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(0, 0, 0, 0);
			
//			TextView listTextView = (TextView)rowView.findViewById(R.id.itemtext);
//			listTextView.setText(mSequenceItems.get(position).getDescription());
			
//			iv.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT));
//			iv.setScaleType(ScaleType.FIT_CENTER);

			return rowView;
		}
}
