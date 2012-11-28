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
import android.widget.GridView;
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
			view = li.inflate(0x7f030000, null);
			TextView tv = (TextView)view.findViewById(0x7f080002); //android.R.id.icon_text
			tv.setText(viewItem.getDescription());
			ImageView iv = (ImageView)view.findViewById(0x7f080001); //android.R.id.icon_image
			iv.setImageResource(viewItem.getIconResourceId());
			
			//view = new ImageView(mContext);
			view.setLayoutParams(new GridView.LayoutParams(150, 150));
			//view.setScaleType(ImageView.ScaleType.CENTER_CROP);
			view.setPadding(8, 8, 8, 8);
			//view.setImageResource(viewItem.getIconResourceId());
		} else {
			view = convertView;
		}

		view.setOnTouchListener(new OnTouchListener(){

			public boolean onTouch(View view, MotionEvent motionEvent) {
				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					SoundBoardItem touchItem = (SoundBoardItem)getItem(position);
					MediaPlayer mp = MediaPlayer.create(mContext, touchItem.getSoundResourceId());
					Toast.makeText(mContext, touchItem.getDescription(), Toast.LENGTH_SHORT).show(); //Show the user the description
					mp.start();
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
