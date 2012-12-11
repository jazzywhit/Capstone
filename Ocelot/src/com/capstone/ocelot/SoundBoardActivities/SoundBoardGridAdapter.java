package com.capstone.ocelot.SoundBoardActivities;

import android.content.ClipData;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.ocelot.SoundBoardActivity;

public class SoundBoardGridAdapter extends BaseAdapter {
	private Context mContext;

	public SoundBoardGridAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		return ((SoundBoardActivity)mContext).getGridSize();
	}

	public Object getItem(int position) {
		return ((SoundBoardActivity)mContext).getGridItem(position);
	}

	public long getItemId(int position) {
		return 0;
	}
	
	public void setCurrentItem(SoundBoardItem item){
		((SoundBoardActivity)mContext).setCurrentItem(item);
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(final int position, View convertView, ViewGroup parent) {

		View view;
		if (convertView == null) {  // if it's not recycled, initialize some attributes
			LayoutInflater li = ((SoundBoardActivity)mContext).getLayoutInflater();
			SoundBoardItem viewItem = (SoundBoardItem) getItem(position); 
			
			if(viewItem.getHasImage()) {
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

		view.setOnTouchListener(new OnTouchListener(){

			public boolean onTouch(View view, MotionEvent motionEvent) {
				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					SoundBoardItem touchItem = (SoundBoardItem)getItem(position);
					
					//If no sound present, just show the text
					if (touchItem.getHasSound()){
						MediaPlayer mp = MediaPlayer.create(mContext, touchItem.getSoundResourceId());
						mp.start();
					} else {
						Toast.makeText(mContext, touchItem.getDescription(), Toast.LENGTH_SHORT).show(); //Show the user the description
					}
					
					return true;
				} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE){
					SoundBoardItem touchItem = (SoundBoardItem)getItem(position);
					setCurrentItem(touchItem);
					ClipData data = ClipData.newPlainText("", "");
					DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
					view.startDrag(data, shadowBuilder, view, 0);
					return true;
				} else if (motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
					return true;
				} else {
					return false;
				}
			}
		});
		return view;
	}
}
