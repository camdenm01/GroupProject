package com.example.groupproject;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * this is the engine that will handle player input and all events in the game
 * right now, it consists of TextViews and Buttons so we can get the logic for the game down
 * later on, we can change these to ImageViews and implement dragging
 * right now, we'll have buttons to select tiles from UserTiles and put them onto Board
 */
public class GameEngine {
    //Game Functionality
    //this holds the high score; timeToWait is how long we should wait until moving the tiles down
    private int highScore, timeToWait;
    //this is the board that will be played
    private Board playingBoard;
    //this is the movingTile that will hold the selected tile
    private MovingTile mTile;
    //this is the userTiles that will generate new playerTiles
    private UserTiles table;
    //this designates whether or not the game is over
    public boolean isGameOver;

    //UI Stuff
    //this is the context the ui is done in
    Activity context;
    //score and highScore are the textviews that will show what the variable name suggests
    private TextView scoreTxt, highScoreTxt;
    //this holds what will be drawn onto the tiles that the player can't interact with (top 3 rows)
    private ImageView[][] imageTiles;
    //this holds the buttons that will hold where the player can place tiles
    private ImageButton[][] playerSpaces;
    //this holds where the tiles will be generated, the button is so the player can select a tile
    private ImageButton[] userTiles;
    //this will be used to play music
    MediaPlayer bgmPlayer;

    /**
     * @param textScore given to uiControl
     * @param textHS given to uiControl
     * @param txtNameBoard the string before the nuber in the ide of each textview
     * @param buttonNameBoard the string before the numbers in the id of each button in board
     * @param nameUserTile the string before the number in the id of each button in user tiles
     * initializes all variables and sets up game
     */
    GameEngine(Activity activity, TextView textScore, TextView textHS, String txtNameBoard, String buttonNameBoard, String nameUserTile, String storedData)
    {
        //we'll created a selected difficulty int to give GameEngine
        SharedPreferences sharedPreferences = activity.getSharedPreferences(storedData, MODE_PRIVATE);
        String selectedDifficulty = sharedPreferences.getString("difficulty", "");
        int selD;
        //we'll find the corresponding number
        switch (selectedDifficulty)
        {
            case "easy":
                selD = 1;
                break;
            case "hard":
                selD = 3;
                break;
            default:
                selD = 2;
                break;
        }
        int hScore = sharedPreferences.getInt("highScore", 0);
        isGameOver = false;
        //set up custom classes
        playingBoard = new Board(selD);
        mTile = new MovingTile();
        table = new UserTiles();
        //get score and highscore textview
        scoreTxt = textScore;
        highScoreTxt = textHS;
        //set up context
        context = activity;
        //set up how long we should wait
        switch (selD)
        {
            case 1:
                timeToWait = 5000;
                break;
            case 2:
                timeToWait = 3000;
                break;
            case 3:
                timeToWait = 1000;
                break;
        }

        //set up the arrays to hold the textViews and buttons
        imageTiles = new ImageView[3][5];
        playerSpaces = new ImageButton[2][5];
        userTiles = new ImageButton[4];

        //sets up our music player
        bgmPlayer = MediaPlayer.create(context, R.raw.tiletrisbgm);

        //we'll iterate through textTiles and set up all the textValue
        for (int x = 0; x < 3; x++)
        {
            for (int y = 0; y < 5; y++)
            {
                String textID = txtNameBoard + String.valueOf(x + 1) + String.valueOf(y);
                int resourceID = activity.getResources().getIdentifier(textID, "id", activity.getPackageName());
                imageTiles[x][y] = (ImageView) activity.findViewById(resourceID);
            }
        }

        //we'll iterate through playerSpaces and set up all the Buttons
        for (int x = 0; x < 2; x++)
        {
            for (int y = 0; y < 5; y++)
            {
                String textID = buttonNameBoard + String.valueOf(x + 4) + String.valueOf(y);
                int resourceID = activity.getResources().getIdentifier(textID, "id", activity.getPackageName());
                playerSpaces[x][y] = (ImageButton) activity.findViewById(resourceID);
                //when the button is clicked we should change the text of the button if mTile is active
                playerSpaces[x][y].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mTile.isActive()) {
                            //we'll need the view id to get the indexes of the button
                            String viewID = view.getResources().getResourceName(view.getId());
                            //from the id, we'll get the indexes
                            int bIndex1 = Character.getNumericValue(viewID.charAt(viewID.length() - 2));
                            int bIndex2 = Character.getNumericValue(viewID.charAt(viewID.length() - 1));
                            //if the tile isn't already occupied, then add it
                            if (playingBoard.getTile(bIndex1, bIndex2) < 1)
                            {
                                //then add the tile to board
                                playingBoard.addTile(bIndex1, bIndex2, mTile.release());
                                //since it changed, we'll redraw board and update score
                                context.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        drawBoard();
                                        drawScore();
                                    }
                                });
                            }
                        }
                    }
                });


            }
        }

        //we'll iterate through userTiles and set up all the Buttons
        for (int x = 0; x < 4; x++)
        {
                String textID = nameUserTile + String.valueOf(x);
                int resourceID = activity.getResources().getIdentifier(textID, "id", activity.getPackageName());
                userTiles[x] = (ImageButton) activity.findViewById(resourceID);
                //TO DO: set up onClickListener
                userTiles[x].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        //we'll need the view id to get the index of the button in the list in table
                        String viewID = view.getResources().getResourceName(view.getId());
                        //from the id, we'll get the index
                        int tableIndex = Character.getNumericValue(viewID.charAt(viewID.length() - 1));
                        //if the button doesn't have an empty tile, then we'll select it and put it in movingTile
                        if (table.getTile(tableIndex) != -1)
                        {
                            int movedTile = mTile.release();
                            mTile.setActive(table.selectTile(tableIndex));
                            table.setTile(tableIndex, movedTile);
                        }

                        //we'll need to drawUserTile because it changed
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                drawUserTile();
                            }
                        });
                    }
                });
        }

        //we'll need to drawUserTile because the tiles will have starting values
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                drawUserTile();
                drawScore();
            }
        });
    }

    /**
     * this holds the gameLoop that will handle everything that happens in the game
     */
    public void gameLoop()
    {
        //we'll start playing the music
        bgmPlayer.start();
        //this will hold the time since last frame so we know when we need to update the baord
        long lastFrame = System.currentTimeMillis();
        //while the game isn't over
        while (!isGameOver)
        {
            //if it's time to update the board, we do
            if (System.currentTimeMillis() - lastFrame >= timeToWait)
            {
                Log.v("IN LOOP", "looping");
                //have the board update itself and check if the game is over
                isGameOver = playingBoard.move();
                if (isGameOver)
                    break;
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
        //at the end of the game, we'll update high score
        updateHighScore();
        ((GameActivity) context).displayGameOver(playingBoard.getScore(), highScore);
        //we'll stop playing our music
        stopMusic();
    }

    /**
     * checks if previous highscore was beaten and if so, modify it in memory
     */
    public void updateHighScore()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("highScore", highScore);
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
                        imageTiles[x][y].setImageResource(R.drawable.emptytile);
                        break;
                    case 0:
                        imageTiles[x][y].setImageResource(R.drawable.enemytile);
                        break;
                    case 1:
                        imageTiles[x][y].setImageResource(R.drawable.swordtile);
                        break;
                    case 2:
                        imageTiles[x][y].setImageResource(R.drawable.greatswordtile);
                        break;
                    case 3:
                        imageTiles[x][y].setImageResource(R.drawable.axetile);
                        break;
                    case 4:
                        imageTiles[x][y].setImageResource(R.drawable.daggertile);
                        break;
                    case 5:
                        imageTiles[x][y].setImageResource(R.drawable.bombtile);
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
                        playerSpaces[x][y].setImageResource(R.drawable.emptytile);
                        break;
                    case 0:
                        playerSpaces[x][y].setImageResource(R.drawable.enemytile);
                        break;
                    case 1:
                        playerSpaces[x][y].setImageResource(R.drawable.swordtile);
                        break;
                    case 2:
                        playerSpaces[x][y].setImageResource(R.drawable.greatswordtile);
                        break;
                    case 3:
                        playerSpaces[x][y].setImageResource(R.drawable.axetile);
                        break;
                    case 4:
                        playerSpaces[x][y].setImageResource(R.drawable.daggertile);
                        break;
                    case 5:
                        playerSpaces[x][y].setImageResource(R.drawable.bombtile);
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
                    userTiles[x].setImageResource(R.drawable.emptytile);
                    break;
                case 0:
                    userTiles[x].setImageResource(R.drawable.enemytile);
                    break;
                case 1:
                    userTiles[x].setImageResource(R.drawable.swordtile);
                    break;
                case 2:
                    userTiles[x].setImageResource(R.drawable.greatswordtile);
                    break;
                case 3:
                    userTiles[x].setImageResource(R.drawable.axetile);
                    break;
                case 4:
                    userTiles[x].setImageResource(R.drawable.daggertile);
                    break;
                case 5:
                    userTiles[x].setImageResource(R.drawable.bombtile);
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

        int curScore = playingBoard.getScore();
        scoreTxt.setText("Score: " + String.valueOf( curScore ));
        if (curScore > highScore)
            highScoreTxt.setText("HighScore: " + String.valueOf(curScore));
    }

    /**
     * this is used to stop the music when the current gameActivity is done
     */
    public void stopMusic()
    {
        //if the music player hasn't been destroyed yet...
        if (bgmPlayer != null)
        {
            //...we'll destroy it
            bgmPlayer.stop();
            bgmPlayer.release();
            bgmPlayer = null;
        }
    }
}
