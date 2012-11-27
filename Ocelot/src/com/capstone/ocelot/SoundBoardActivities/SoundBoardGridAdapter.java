package com.capstone.ocelot.SoundBoardActivities;

import android.content.ClipData;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
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

		ImageView imageView;
		if (convertView == null) {  // if it's not recycled, initialize some attributes
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageResource(((SoundBoardItem) getItem(position)).getIconResourceId());
		imageView.setOnTouchListener(new OnTouchListener(){

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
		
		return imageView;
	}
}
