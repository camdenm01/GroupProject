package com.example.groupproject;

import android.os.Bundle;

import java.util.Random;

/**
 * this will store the playerTiles that the user can select and put on the board
 */
public class UserTiles {
    //this stores the tiles that can be selected by the player
    //see documentation in Board.java for which tile is represented by which number
    private int[] storedTiles;
    //this stores the time since the last tile was generated
    //as this number increases, the percentage chance of a tile being generated is increased
    //this is incremented each time the board is moved
    private int timeLastGen;

    /**
     * this sets up the storedTile array and generates starting tiles
     * sets timeLastGen = 0 to start
     */
    UserTiles()
    {
        //created the array storedTiles and fill it with empty tiles
        storedTiles = new int[4];
        for (int x = 0; x < 4; x++)
        {
            storedTiles[x] = -1;
        }
        Random rand = new Random();
        storedTiles[0] = rand.nextInt(5) + 1;
        //set up timeLastGen
        timeLastGen = 0;
    }

    /**
     * this determines if a tile should be generated using timeLastGen
     * if it is generated, then we randomly generate a playerTile and add it to storedTiles
     */
    public void generateTile()
    {
        //we'll need to store the index of an empty tile if there is any
        int emptyTile = -1;
        //we'll go through the array...
        for (int x = 0; x < 4; x++)
        {
            //...looking for an empty tile...
            if (storedTiles[x] == -1 && emptyTile == -1)
            {
                //...if we found one, we'll store the index
                emptyTile = x;
            }
        }
        //if we found an empty tile...
        if (emptyTile != -1)
        {
            //...then we'll see if we'll generate an empty tile
            //we'll need to generate a random tile
            Random rand = new Random();
            //if it's been too long since last time we generated a tile...
            if (timeLastGen == 1)
            {
                //...then we'll generate a tile without randomizing it
                storedTiles[emptyTile] = rand.nextInt(5) + 1;
                //since we just made a tile, timeLastGen = 0
                timeLastGen = 0;
            }
            //else we'll randomly determine if we should generate a tile
            //the longer it's been since last gen, the more often we'll make a tile
            else if (rand.nextInt(1 - timeLastGen) == 0)
            {
                storedTiles[emptyTile] = rand.nextInt(5) + 1;
                //since we just made a tile, timeLastGen = 0
                timeLastGen = 0;
            }
            //if we didn't make a tile, we'll increment timeLastGen
            else
            {
                timeLastGen++;
            }
        }
    }

    /**
     * @param selectedIndex holds the index of which tile is being selected
     * @return the tile being selected
     * this returns the tile at [selectedIndex] and removes the tile from the list
     */
    public int selectTile(int selectedIndex)
    {
        int tileType = storedTiles[selectedIndex];
        storedTiles[selectedIndex] = -1;
        return tileType;
    }

    /**
     * @param selectedIndex holds the index of which tile is being selected
     * @return the tile being selected
     * this returns the tile at [selectedIndex]
     */
    public int getTile(int selectedIndex)
    {
        return storedTiles[selectedIndex];
    }

    public void setTile(int selectedIndex, int tileNum)
    {
        storedTiles[selectedIndex] = tileNum;
    }

    public void getUserTileState(Bundle savedInstanceState)
    {
        storedTiles = savedInstanceState.getIntArray("userTileArray");
    }

    public void saveUserTile(Bundle outState)
    {
        outState.putIntArray("userTileArray", storedTiles);
    }

    /**
     * increments timeLastGen
     */
    public void incTimeLastGen()
    {
        timeLastGen++;
    }
}
