package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button easyButton = findViewById(R.id.button_easy);
        Button mediumButton = findViewById(R.id.button_medium);
        Button hardButton = findViewById(R.id.button_hard);
        SeekBar volumeSlider = findViewById(R.id.volume_slider);
        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        SharedPreferences sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String selectedDifficulty = sharedPreferences.getString("difficulty", "");

        //Retrieve the last selected difficulty and highlight the appropriate button
        if(selectedDifficulty.equals("easy")) {
            easyButton.setBackgroundColor(Color.parseColor("#000000"));
        }
        if(selectedDifficulty.equals("medium")){
            mediumButton.setBackgroundColor(Color.parseColor("#000000"));
        }
        if(selectedDifficulty.equals("hard")){
            hardButton.setBackgroundColor(Color.parseColor("#000000"));
        }

        //initialize the volume slider and allow it to change system volume
        volumeSlider.setMax(maxVolume);
        volumeSlider.setProgress(curVolume);
        volumeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyButton.setBackgroundColor(Color.parseColor("#000000"));
                mediumButton.setBackgroundColor(Color.parseColor("#1532F3"));
                hardButton.setBackgroundColor(Color.parseColor("#1532F3"));

                editor.putString("difficulty", "easy");
                editor.apply();
            }
        });

        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyButton.setBackgroundColor(Color.parseColor("#1532F3"));
                mediumButton.setBackgroundColor(Color.parseColor("#000000"));
                hardButton.setBackgroundColor(Color.parseColor("#1532F3"));

                editor.putString("difficulty", "medium");
                editor.apply();
            }
        });

        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyButton.setBackgroundColor(Color.parseColor("#1532F3"));
                mediumButton.setBackgroundColor(Color.parseColor("#1532F3"));
                hardButton.setBackgroundColor(Color.parseColor("#000000"));

                editor.putString("difficulty", "hard");
                editor.apply();
            }
        });


    }


}