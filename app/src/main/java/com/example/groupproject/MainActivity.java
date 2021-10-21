package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        Button startGameButton = (Button) findViewById(R.id.game_start_button);
        Button settingsButton = (Button) findViewById(R.id.settings_button);
        Button creditsButton = (Button) findViewById(R.id.credits_button);

        startGameButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, GameActivity.class)));

        settingsButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SettingsActivity.class)));

        creditsButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, CreditsActivity.class)));
    }
}
