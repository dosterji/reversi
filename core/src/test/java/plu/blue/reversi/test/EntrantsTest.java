package plu.blue.reversi.test;

import org.junit.Before;
import org.junit.Test;
import plu.blue.reversi.client.Entrants;
import plu.blue.reversi.client.TournamentOrganizer;

import static org.junit.Assert.*;

/**
 * Created by kyleb on 5/12/2017.
 */
public class EntrantsTest {
    public TournamentOrganizer tO;
    Entrants e1, e2;

    @Before
    public void init(){
        tO = new TournamentOrganizer(2);

        tO.addPlayer("Kyle");
        tO.addPlayer("Steve");

        String[] players = tO.getNames();
        e1 = new Entrants(players[0]);
        e2 = new Entrants(players[1]);

    }

    @Test
    public void getName() throws Exception {
        assertEquals("Kyle", e1.getName());
        assertEquals("Steve", e2.getName());
    }

    @Test
    public void getLoss() throws Exception {
        e1.setLoss(true);
        assertTrue(e1.getLost());
        assertFalse(e2.getLost());
    }

}