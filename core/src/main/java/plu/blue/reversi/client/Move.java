package plu.blue.reversi.client;

/**
 * A class that holds the information for a particular move.
 * Created by John on 3/17/2017.
 */
public class Move {
    /*The Coordinate at which the move was made */
    private Coordinate cell;
    /* The player who made the move */
    private Player player;

    // +--------------+
    // | Constructors |
    // +--------------+
    public Move(Coordinate c, Player p) {
        this.cell = c;
        this.player = p;
    }

    // +---------+
    // | Getters |
    // +---------+
    public Coordinate getCoordinate() {return cell;}
    public Player getPlayer() {return player;}

    public String toString() {
        return cell.toString() +", " + player.toString();
    }

}
