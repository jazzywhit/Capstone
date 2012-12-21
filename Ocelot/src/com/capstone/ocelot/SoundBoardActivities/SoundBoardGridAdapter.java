package com.capstone.ocelot.SoundBoardActivities;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.capstone.ocelot.SoundBoardActivity;

@TargetApi(16)
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
	
	SoundBoardActivity getParent(){
		return ((SoundBoardActivity)mContext);
	}
	
	private Drawable getBackground(){
		Drawable bg;
		if (getParent().isDeleteMode()){
			bg = getParent().getResources().getDrawable(com.capstone.ocelot.R.drawable.editbox_background_focus_yellow);
		} else {
			bg = getParent().getResources().getDrawable(com.capstone.ocelot.R.drawable.editbox_dropdown_background);
		}
		return bg;
	}
	
	private void PlayGridItem(int position){
		if (getParent().isDeleteMode()){
			getParent().removeGridItem(position);
		} else {
			SoundBoardItem touchItem = (SoundBoardItem)getItem(position);
			MediaPlayer mPlayer = touchItem.getMediaPlayer(mContext);
			if(mPlayer.isPlaying()){
				mPlayer.release();
			}
			mPlayer.setOnCompletionListener(new OnCompletionListener() {
				public void onCompletion(MediaPlayer mp) {
					mp.release();
				}
			});
			mPlayer.start();
		}
	}

	// create a new ImageView for each item referenced by the Adapter
	@TargetApi(16)
	public View getView(final int position, View convertView, ViewGroup parent) {

		View view;
		final SoundBoardItem viewItem = (SoundBoardItem) getItem(position);
		
		if (convertView == null) {  // if it's not recycled, initialize some attributes
			LayoutInflater li = getParent().getLayoutInflater();
//			if(viewItem.hasImage()) {
				view = li.inflate(com.capstone.ocelot.R.layout.gridicon, null);
//				ImageView iv = (ImageView)view.findViewById(com.capstone.ocelot.R.id.icon_image);
//				TextView tv = (TextView)view.findViewById(com.capstone.ocelot.R.id.icon_text);
//				if (viewItem.hasImage()){
//					if (viewItem.isExternalImage())
//						iv.setImageURI(viewItem.getIconResourceUri());
//					else
//						iv.setImageResource(viewItem.getIconResourceId());
//				}
//				tv.setText(viewItem.getDescription());
//				view.setBackground(getBackground());
//			} else {
//				view = li.inflate(com.capstone.ocelot.R.layout.gridicontext, null);
//				TextView tv = (TextView)view.findViewById(com.capstone.ocelot.R.id.icon_text_text);
//				tv.setText(viewItem.getDescription());
//				view.setBackground(getBackground());
//			}
			
//			view.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					PlayGridItem(position);
//				}
//			});
//			
//			view.setOnLongClickListener(new OnLongClickListener() {
//				@Override
//				public boolean onLongClick(View v) {
//					PlayGridItem(position);
//					return true;
//				}
//			});
			
//			view.setOnTouchListener(new OnTouchListener() {
//				@Override
//				public boolean onTouch(View v, MotionEvent event) {					
//					if (event.getAction() == MotionEvent.ACTION_MOVE){
//						//Create the Drop Shadow
//						ClipData data = ClipData.newPlainText("", "");
//						DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
//						v.startDrag(data, shadowBuilder, v, 0);
//						
//						//Set the current Item
//						SoundBoardItem touchItem = (SoundBoardItem)getItem(position);
//						getParent().setCurrentItem(touchItem);
//						return true;
//					} else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
//						return true;
//					} else {
//						return false;
//					}
//				}
//			});
		} else {
			view = convertView;
		}
		
//		if (viewItem == null){
//			return view;
//		}
		
		//Set the layouts for the different types of buttons.
//		if(viewItem.hasImage()) {
			ImageView iv = (ImageView)view.findViewById(com.capstone.ocelot.R.id.icon_image);
			if (viewItem.isExternalImage())
				iv.setImageURI(viewItem.getIconResourceUri());
			else
				iv.setImageResource(viewItem.getIconResourceId());
//		}
		
		TextView tv = (TextView)view.findViewById(com.capstone.ocelot.R.id.icon_text);
		tv.setText(viewItem.getDescription());
		view.setBackground(getBackground());
//			if (viewItem.hasImage())	
//				tv.settex
//		} else {
//			TextView tv = (TextView)view.findViewById(com.capstone.ocelot.R.id.icon_text_text);
//			tv.setText(viewItem.getDescription());
//			view.setBackground(getBackground());
//		}
			
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PlayGridItem(position);
			}
		});
//		
		view.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				PlayGridItem(position);
				return true;
			}
		});
		
		view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {					
				if (event.getAction() == MotionEvent.ACTION_MOVE){
					//Create the Drop Shadow
					ClipData data = ClipData.newPlainText("", "");
					DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
					v.startDrag(data, shadowBuilder, v, 0);
					
					//Set the current Item
					SoundBoardItem touchItem = (SoundBoardItem)getItem(position);
					getParent().setCurrentItem(touchItem);
					return true;
				} else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
					return true;
				} else {
					return false;
				}
			}
		});
		
		return view;
	}
}
