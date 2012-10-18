package com.capstone.ocelot;

import java.util.ArrayList;

import android.content.ClipData;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<SoundBoardItem> mSoundBoardItems = null;

	public ImageAdapter(Context c, ArrayList<SoundBoardItem> mSoundBoardItems) {
		mContext = c;

		//This will need to be extended so that we can load from a database!
		//create a simple list
		this.mSoundBoardItems = mSoundBoardItems;

		//        SoundBoardItem s = new SoundBoardItem();
		//		s.setDescription("Cougar");
		//		s.setIconResourceId(R.drawable.cougar);
		//		s.setSoundResourceId(R.raw.cougar);
		//		mSoundBoardItems.add(s);
		//		
		//		s = new SoundBoardItem();
		//		s.setDescription("Chicken");
		//		s.setIconResourceId(R.drawable.chicken);
		//		s.setSoundResourceId(R.raw.chicken);
		//		mSoundBoardItems.add(s);
		//		
		//		s = new SoundBoardItem();
		//		s.setDescription("Dog");
		//		s.setIconResourceId(R.drawable.dog);
		//		s.setSoundResourceId(R.raw.dog);
		//		mSoundBoardItems.add(s);
		//		
		//		s = new SoundBoardItem();
		//		s.setDescription("Elephant");
		//		s.setIconResourceId(R.drawable.elephant);
		//		s.setSoundResourceId(R.raw.elephant);
		//		mSoundBoardItems.add(s);
	}

	public int getCount() {
		return mSoundBoardItems.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(final int position, View convertView, ViewGroup parent) {

		ImageView imageView;
		if (convertView == null) {  // if it's not recycled, initialize some attributes
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageResource(mSoundBoardItems.get(position).getIconResourceId());
		imageView.setOnTouchListener(new OnTouchListener(){

			public boolean onTouch(View view, MotionEvent motionEvent) {
				Log.v("log_tag", motionEvent.toString());
				Log.v("log_tag", view.toString());

				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					MediaPlayer mp = MediaPlayer.create(mContext, mSoundBoardItems.get(position).getSoundResourceId());
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

		//	    imageView.setOnClickListener(new OnClickListener() {  //Play the sound associated with the object.
		//			public void onClick(View v) {
		//				MediaPlayer mp = MediaPlayer.create(mContext, mSoundBoardItems.get(position).getSoundResourceId());
		//	        	mp.start();
		//			}
		//		});
		return imageView;
	}

	//Touch Listener to interact with the SoundBoardItems
	//	private class MyTouchListener implements OnTouchListener {
	//	    public boolean onTouch(View view, MotionEvent motionEvent) {
	//	      if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
	//	        ClipData data = ClipData.newPlainText("", "");
	//	        DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
	//	        view.startDrag(data, shadowBuilder, view, 0);
	//	        //view.setVisibility(View.INVISIBLE);
	//	        return true;
	//	      } else {
	//	        return false;
	//	      }
	//	    }
	//	}

	//	private class MyTouchListener implements OnTouchListener {
	//	    public boolean onTouch(View v, MotionEvent event) {
	//	        // TODO Auto-generated method stub
	//	
	//	        if(event.getAction() == MotionEvent.ACTION_DOWN)
	//	        {
	//	            v.setBackgroundResource(R.drawable.chicken); //Will need to create a drop shadow for this.
	//	            Log.v("log_tag", "MotionEvent.ACTION_DOWN");
	//	            MediaPlayer mp = MediaPlayer.create(mContext, mSoundBoardItems.get(position).getSoundResourceId());
	//	            mp.start();
	//	            return true;
	//	        }
	//	        else if(event.getAction()==MotionEvent.ACTION_UP)
	//	        {
	//	
	//	            //v.setBackgroundDrawable(null); 
	//	            Log.v("log_tag", "MotionEvent.ACTION_UP");
	//	            return true; 
	//	        }
	//	        return false;
	//	    }
	//	}

	// create a new ImageView for each item referenced by the Adapter
	//    public View getView(int position, View convertView, ViewGroup parent) {
	//        ImageView imageView;
	//        if (convertView == null) {  // if it's not recycled, initialize some attributes
	//            imageView = new ImageView(mContext);
	//            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
	//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	//            imageView.setPadding(8, 8, 8, 8);
	//        } else {
	//            imageView = (ImageView) convertView;
	//        }
	//
	//        imageView.setImageResource(mSoundBoardItems.get([position));
	//        imageView.setOnTouchListener(new OnTouchListener() {
	//
	//            @Override
	//            public boolean onTouch(View v, MotionEvent event) {
	//                // TODO Auto-generated method stub
	//
	//                if(event.getAction() == MotionEvent.ACTION_DOWN)
	//                {
	//                    v.setBackgroundResource(R.drawable.sample_0);
	//                    Log.v("log_tag", "MotionEvent.ACTION_DOWN");
	//                    return true;
	//                }
	//                else if(event.getAction()==MotionEvent.ACTION_UP)
	//                {
	//                    Log.v("log_tag", "MotionEvent.ACTION_UP");
	//                    return true; 
	//                }
	//                return false;
	//            }
	//        });
	//        return imageView;
	//    }
}
