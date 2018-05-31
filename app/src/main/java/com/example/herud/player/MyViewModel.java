package com.example.herud.player;

import android.arch.lifecycle.ViewModel;

/**
 * Created by Herud on 2018-05-22.
 */

public class MyViewModel extends ViewModel
{
    private Integer songId;
    private Integer songPos;
    private Integer songDuration;

    public Integer getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(Integer songDuration) {
        this.songDuration = songDuration;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public void setSongPos(Integer songPos) {
        this.songPos = songPos;
    }

    public Integer getSongId() {

        return songId;
    }

    public Integer getSongPos() {
        return songPos;
    }
}
