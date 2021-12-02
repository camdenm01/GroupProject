package com.example.groupproject;

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
     * 1 = sword, 2 = great-sword, 3 = wave, 4 = bishop, 5 = bomb
     * see CSC415_groupProject for what each player does upon activation
     */
    private int[][] board;

    /**
     * this initializes board and sets all the tiles to be empty
     */
    Board()
    {
        board = new int[7][5];
        for (int x = 0; x < 5; x++)
        {
            for (int y = 0; y < 7; y++)
            {
                board[x][y] = -1;
            }
        }
    }

    /**
     * this will generate random enemy tiles and move everything downward
     * if a tile would move onto an occupied space, collide should be called
     * @return true if the player lost, false otherwise
     */
    public boolean move() {
        //we'll check if the player loses
        for (int x = 0; x < 5; x++)
        {
            //if there are any enemy tiles in the bottom row, player loses
            if (board[6][x] == 0)
            {
                return true;
            }
        }

        //we'll randomly generate the upper row
        Random rand = new Random();
        for (int x = 0; x < 5; x++)
        {
            if (rand.nextInt(3) == 0)
            {
                board[0][x] = 0;
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
                    collide(x, y, board[x][y]);
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
        board[xPos][yPos] = -1;
        board[xPos - 1][yPos] = -1;

        //now we have a different effect for each player tile
        switch (curTile)
        {
            case 1:
                //as a sword, empty two tiles
                board[xPos - 2][yPos] = -1;
                board[xPos - 3][yPos] = -1;
                break;
            case 2:
                //as a great-sword, eliminate all tiles in a vertical role
                for (int x = 0; x < xPos; x++)
                {
                    board[x][yPos] = -1;
                }
                break;
            case 3:
                //as a wave, eliminate all tile in a cone
                if (yPos > 0)
                {
                    board[xPos - 2][yPos - 1] = -1;
                    board[xPos - 1][yPos - 1] = -1;
                }
                if (yPos < 5)
                {
                    board[xPos - 2][yPos + 1] = -1;
                    board[xPos - 1][yPos + 1] = -1;
                }
                board[xPos - 2][yPos + 1] = -1;
                break;
            case 4:
                //bishop, destroy diagonal tiles
                if (yPos > 0)
                {
                    board[xPos - 2][yPos - 1] = -1;
                }
                if (yPos < 5)
                {
                    board[xPos - 2][yPos + 1] = -1;
                }
                break;
            case 5:
                //as a bomb, destroy all surrounding tiles
                if (yPos > 0)
                {
                    board[xPos - 2][yPos - 1] = -1;
                    board[xPos - 1][yPos - 1] = -1;
                    board[xPos][yPos - 1] = -1;
                }
                if (yPos < 5)
                {
                    board[xPos - 2][yPos + 1] = -1;
                    board[xPos - 1][yPos + 1] = -1;
                    board[xPos][yPos + 1] = -1;
                }
                if (xPos < 6)
                {
                    board[xPos + 1][yPos] = -1;
                }
                board[xPos - 2][yPos] = -1;
                break;
        }
    }

    /**
     * @param xPos x position of tile to be added
     * @param yPos y position of tile to be added
     * @param tileType the number of the tile to be added
     * @return true if the tile is occupied by a playerTile, false otherwise
     * this checks if the tile at x, y is occupied, if it is by a player tile, return false
     * if it is occupied by an enemy tile, call collide
     * if the tile is empty, simply set the tile to be the given tileType
     */
    public boolean addTile(int xPos, int yPos, int tileType)
    {
        if (board[xPos][yPos] > 0)
        {
            return true;
        }
        else if (board[xPos][yPos] == 0)
        {
            collide(xPos, yPos, tileType);
        }
        else
        {
            board[xPos][yPos] = tileType;
        }
        return false;
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
}
