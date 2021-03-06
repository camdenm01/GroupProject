package com.example.groupproject;

import android.os.Bundle;
import android.util.Log;

import java.util.Random;

/**
 * this will represent the game board and hold all the tiles
 */
public class Board {
    /**
     * this is a 2D array that holds all the tiles on the board
     * 0, 0 is the top left
     * possible tiles are:
     * -1 = empty, 0 = enemy
     * 1 = sword, 2 = great-sword, 3 = twin-axes, 4 = bishop, 5 = bomb
     * see CSC415_groupProject for what each player does upon activation
     */
    private int[][] board;
    private long[][] lastFrameTime;
    //curNumEnemy = number of enemies on the board; totalNumEnemy = number of enemy tiles generated; difficulty = diffculty selected in setting
    private int curNumEnemy, totalNumEnemy, difficulty;

    /**
     * this initializes board and sets all the tiles to be empty
     */
    Board(int inputD)
    {
        board = new int[7][5];
        lastFrameTime = new long[7][5];
        for (int x = 0; x < 7; x++)
        {
            for (int y = 0; y < 5; y++)
            {
                board[x][y] = -1;
            }
        }
        curNumEnemy = 0;
        totalNumEnemy = 0;
        difficulty = inputD;
    }

    /**
     * this will generate random enemy tiles and move everything downward
     * if a tile would move onto an occupied space, collide should be called
     * @return true if the player lost, false otherwise
     */
    public boolean move() {
        //we'll randomly generate the upper row
        Random rand = new Random();
        for (int x = 0; x < 5; x++)
        {
            //1/2 tiles will be enemy tiles
            if (rand.nextInt(2) == 0)
            {
                board[0][x] = 0;
                totalNumEnemy++;
            }
        }

        //we'll move everything down one space
        for (int x = 6; x > 0; x--)
        {
            for (int y = 0; y < 5; y++)
            {
                //if an enemy tile would hit a player tile
                if (board[x][y] > 0 && board[x - 1][y] == 0)
                {
                    //we call collide
                    //collide(x, y, board[x][y], 1);
                    board[x][y] = board[x][y] + 5;
                    board[x - 1][y] = -1;
                }

                //if we need to move an enemy tile down, we do
                if (board[x - 1][y] == 0)
                {
                    //we move the enemy tile down
                    board[x][y] = board[x - 1][y];
                    //we empty the tile that moved
                    board[x - 1][y] = -1;
                }
            }
        }

        //we'll resolve any collisions of the bombs
        for (int x = 6; x > 0; x--)
        {
            for (int y = 0; y < 5; y++)
            {
                //if an enemy tile would hit a player tile
                if (board[x][y] == 10) {
                    collide(x, y,  5);
                }
            }
        }

        //we'll resolve the rest of the collisions
        for (int x = 6; x > 0; x--)
        {
            for (int y = 0; y < 5; y++)
            {
                //if an enemy tile would hit a player tile
                if (board[x][y] > 5) {
                    collide(x, y, board[x][y] - 5);
                }
            }
        }

        //we'll check if the player loses
        for (int x = 0; x < 5; x++)
        {
            //if there are any enemy tiles in the bottom row, player loses
            if (board[6][x] == 0)
            {
                return true;
            }
        }

        //since we're here, we haven't lost
        return false;
    }

    /**
     * @param xPos x position of collision
     * @param yPos y position of collision
     * @param curTile the tile that is in the occupied spot
     * depending on curTile, different effects should happen depending on currTile
     * see CSC415_groupProject for different file effects
     * after effects, tile at xPos, yPos should be empty
     */
    public void collide(int xPos, int yPos, int curTile)
    {
        //empty both tiles
        board[xPos][yPos] = -2;
        lastFrameTime[xPos][yPos] = System.currentTimeMillis();
        //board[xPos - xPosMod][yPos] = -1;

        //now we have a different effect for each player tile
        switch (curTile)
        {
            case 1:
                //as a sword, empty two tiles
                board[xPos - 1 ][yPos] = -2;
                board[xPos - 2 ][yPos] = -2;
                lastFrameTime[xPos - 1][yPos] = System.currentTimeMillis();
                lastFrameTime[xPos - 2][yPos] = System.currentTimeMillis();
                break;
            case 2:
                //as a great-sword, eliminate all tiles in a vertical role
                for (int x = 0; x < xPos; x++)
                {
                    board[x][yPos] = -2;
                    lastFrameTime[x][yPos] = System.currentTimeMillis();
                }
                break;
            case 3:
                //as twin-axes, eliminate all tile in two lines offset from center
                if (yPos > 0)
                {
                    board[xPos - 1 ][yPos - 1] = -2;
                    board[xPos - 2 ][yPos - 1] = -2;
                    lastFrameTime[xPos - 1][yPos - 1] = System.currentTimeMillis();
                    lastFrameTime[xPos - 2][yPos - 1] = System.currentTimeMillis();
                }
                if (yPos < 4)
                {
                    board[xPos - 1 ][yPos + 1] = -2;
                    board[xPos - 2 ][yPos + 1] = -2;
                    lastFrameTime[xPos - 1][yPos + 1] = System.currentTimeMillis();
                    lastFrameTime[xPos - 1][yPos + 1] = System.currentTimeMillis();
                }

                break;
            case 4:
                //twin daggers, destroy one tile ahead and diagonal tiles (cone
                board[xPos - 1 ][yPos] = -2;
                lastFrameTime[xPos - 1][yPos] = System.currentTimeMillis();
                if (yPos > 0)
                {
                    board[xPos - 1 ][yPos - 1] = -2;
                    lastFrameTime[xPos - 1][yPos - 1] = System.currentTimeMillis();
                }
                if (yPos < 4)
                {
                    board[xPos - 1 ][yPos + 1] = -2;
                    lastFrameTime[xPos - 1][yPos + 1] = System.currentTimeMillis();
                }
                break;
            case 5:
                //as a bomb, destroy all surrounding tiles
                if (yPos > 0)
                {
                    board[xPos - 1 ][yPos - 1] = -2;
                    board[xPos ][yPos - 1] = -2;
                    lastFrameTime[xPos - 1][yPos - 1] = System.currentTimeMillis();
                    lastFrameTime[xPos][yPos - 1] = System.currentTimeMillis();
                    if (xPos  + 1 < 7) {
                        board[xPos + 1 ][yPos - 1] = -2;
                        lastFrameTime[xPos + 1][yPos - 1] = System.currentTimeMillis();
                    }
                }
                if (yPos < 4)
                {
                    board[xPos - 1 ][yPos + 1] = -2;
                    board[xPos ][yPos + 1] = -2;
                    lastFrameTime[xPos - 1][yPos + 1] = System.currentTimeMillis();
                    lastFrameTime[xPos][yPos + 1] = System.currentTimeMillis();
                    if (xPos  + 1 < 7) {
                        board[xPos + 1 ][yPos + 1] = -2;
                        lastFrameTime[xPos + 1][yPos + 1] = System.currentTimeMillis();
                    }
                }
                if (xPos  + 1 <= 6)
                {
                    board[xPos + 1 ][yPos] = -2;
                    lastFrameTime[xPos + 1][yPos] = System.currentTimeMillis();
                }
                board[xPos - 1 ][yPos] = -2;
                lastFrameTime[xPos - 1][yPos] = System.currentTimeMillis();
                break;
        }
    }

    /**
     * this converts all the destroyed tiles to empty tiles
     */
    public boolean destroyToEmpty(int timeWait)
    {
        boolean needChange = false;
        //we'll resolve the rest of the collisions
        for (int x = 6; x > 0; x--)
        {
            for (int y = 0; y < 5; y++)
            {
                if (board[x][y] == -2 && System.currentTimeMillis() - lastFrameTime[x][y] >= timeWait/3)
                {
                    board[x][y] = -1;
                    needChange = true;
                }
            }
        }

        return needChange;
    }

    /**
     * @param xPos x position of tile to be added
     * @param yPos y position of tile to be added
     * @param tileType the number of the tile to be added
     * this checks if the tile at x, y is occupied, if it is by a player tile, return false
     * if it is occupied by an enemy tile, call collide
     * if the tile is empty, simply set the tile to be the given tileType
     */
    public void addTile(int xPos, int yPos, int tileType)
    {
        if (board[xPos][yPos] == 0)
        {
            collide(xPos, yPos, tileType);
        }
        else
        {
            board[xPos][yPos] = tileType;
        }
    }

    /**
     * @param xPos x position of targetted tile
     * @param yPos y position of targetted tile
     * @return the tile at x, y
     */
    public int getTile(int xPos, int yPos)
    {
        return board[xPos][yPos];
    }

    /**
     * @return score which is the difficulty * number of enemies destroyed
     */
    public int getScore()
    {
        //now we'll count how many enemy tiles there were and make the score based on that
        curNumEnemy = 0;
        for (int x = 0; x < 7; x++)
        {
            for (int y = 0; y < 5; y++)
            {
                if (board[x][y] == 0)
                {
                    curNumEnemy++;
                }
            }
        }

        //return the score
        return difficulty * (totalNumEnemy - curNumEnemy);
    }

    public void getBoardState(Bundle savedInstanceState)
    {
        for (int x = 0; x < 7; x++)
        {
            Log.e("Board Class: ", "getting board");
            board[x] = savedInstanceState.getIntArray("boardArray" + String.valueOf(x));
            lastFrameTime[x] = savedInstanceState.getLongArray("lastFrame" + String.valueOf(x));
        }
        totalNumEnemy = savedInstanceState.getInt("tilesGenerated");
    }

    public void saveBoard(Bundle outState)
    {
        for (int x = 0; x < 7; x++)
        {
            Log.e("Board Class: ", "saving board");
            outState.putIntArray("boardArray" + String.valueOf(x), board[x]);
            outState.putLongArray("lastFrame" + String.valueOf(x), lastFrameTime[x]);
        }
        outState.putInt("tilesGenerated", totalNumEnemy);
    }
}
