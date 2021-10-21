package com.example.groupproject;

import android.widget.TextView;

/**
 * this is the engine that will handle player input and all events in the game
 */
public class GameEngine {
    //this is the board that will be played
    private Board playingBoard;
    //this handles displaying the current gamestate to the screen
    private EngineToUI uiControl;
    //this is the movingTile that will hold the selected tile
    private MovingTile mTile;
    //this is the userTiles that will generate new playerTiles
    private UserTiles table;

    /**
     * @param textScore given to uiControl
     * @param testHS given to uiControl
     * @param nameBoard given to uiControl
     * @param nameUserTile given to uiControl
     * initializes all variables and sets up game
     */
    GameEngine(String textScore, String testHS, String nameBoard, String nameUserTile)
    {

    }

    /**
     * this holds the gameLoop that will handle everything that happens in the game
     */
    public void gameLoop()
    {

    }

    /**
     * this updates the game state accordingly
     */
    public void update()
    {

    }

    /**
     * checks if previous highscore was beaten, if so store it in memory
     */
    public void updateHighScore()
    {

    }
}
