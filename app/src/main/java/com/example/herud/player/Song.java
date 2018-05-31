package com.example.herud.player;

import android.net.Uri;

/**
 * Created by Herud on 2018-05-15.
 */

public class Song
{
    private Integer songId;
    private String title;
    private String artist;
    private Uri mediaPath;
    public Song(Integer id, String title, String artist,Uri mp)
    {
        this.songId=id;
        this.title=title;
        this.artist=artist;
        this.mediaPath=mp;
    }


    public Integer getSongId() {
        return songId;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public Uri getMediaPath(){return mediaPath;}
}
