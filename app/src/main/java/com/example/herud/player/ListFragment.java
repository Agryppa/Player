package com.example.herud.player;

import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * Created by Herud on 2018-05-15.
 */


public class ListFragment extends Fragment
{
   /* private MyArrayAdapter adapter;
    private ArrayList<Song> arrL;


    void fillArrL()
    {
        arrL=new ArrayList<>();


        MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();

        int resourceID=0;
        Field[] fields=R.raw.class.getFields();
        for(int count=0; count < fields.length; count++){
            Log.i("Raw Asset: ", fields[count].getName());
            try {
                resourceID=fields[count].getInt(fields[count]);
                Uri mediaPath = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + resourceID);
                metaRetriver.setDataSource(getActivity(),mediaPath);

                String artist = metaRetriver
                        .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                String title=metaRetriver
                        .extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

                arrL.add(new Song(resourceID,title,artist));


            } catch (IllegalAccessException e) {
                //e.printStackTrace();
            }
        }







    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater
                .inflate(R.layout.list_layout, container, false);
        ListView lv = (ListView)view.findViewById(R.id.listViewEl);

        //Integer position= getActivity().getIntent().getIntExtra(FragmentParentActivity.intentKey,0);
        fillArrL();
        adapter=new MyArrayAdapter(this.getActivity(), arrL);
        lv.setAdapter(adapter);



        return view;
    }

*/
}
