package com.example.herud.player;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;
import android.content.Intent;

public class Settings extends AppCompatActivity {

    private RatingBar ratingBar;
    private RadioGroup radioGroup;
    private Button saveButton;
    private Switch loopSwitch;
    private SeekBar seekBar;
    private final int MAX_VOLUME=100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ratingBar=findViewById(R.id.ratingBar);

        radioGroup=findViewById(R.id.radioGroup);

        saveButton=findViewById(R.id.saveButton);

        loopSwitch=findViewById(R.id.loop);
        seekBar=findViewById(R.id.seekBar2);

        final Intent returnIntent = new Intent();

        seekBar.setMax(99);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    float volume =(float) (1 - (Math.log(MAX_VOLUME - progress) / Math.log(MAX_VOLUME)));
                    returnIntent.putExtra("volumeKey", volume);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        loopSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                returnIntent.putExtra("loopKey",b);
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup,int itemId)
            {
                if(itemId==R.id.red)
                {
                    //Toast.makeText(getApplicationContext(),"red",Toast.LENGTH_SHORT).show();
                    returnIntent.putExtra("color",R.color.red);
                }
                else
                {
                    if(itemId==R.id.blue)
                    {
                        returnIntent.putExtra("color",R.color.blue);
                        //Toast.makeText(getApplicationContext(),"blue",Toast.LENGTH_SHORT).show();
                    }
                    else
                    if(itemId==R.id.dark)
                    {
                        returnIntent.putExtra("color",R.color.colorPrimary);
                        //Toast.makeText(getApplicationContext(),"dark blue",Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                    if(rating<2)
                        Toast.makeText(getApplicationContext(),"Next version is going to be better",Toast.LENGTH_SHORT).show();
                    else
                    if(rating<4.5)
                        Toast.makeText(getApplicationContext(),"I appreciate the feedback",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(),"Thank you very much",Toast.LENGTH_SHORT).show();



                }
            });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });


        }
}
