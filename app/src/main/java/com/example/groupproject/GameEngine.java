package com.example.groupproject;

import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * this is the engine that will handle player input and all events in the game
 * right now, it consists of TextViews and Buttons so we can get the logic for the game down
 * later on, we can change these to ImageViews and implement dragging
 * right now, we'll have buttons to select tiles from UserTiles and put them onto Board
 */
public class GameEngine {
    //Game Functionality
    //this holds the score and highscore
    private int score, highScore;
    //this is the board that will be played
    private Board playingBoard;
    //this is the movingTile that will hold the selected tile
    private MovingTile mTile;
    //this is the userTiles that will generate new playerTiles
    private UserTiles table;
    //this designates whether or not the game is over
    private boolean isGameOver;

    //UI Stuff
    //this is the context the ui is done in
    Activity context;
    //score and highScore are the textviews that will show what the variable name suggests
    private TextView scoreTxt, highScoreTxt;
    //this holds what will be drawn onto the tiles that the player can't interact with (top 3 rows)
    private TextView[][] textTiles;
    //this holds the buttons that will hold where the player can place tiles
    private Button[][] playerSpaces;
    //this holds where the tiles will be generated, the button is so the player can select a tile
    private Button[] userTiles;

    /**
     * @param textScore given to uiControl
     * @param textHS given to uiControl
     * @param txtNameBoard the string before the nuber in the ide of each textview
     * @param buttonNameBoard the string before the numbers in the id of each button in board
     * @param nameUserTile the string before the number in the id of each button in user tiles
     * initializes all variables and sets up game
     */
    GameEngine(Activity activity, TextView textScore, TextView textHS, String txtNameBoard, String buttonNameBoard, String nameUserTile)
    {
        score = 0;
        //TO DO: store High Score and get it from a file, 0 is a stub rn
        highScore = 0;
        isGameOver = false;
        //set up custom classes
        playingBoard = new Board();
        mTile = new MovingTile();
        table = new UserTiles();
        //get score and highscore textview
        scoreTxt = textScore;
        highScoreTxt = textHS;
        //set up context
        context = activity;

        //set up the arrays to hold the textViews and buttons
        textTiles = new TextView[5][5];
        playerSpaces = new Button[2][5];
        userTiles = new Button[4];

        //we'll iterate through textTiles and set up all the textValue
        for (int x = 0; x < 5; x++)
        {
            for (int y = 0; y < 5; y++)
            {
                String textID = txtNameBoard + String.valueOf(x + 1) + String.valueOf(y);
                int resourceID = activity.getResources().getIdentifier(textID, "id", activity.getPackageName());
                textTiles[x][y] = (TextView) activity.findViewById(resourceID);
            }
        }

        //we'll iterate through playerSpaces and set up all the Buttons
        for (int x = 0; x < 2; x++)
        {
            for (int y = 0; y < 5; y++)
            {
                String textID = buttonNameBoard + String.valueOf(x + 4) + String.valueOf(y);
                int resourceID = activity.getResources().getIdentifier(textID, "id", activity.getPackageName());
                playerSpaces[x][y] = (Button) activity.findViewById(resourceID);
                //TO DO: set up onClickListener
            }
        }

        //we'll iterate through userTiles and set up all the Buttons
        for (int x = 0; x < 5; x++)
        {
                String textID = nameUserTile + String.valueOf(x);
                int resourceID = activity.getResources().getIdentifier(textID, "id", activity.getPackageName());
                userTiles[x] = (Button) activity.findViewById(resourceID);
                //TO DO: set up onClickListener
        }
    }

    /**
     * this holds the gameLoop that will handle everything that happens in the game
     */
    public void gameLoop()
    {
        //this will hold the time since last frame so we know when we need to update the baord
        long lastFrame = System.currentTimeMillis();
        //while the game isn't over
        while (!isGameOver)
        {
            //if it's time to update the board, we do
            if (System.currentTimeMillis() - lastFrame >= 1000)
            {
                //have the board update itself and check if the game is over
                isGameOver = playingBoard.move();
                //make new tiles
                table.generateTile();
                //update the ui, we'll tell it to update the ui in the ui thread
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        drawBoard();
                        drawUserTile();
                        drawMovingTile();
                        drawScore();
                    }
                });
                //update time since last frame
                lastFrame = System.currentTimeMillis();
            }
        }
    }

    /**
     * checks if previous highscore was beaten and if so, modify it in memory
     */
    public void updateHighScore()
    {

    }

    /**
     * this will use Board's getTile method and update every Tile in textTiles and playerSpaces
     * the changes will be drawn to the board
     */
    public void drawBoard()
    {
        //update textTiles
        for (int x = 0; x < 3; x++)
        {
            for (int y = 0; y < 5; y++)
            {
                switch (playingBoard.getTile(x + 1, y))
                {
                    case -1:
                        textTiles[x][y].setText("[---]");
                        break;
                    case 0:
                        textTiles[x][y].setText("[-X-]");
                        break;
                    case 1:
                        textTiles[x][y].setText("[-1-]");
                        break;
                    case 2:
                        textTiles[x][y].setText("[-2-]");
                        break;
                    case 3:
                        textTiles[x][y].setText("[-3-]");
                        break;
                    case 4:
                        textTiles[x][y].setText("[-4-]");
                        break;
                    case 5:
                        textTiles[x][y].setText("[-5-]");
                        break;
                }
            }
        }

        //update playerSpaces
        for (int x = 0; x < 2; x++)
        {
            for (int y = 0; y < 5; y++)
            {
                switch (playingBoard.getTile(x + 4, y))
                {
                    case -1:
                        playerSpaces[x][y].setText("[---]");
                        break;
                    case 0:
                        playerSpaces[x][y].setText("[-X-]");
                        break;
                    case 1:
                        playerSpaces[x][y].setText("[-1-]");
                        break;
                    case 2:
                        playerSpaces[x][y].setText("[-2-]");
                        break;
                    case 3:
                        playerSpaces[x][y].setText("[-3-]");
                        break;
                    case 4:
                        playerSpaces[x][y].setText("[-4-]");
                        break;
                    case 5:
                        playerSpaces[x][y].setText("[-5-]");
                        break;
                }
            }
        }
    }

    /**
     * this will use UserTiles's getTile method to update all buttons in userTiles
     * the changes will be drawn to the board
     */
    public void drawUserTile()
    {
        //update userTiles text
        for (int x = 0; x < 4; x++)
        {
            switch (table.getTile(x))
            {
                case -1:
                    userTiles[x].setText("[---]");
                    break;
                case 0:
                    userTiles[x].setText("[-X-]");
                    break;
                case 1:
                    userTiles[x].setText("[-1-]");
                    break;
                case 2:
                    userTiles[x].setText("[-2-]");
                    break;
                case 3:
                    userTiles[x].setText("[-3-]");
                    break;
                case 4:
                    userTiles[x].setText("[-4-]");
                    break;
                case 5:
                    userTiles[x].setText("[-5-]");
                    break;
            }
        }
    }

    /**
     * this should check if mTile is active and if so, it should draw the tile where the player's finger is
     */
    public void drawMovingTile()
    {
        //stub rn because there is no dragging
    }

    /**
     * this updates the score and, if necessary, the high score
     */
    public void drawScore()
    {
        scoreTxt.setText("Score: " + String.valueOf(score));
        if (score > highScore)
            highScoreTxt.setText("Highscore: " + String.valueOf(score));
    }
}
