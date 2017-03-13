package plu.blue.reversi.client;

/**
 * Created by John on 3/13/2017.
 */
/**
 * Pivate Inner Class
 * Holds the information about a player.
 */
public class Player {
    //fields
    private String name;
    private int score;
    /* The color that this player is designed to use.
        The black player is designated by a integer value of 1.
        The white player is designated by a integer value of -1.
        Blank spaces on the board are designated by 0's.
     */
    private int color;

    public Player(String n, int c) {
        name = n;
        color = c;
        score = 0;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int s) {
        score = s;
    }
    public int getColor() {
        return color;
    }
    public String getName() {
        return name;
    }
} //End Player
