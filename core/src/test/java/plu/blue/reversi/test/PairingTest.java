package plu.blue.reversi.test;

import org.junit.Before;
import org.junit.Test;
import plu.blue.reversi.client.Entrants;
import plu.blue.reversi.client.Pairing;
import plu.blue.reversi.client.TournamentOrganizer;

import static org.junit.Assert.*;

/**
 * Created by kyleb on 5/12/2017.
 */
public class PairingTest {
    TournamentOrganizer tO;
    Entrants e1, e2;
    Pairing pair;

    @Before
    public void init(){
        tO = new TournamentOrganizer(2);

        tO.addPlayer("Kyle");
        tO.addPlayer("Steve");

        String[] players = tO.getNames();
        e1 = new Entrants(players[0]);
        e2 = new Entrants(players[1]);
        pair = new Pairing(e1, e2);

    }

    @Test
    public void getPlayer1() throws Exception {
        Entrants temp = pair.getPlayer1();
        System.out.println(temp.getName());

    }

    @Test
    public void getPlayer2() throws Exception {
        Entrants temp = pair.getPlayer2();
        System.out.println(temp.getName());
    }

}