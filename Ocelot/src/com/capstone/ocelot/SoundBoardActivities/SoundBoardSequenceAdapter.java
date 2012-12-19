package com.capstone.ocelot.SoundBoardActivities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.capstone.ocelot.SoundBoardActivity;

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
			
			View view;
			if (convertView == null) {  // if it's not recycled, initialize some attributes
				LayoutInflater li = ((SoundBoardActivity)mContext).getLayoutInflater();
				SoundBoardItem viewItem = (SoundBoardItem) getItem(position); 
				
				if(viewItem.hasImage()) {
					view = li.inflate(com.capstone.ocelot.R.layout.gridicon, null);
					
					ImageView iv = (ImageView)view.findViewById(com.capstone.ocelot.R.id.icon_image);
					TextView tv = (TextView)view.findViewById(com.capstone.ocelot.R.id.icon_text);
					
					iv.setImageResource(viewItem.getIconResourceId());
					tv.setText(viewItem.getDescription());
				} else {
					view = li.inflate(com.capstone.ocelot.R.layout.gridicontext, null);
					TextView tv = (TextView)view.findViewById(com.capstone.ocelot.R.id.icon_text_text);
					
					tv.setText(viewItem.getDescription());
				}
			} else {
				view = convertView;
			}
			
			return view;
		}
}