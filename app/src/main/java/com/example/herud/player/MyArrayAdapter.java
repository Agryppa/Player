package com.example.herud.player;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.herud.player.MainActivity.mainPlayer;


/**
 * Created by Herud on 2018-05-15.
 */

public class MyArrayAdapter extends ArrayAdapter {
    private ArrayList<Song> songs;
    private MainActivity mainActivity;

    public MyArrayAdapter(Context context, ArrayList<Song> songs) {

        super(context, 0, songs);
        this.songs=songs;
        this.mainActivity=(MainActivity)context;
    }


    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int pos) {
        return songs.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Song song = (Song)getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_element, parent, false);
        }

        TextView titleTv =  convertView.findViewById(R.id.songTitle);
        TextView artistTv =  convertView.findViewById(R.id.artist);
        final ImageButton play = convertView.findViewById(R.id.playButton) ;

        play.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mainPlayer.stop();
                mainPlayer.release();
                mainPlayer=MediaPlayer.create(getContext(),song.getSongId());
                if(mainActivity.currVolume!=-1)
                    mainPlayer.setVolume(mainActivity.currVolume,mainActivity.currVolume);
                mainPlayer.start();
                mainActivity.seekBar.setMax(mainPlayer.getDuration());
                mainActivity.currTitle=song.getTitle();
                mainActivity.mSeekbarUpdateHandler.postDelayed(mainActivity.mUpdateSeekbar, 0);

                mainActivity.stopService(mainActivity.playerService);
                mainActivity.startService(mainActivity.playerService);

            }

        });



        titleTv.setText(song.getTitle());
        artistTv.setText(song.getArtist());





        return convertView;
    }




}