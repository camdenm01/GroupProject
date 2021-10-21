package com.example.groupproject;

public class Board {
    private int[][] board;

    Board()
    {
        board = new int[5][7];
        for (int x = 0; x < 5; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                board[x][y] = -1;
            }
        }
    }

    public boolean move()
    {
        return false;
    }

    public void collide(int xPos, int yPos, int curTile, int movTile)
    {

    }

    public void addTile(int xPos, int yPos, int tileType)
    {
        board[xPos][yPos] = tileType;
    }

    public void drawBoard()
    {

    }
}
