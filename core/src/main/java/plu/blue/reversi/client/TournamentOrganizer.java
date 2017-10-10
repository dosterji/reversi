package plu.blue.reversi.client;

import java.util.ArrayList;

/**
 * Created by kyleb on 4/30/2017.
 */
public class TournamentOrganizer{
    //Max Players
    private final int maxPlayers = 32;
    //Counts the number of players
    private int numPlayers;

    //ArrayList to hold player names
    private String[] playerList;
    private int arraySize;

    public TournamentOrganizer(int size){
        arraySize = size;
        playerList = new String[arraySize];
    }

    public void addPlayer(String name){
        playerList[numPlayers] = name;
        numPlayers++;
    }

    public int getNumPlayers(){
        return numPlayers;
    }

    public String[] getNames(){
        return playerList;
    }

}

