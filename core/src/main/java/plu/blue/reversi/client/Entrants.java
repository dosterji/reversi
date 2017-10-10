package plu.blue.reversi.client;

/**
 * Created by kyleb on 5/9/2017.
 */
public class Entrants {
    //Field delcarations
    private String name;
    private int numWins = 0;
    private boolean lost = false;

    public Entrants(String nm){
        name = nm;
        numWins = 0;
        lost = false;

    }

    //GETTERS
    public String getName(){
        return name;
    }

    public int getNumWins(){
        return numWins;
    }

    public boolean getLost(){
        return lost;
    }

    //SETTERS
    public void setNumWins(int num){
        numWins = num;
    }

    public void setLoss(boolean flag){
        lost = flag;
    }

}
