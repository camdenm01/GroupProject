package com.example.groupproject;

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
        board = new int[5][7];
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
    public boolean move()
    {
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
        return false;
    }

    /**
     * @param xPos x position of targetted tile
     * @param yPos y position of targetted tile
     * @return the tile at x, y
     */
    public int getTile(int xPos, int yPos)
    {
        return 0;
    }
}
