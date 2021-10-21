package com.example.groupproject;

public class MovingTile {
    private int tileType;
    private boolean active;

    MovingTile(int type)
    {
        tileType = type;
    }

    public void updatePos()
    {

    }

    public int release()
    {
        return -1;
    }

    public boolean isActive()
    {
        return false;
    }
}
