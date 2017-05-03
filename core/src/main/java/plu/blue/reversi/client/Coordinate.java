package plu.blue.reversi.client;

import java.io.Serializable;

/**
 * Created by John on 3/13/2017.
 * A class for storing coordinate data.
 */
public class Coordinate implements Serializable {

    // For serializing purposes
    private static final long serialVersionUID = 39001L;

    /** The Row Location */
    private int rowLoc;
    /** The Column Location */
    private int colLoc;

    public Coordinate(int row, int col) {
        rowLoc = row;
        colLoc = col;
    }

    /**
     * Getters
     * @return The Location Specified
     */
    public int getRowLocation() {
        return rowLoc;
    }
    public int getColLocation() {
        return colLoc;
    }

    /**
     * An equals method that tells whether two coordinates are equal
     * @param other the other coordinate
     * @return true or false
     */
    public boolean equals(Coordinate other) {
        if( this.toString().equals(other.toString()))
            return true;
        else
            return false;
    }
    /**
     * Tells whether this coordinate is a corner
     * @return
     */
    public boolean isCorner() {
        if(this.equals(new Coordinate(0,0)) || this.equals(new Coordinate(0,7))
                || this.equals(new Coordinate(7,0)) || this.equals(new Coordinate(7,7)))
            return true;
        else
            return false;
    }
    /**
     * Creates a string representation of this object in the form:
     *                  "[rowLoc, colLoc]"
     * @return The string representation
     */
    public String toString() {
        String s = "";
        switch(colLoc){
            case 0: s+= "A";
                    break;
            case 1: s+= "B";
                    break;
            case 2: s+= "C";
                    break;
            case 3: s+= "D";
                    break;
            case 4: s+= "E";
                    break;
            case 5: s+= "F";
                    break;
            case 6: s+= "G";
                    break;
            default: s+= "H";
                    break;
        }
        s+= rowLoc+1;
        return s;
    }
}
