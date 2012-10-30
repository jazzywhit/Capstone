package com.capstone.ocelot.SoundBoard;

import java.util.ArrayList;

import android.content.ClipData;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class SoundBoardGridAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<SoundBoardItem> mSoundBoardItems = null;
	public SoundBoardItem currentItem;

	public SoundBoardGridAdapter(Context c, ArrayList<SoundBoardItem> mSoundBoardItems) {
		mContext = c;
		this.mSoundBoardItems = mSoundBoardItems;
	}

	public int getCount() {
		return mSoundBoardItems.size();
	}

	public Object getItem(int position) {
		return mSoundBoardItems.get(position);
	}

	public long getItemId(int position) {
		return 0;
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

		imageView.setImageResource(mSoundBoardItems.get(position).getIconResourceId());
		imageView.setOnTouchListener(new OnTouchListener(){

			public boolean onTouch(View view, MotionEvent motionEvent) {
//				Log.v("log_tag", motionEvent.toString());
//				Log.v("log_tag", view.toString());

				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					currentItem = mSoundBoardItems.get(position); //Set the accessible item
					MediaPlayer mp = MediaPlayer.create(mContext, mSoundBoardItems.get(position).getSoundResourceId());
					Toast.makeText(mContext, mSoundBoardItems.get(position).getDescription(), Toast.LENGTH_SHORT).show();
					mp.start();
					return true;
				} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE){
					view.setVisibility(View.INVISIBLE);
					ClipData data = ClipData.newPlainText("", "");
					DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
					view.startDrag(data, shadowBuilder, view, 0);
					return true;
				} else if (motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
					view.setVisibility(View.VISIBLE);
					return true;
				} else {
					return false;
				}
			}
		});
		imageView.setOnDragListener(new OnDragListener(){
			public boolean onDrag(View view, DragEvent dragEvent) {
				Log.v("log_tag", dragEvent.toString());
				if (dragEvent.getAction() == DragEvent.ACTION_DRAG_STARTED){
					//view.setVisibility(View.INVISIBLE);
					return true;
				} else if (dragEvent.getAction() == DragEvent.ACTION_DRAG_ENDED){
					//view.setVisibility(View.VISIBLE);
					return true;
				}
				return false;
			}
		});
		return imageView;
	}
}
