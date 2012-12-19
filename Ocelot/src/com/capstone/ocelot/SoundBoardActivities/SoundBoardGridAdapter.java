package com.capstone.ocelot.SoundBoardActivities;

import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
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
	
	public void setCurrentItem(SoundBoardItem item){
		((SoundBoardActivity)mContext).setCurrentItem(item);
	}
	
	private Drawable getBackground(){
		Drawable bg;
		
		if (getParent().isDeleteMode()){
			bg = getParent().getResources().getDrawable(com.capstone.ocelot.R.drawable.red_border);
		} else {
			bg = getParent().getResources().getDrawable(com.capstone.ocelot.R.drawable.black_border);
		}
		return bg;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(final int position, View convertView, ViewGroup parent) {

		View view;
		if (convertView == null) {  // if it's not recycled, initialize some attributes
			LayoutInflater li = getParent().getLayoutInflater();
			SoundBoardItem viewItem = (SoundBoardItem) getItem(position); 
			
			if(viewItem.hasImage()) {
				view = li.inflate(com.capstone.ocelot.R.layout.gridicon, null);
				ImageView iv = (ImageView)view.findViewById(com.capstone.ocelot.R.id.icon_image);
				TextView tv = (TextView)view.findViewById(com.capstone.ocelot.R.id.icon_text);
				iv.setImageResource(viewItem.getIconResourceId());
				tv.setText(viewItem.getDescription());
				view.setBackgroundDrawable(getBackground());
			} else {
				view = li.inflate(com.capstone.ocelot.R.layout.gridicontext, null);
				TextView tv = (TextView)view.findViewById(com.capstone.ocelot.R.id.icon_text_text);
				tv.setText(viewItem.getDescription());
				view.setBackgroundDrawable(getBackground());
			}
			
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (getParent().isDeleteMode()){
						getParent().removeGridItem(position);
					} else {
						SoundBoardItem touchItem = (SoundBoardItem)getItem(position);
						MediaPlayer mPlayer = touchItem.getMediaPlayer(mContext);
						mPlayer.start();
						getParent().UpdateGrid(); //Organize by the amount played
					}
				}
			});
			
			view.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					SoundBoardItem touchItem = (SoundBoardItem)getItem(position);
//					MediaPlayer mPlayer = touchItem.getMediaPlayer(mContext);
//					mPlayer.start();
					
					getParent().setCurrentItem(touchItem);
					getParent().addSequenceItem();
					return true;
				}
			});
			
			view.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_MOVE){
						SoundBoardItem touchItem = (SoundBoardItem)getItem(position);
						setCurrentItem(touchItem);
						ClipData data = ClipData.newPlainText("", "");
						DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
						v.startDrag(data, shadowBuilder, v, 0);
						return true;
					} else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
						return true;
					} else {
						return false;
					}
				}
			});
		} else {
			view = convertView;
		}
		return view;
	}
}
