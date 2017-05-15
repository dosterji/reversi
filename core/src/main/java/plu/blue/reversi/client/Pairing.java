package plu.blue.reversi.client;

/**
 * Created by kyleb on 5/9/2017.
 */
public class Pairing{
    Entrants player1;
    Entrants player2;

    public Pairing(Entrants p1, Entrants p2){
        player1 = p1;
        player2 = p2;
    }

    public Entrants getPlayer1(){
        return player1;
    }

    public Entrants getPlayer2(){
        return player2;
    }
}
