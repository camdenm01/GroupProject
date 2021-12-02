package com.example.groupproject;

/**
 * this is the currently selected tile, in the final version it will be dragged around the screen
 */
public class MovingTile {
    //this holds the tile that has been selected
    private int tileType;
    //this shows if there is a currently selected tile, it will designate when the movingTile should be drawn
    private boolean active;

    /**
     * simply creates the tile for use later
     */
    MovingTile()
    {
        tileType = -1;
        active = false;
    }

    /**
     * @param inputTile the tile the movingTile should represent
     * this will set the movingTile as active and give it a tileType
     */
    public void setActive(int inputTile)
    {
        tileType = inputTile;
        active = true;
    }

    /**
     * @return tileType
     * this is called when the movingTile is released and should no longer be dragged
     * this sets the tile as inactive and returns tileType so GameEngine can use it
     */
    public int release()
    {
        active = false;
        return tileType;
    }

    /**
     * @return active
     */
    public boolean isActive()
    {
        return active;
    }
}
