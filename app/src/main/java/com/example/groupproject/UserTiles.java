package com.example.groupproject;

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

    }

    /**
     * this determines if a tile should be generated using timeLastGen
     * if it is generated, then we randomly generate a playerTile and add it to storedTiles
     */
    public void generateTile()
    {

    }

    /**
     * @param selectedIndex holds the index of which tile is being selected
     * @return the tile being selected
     * this returns the tile at [selectedIndex] and removes the tile from the list
     */
    public int selectTile(int selectedIndex)
    {
        return -1;
    }

    /**
     * @param selectedIndex holds the index of which tile is being selected
     * @return the tile being selected
     * this returns the tile at [selectedIndex]
     */
    public int getTile(int selectedIndex)
    {
        return -1;
    }

    /**
     * increments timeLastGen
     */
    public void incTimeLastGen()
    {
        timeLastGen++;
    }
}
