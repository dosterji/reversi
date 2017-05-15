package plu.blue.reversi.test;

import org.junit.Before;
import org.junit.Test;
import plu.blue.reversi.client.TournamentOrganizer;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by kyleb on 5/12/2017.
 */
public class TournamentOrganizerTest {
    private TournamentOrganizer tO;

    @Test
    public void addPlayer() throws Exception {
        tO = new TournamentOrganizer(2);
        tO.addPlayer("Kyle");
        tO.addPlayer("Michael");

        String[] players = tO.getNames();

        for(int i=0; i<tO.getNumPlayers(); i++){
            System.out.println(players[i]);
        }

    }

}