package com.example.groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    static boolean active; //used to check if this activity is active
    private EngineToUI runGame;
    private Thread gameThread;

    /**
     * private GameEngine gameEngine;
     **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitleBar();
        setContentView(R.layout.activity_game);
        ImageView backgroundView = (ImageView) findViewById(R.id.background_image);

        SharedPreferences sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        String selectedDifficulty = sharedPreferences.getString("difficulty", "");

        if (selectedDifficulty == "easy") {
            backgroundView.setImageResource(R.drawable.background_easy);

        } else if (selectedDifficulty == "medium") {
            backgroundView.setImageResource(R.drawable.background_medium);

        } else if (selectedDifficulty == "hard") {
            backgroundView.setImageResource(R.drawable.background_hard);

        }

        runGame = new EngineToUI(this, this.findViewById(R.id.scoreTxt), this.findViewById(R.id.highScoreTxt), "SharedPrefs");

        if (savedInstanceState != null && active) {
            Log.v("onCreate: ", "saved instance happening");
            runGame.getGameState(savedInstanceState);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //set this activity to be active
        active = true;
        //set up game and run
        SharedPreferences sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        String selectedDifficulty = sharedPreferences.getString("difficulty", "");
        gameThread = new Thread(runGame);
        gameThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        runGame.engineMusicStop();
        //active = false; //this activity should no longer be active

        runGame.endGame();
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        runGame.endGame();
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        runGame.saveGameState(outState);
    }

    /**
     * hides the title bar when the user is playing the game
     **/
    private void hideTitleBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST);
    }

    public void isNotActive()
    {
        active = false;
    }

    protected void displayGameOver(int currentScore, int highScore){
        //display the game over fragment only if the activity is still active/open
        if(active) {
            /**gameEngine.updateHighScore();**/
            Fragment gameOverFragment = new GameOverFragment(currentScore, highScore);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.game_over_fragment_container, gameOverFragment);
            transaction.commit();
        }
    }
}