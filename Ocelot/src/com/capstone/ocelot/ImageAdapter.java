package com.capstone.ocelot;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        	
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
        return imageView;
    }
}
