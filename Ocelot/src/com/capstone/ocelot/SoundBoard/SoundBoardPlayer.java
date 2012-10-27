package com.capstone.ocelot.SoundBoard;

import java.io.FileDescriptor;
import java.io.IOException;
 
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
 
public class SoundBoardPlayer implements OnCompletionListener{
    MediaPlayer mediaPlayer;
    boolean isPrepared = false;
     
    public SoundBoardPlayer(Context context, Uri uri){
        mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer.setDataSource(context, uri);
            mediaPlayer.prepare();
            isPrepared = true;
            mediaPlayer.setOnCompletionListener(this);
        } catch(Exception ex){
            throw new RuntimeException("Couldn't load music, uh oh!");
        }
    }
    
//    public void ChangeTrack(AssetFileDescriptor assetDescriptor){
//    	mediaPlayer = new MediaPlayer();
//        try{
//            mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(), assetDescriptor.getStartOffset(), assetDescriptor.getLength());
//            mediaPlayer.prepare();
//            isPrepared = true;
//            mediaPlayer.setOnCompletionListener(this);
//        } catch(Exception ex){
//            throw new RuntimeException("Couldn't load music, uh oh!");
//        }
//    }
     
    public SoundBoardPlayer(FileDescriptor fileDescriptor){
        mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer.setDataSource(fileDescriptor);
            mediaPlayer.prepare();
            isPrepared = true;
            mediaPlayer.setOnCompletionListener(this);
        } catch(Exception ex){
            throw new RuntimeException("Couldn't load music, uh oh!");
        }
    }
     
    public void onCompletion(MediaPlayer mediaPlayer) {
        synchronized(this){
            isPrepared = false;
            notify();
        }
    }
 
    public void play() {
        if(mediaPlayer.isPlaying()){
            return;
        }
        try{
            synchronized(this){
                if(!isPrepared){
                    mediaPlayer.prepare();
                }
                mediaPlayer.start();
            }
        } catch(IllegalStateException ex){
            ex.printStackTrace();
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }
 
    public void stop() {
        mediaPlayer.stop();
        synchronized(this){
            isPrepared = false;
        }
    }
     
    public void switchTracks(){
        mediaPlayer.seekTo(0);
        mediaPlayer.pause();
    }
     
    public void pause() {
        mediaPlayer.pause();
    }
 
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }
     
    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }
     
    public void setLooping(boolean isLooping) {
        mediaPlayer.setLooping(isLooping);
    }
 
    public void setVolume(float volumeLeft, float volumeRight) {
        mediaPlayer.setVolume(volumeLeft, volumeRight);
    }
 
    public void dispose() {
        if(mediaPlayer.isPlaying()){
            stop();
        }
        mediaPlayer.release();
    }
}