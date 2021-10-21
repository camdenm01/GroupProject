package com.example.groupproject;

import android.widget.Button;
import android.widget.TextView;

/**
 * this will store what we'll use to draw the data to the screen
 * right now, it consists of TextViews and Buttons so we can get the logic for the game down
 * later on, we can change these to ImageViews and implement dragging
 * right now, we'll have buttons to select tiles from UserTiles and put them onto Board
 */
public class EngineToUI {
    //variables:
    //score and highScore are the textviews that will show what the variable name suggests
    private TextView score, highScore;
    //this holds what will be drawn onto the tiles that the player can't interact with (top 3 rows)
    private TextView[][] textTiles;
    //this holds the buttons that will hold where the player can place tiles
    private Button[][] playerSpaces;
    //this holds where the tiles will be generated, the button is so the player can select a tile
    private Button[] userTiles;

    /**
     * @param textScore the name of the score textview
     * @param textHS the name of the highscore textview
     * @param nameBoard the beginning of the name for each textview and button in the board
     * @param nameUserTile the beginning of the name for each Button in UserTile
     * this will set
     */
    EngineToUI(String textScore, String textHS, String nameBoard, String nameUserTile)
    {

    }

    /**
     * @param playingBoard the board being played on
     * this will use Board's getTile method and update every Tile in textTiles and playerSpaces
     * the changes will be drawn to the board
     */
    public void drawBoard(Board playingBoard)
    {

    }

    /**
     * @param table the table being drawn
     * this will use UserTiles's getTile method to update all buttons in userTiles
     * the changes will be drawn to the board
     */
    public void drawUserTile(UserTiles table)
    {

    }

    /**
     * @param mTile the tile that should be drawn
     * this should check if mTile is active and if so, it should draw the tile where the player's finger is
     */
    public void drawMovingTile(MovingTile mTile)
    {

    }

    /**
     * @param score the new score
     * this updates the score and, if necessary, the high score
     */
    public void drawScore(int score)
    {

    }
}
