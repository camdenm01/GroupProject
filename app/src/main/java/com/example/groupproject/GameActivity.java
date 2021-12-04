package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitleBar();
        setContentView(R.layout.activity_game);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //set up game and run
        SharedPreferences sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        String selectedDifficulty = sharedPreferences.getString("difficulty", "");
        EngineToUI runGame = new EngineToUI(this, this.findViewById(R.id.scoreTxt), this.findViewById(R.id.highScoreTxt), "SharedPrefs");
        Thread thread = new Thread(runGame);
        thread.start();
    }

    /**hides the title bar when the user is playing the game**/
    private void hideTitleBar(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST);
    }

    protected void displayGameOver(int currentScore, int highScore){
        Fragment gameOverFragment = new GameOverFragment(currentScore, highScore);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.game_over_fragment_container, gameOverFragment);
        transaction.commit();

    }
}