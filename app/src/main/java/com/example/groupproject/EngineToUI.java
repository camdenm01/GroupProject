package com.example.groupproject;

import static android.content.Context.MODE_PRIVATE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;;
import android.content.SharedPreferences;

/**
 * this class will just set up the gameEngine and run it in a thread
 */
public class EngineToUI implements Runnable
{
    //this is the game that we'll run
    GameEngine game;

    EngineToUI(Activity inputAct, TextView score, TextView highScore, String storedDataLocation)
    {
        //set up the game
        game = new GameEngine(inputAct, score, highScore, "tileImage", "tileIButton", "tilePButton", storedDataLocation);
    }

    @Override
    public void run() {
        //start the game loop
        game.gameLoop();
    }
}
