package com.example.herud.player;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public  static MediaPlayer mainPlayer;
    private MyArrayAdapter adapter;
    private ArrayList<Song> arrL;
    private ImageButton pause;
    private ImageButton forward;
    private ImageButton backward;
    private ConstraintLayout constraintLayout;
    private Integer color;
    public Intent playerService;
    private Toolbar toolbar;
    private ListView lv;
    public float currVolume=-1;
    public static String currTitle="";
    private final static int MAX_VOLUME = 100;

    public SeekBar seekBar;
    public Handler mSeekbarUpdateHandler=new Handler();
    public Runnable mUpdateSeekbar = new Runnable() {
        @Override
        public void run() {
            seekBar.setProgress(mainPlayer.getCurrentPosition());
            mSeekbarUpdateHandler.postDelayed(this, 50);
        }
    };



    void fillArrL() {
        arrL = new ArrayList<>();


        MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();

        int resourceID;
        Field[] fields = R.raw.class.getFields();
        for (int count = 0; count < fields.length; count++) {
            Log.i("Raw Asset: ", fields[count].getName());
            try {
                resourceID = fields[count].getInt(fields[count]);
                Uri mediaPath = Uri.parse("android.resource://" + this.getPackageName() + "/" + resourceID);
                metaRetriver.setDataSource(this, mediaPath);

                String artist = metaRetriver
                        .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                String title = metaRetriver
                        .extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

                arrL.add(new Song(resourceID, title, artist,mediaPath));


            } catch (IllegalAccessException e) {
                //e.printStackTrace();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        constraintLayout=findViewById(R.id.conLayout);
        Toolbar toolbar=findViewById(R.id.toolbar);
        if (requestCode == 0) {
            if(resultCode == Activity.RESULT_OK){
                int color=data.getIntExtra("color",-1);
                if(color!=-1)
                {
                    this.color=color;
                    constraintLayout.setBackgroundColor(getResources().getColor(color));
                    toolbar.setBackgroundColor(getResources().getColor(color+10));
                }
                boolean looping=data.getBooleanExtra("loopKey",false);
                mainPlayer.setLooping(looping);
                float volume=data.getFloatExtra("volumeKey",-1);
                if(volume!=-1)
                {
                    //Toast.makeText(this,Float.toString(volume),Toast.LENGTH_SHORT).show();
                    //float newVolume = (float) (1 - (Math.log(MAX_VOLUME - volume) / Math.log(MAX_VOLUME)));
                    mainPlayer.setVolume(volume, volume);
                    currVolume=volume;
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
                Intent i2= new Intent(MainActivity.this,Settings.class);

                startActivityForResult(i2,0);

                break;
            case R.id.about:
                //Toast.makeText(this, "about selected", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(MainActivity.this,About.class);
                startActivity(i);
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);

        return true;
    }


    @Override
    protected void onDestroy()
    {

        //mainPlayer.stop();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Make sure to call the super method so that the states of our views are saved
        super.onSaveInstanceState(outState);
        // Save our own state now
        outState.putInt("keyPos", mainPlayer.getCurrentPosition());
        outState.putBoolean("keyPlaying",mainPlayer.isPlaying());
        outState.putBoolean("isLooping",mainPlayer.isLooping());
        if(color!=null)
            outState.putInt("color",color );
        if(currVolume!=-1)
            outState.putFloat("volume",currVolume);

    }

    View.OnClickListener forwardListener()
    {
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mainPlayer.getCurrentPosition()+10000<=mainPlayer.getDuration())
                    mainPlayer.seekTo(mainPlayer.getCurrentPosition()+10000);
            }
        };
        return listener;
    }
    View.OnClickListener backwardListener()
    {
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mainPlayer.getCurrentPosition()-10000>=0)
                    mainPlayer.seekTo(mainPlayer.getCurrentPosition()-10000);
            }
        };
        return listener;
    }
    View.OnClickListener pauseListener()
    {
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mainPlayer.isPlaying())
                {
                    mainPlayer.pause();
                    mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);

                }
                else
                {
                    mainPlayer.start();
                    mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);

                }
            }
        };
        return listener;
    }
    SeekBar.OnSeekBarChangeListener seekbarListener()
    {
        SeekBar.OnSeekBarChangeListener l=new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    mainPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        return l;
    }

    void initFields()
    {
        constraintLayout=findViewById(R.id.conLayout);



        pause=findViewById(R.id.pause);
        forward=findViewById(R.id.forward);
        backward=findViewById(R.id.backward);
        seekBar=findViewById(R.id.seekBar);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        forward.setOnClickListener(forwardListener());
        backward.setOnClickListener(backwardListener());
        lv = (ListView)findViewById(R.id.listView);
        playerService = new Intent(this, MyService.class);
        startService(playerService);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        fillArrL();
        initFields();



        adapter=new MyArrayAdapter(this, arrL);
        lv.setAdapter(adapter);

        if(savedInstanceState!=null)
        {
            if(currVolume!=-1)
                mainPlayer.setVolume(currVolume,currVolume);
            seekBar.setMax(mainPlayer.getDuration());
            mainPlayer.setLooping(savedInstanceState.getBoolean("isLooping"));
            if(savedInstanceState.getInt("color",0)!=0) {
                color = savedInstanceState.getInt("color", 0);
                constraintLayout.setBackgroundColor(getResources().getColor(color));
                toolbar.setBackgroundColor(getResources().getColor(color+10));
            }
        }else {
            if(mainPlayer==null)
            {
                mainPlayer = MediaPlayer.create(this, arrL.get(0).getSongId());
                //mainPlayer.start();
                currTitle=arrL.get(0).getTitle();
            }
            seekBar.setMax(mainPlayer.getDuration());
        }

        mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
        seekBar.setOnSeekBarChangeListener(seekbarListener());
        pause.setOnClickListener(pauseListener());


        mainPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(getApplicationContext(),"wtf",Toast.LENGTH_SHORT).show();
                mediaPlayer.reset();
                mediaPlayer = MediaPlayer.create(getApplicationContext(), arrL.get(0).getSongId());
                mediaPlayer.start();
            }
        });


    }
}
