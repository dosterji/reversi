package plu.blue.reversi.client;

import java.awt.*;
import java.io.Serializable;

/**
 * Holds the information about a player.
 * Created by John on 3/13/2017
 */
public class Player implements Serializable {
    //fields
    private String name;
    private int score;
    /* The color that this player is designed to use.
        The black player is designated by a integer value of 1.
        The white player is designated by a integer value of -1.
        Blank spaces on the board are designated by 0's.
     */
    private int color;
    private Color PieceColor;

    public Player(String n, int c) {
        name = n;
        color = c;
        score = 2;
        if(c == -1) PieceColor = Color.BLACK;
        else PieceColor = Color.WHITE;
    }
    public Player( Player other) {
        name = other.getName();
        color = other.getColor();
        score = other.getScore();
        PieceColor = other.getPieceColor();
    }

    // +------------------+
    // | Getters & Setters|
    // +------------------+
    public int getScore() {
        return score;
    }
    public void setScore(int s) {
        score = s;
    }
    public int getColor() {
        return color;
    }

    /**
     * Gets the Color of the Piece the Player current has
     * @return - Color of players piece
     */
    public Color getPieceColor() {
        return PieceColor;
    }


    public void setPieceColor(Color newColor) {
        PieceColor = newColor;
    }
    public String getName() {
        return name;
    }

    /**
     * Get a String representation of the PLayer
     * @return the string
     */
    public String toString() {
        return "Player: " + name + "; Score: " + score + ".";
    }
} //End Player
