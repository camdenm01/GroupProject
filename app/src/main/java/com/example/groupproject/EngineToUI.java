package com.example.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * this class will just set up the gameEngine and run it in a thread
 */
public class EngineToUI implements Runnable
{
    //this is the game that we'll run
    GameEngine game;

    EngineToUI(Activity inputAct, TextView score, TextView highScore)
    {
        //set up the game
        game = new GameEngine(inputAct, score, highScore, "txtTile", "tileButton", "tileButton");
    }

    @Override
    public void run() {
        //start the game loop
        game.gameLoop();
    }
}
