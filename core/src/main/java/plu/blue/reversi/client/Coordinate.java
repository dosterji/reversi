package plu.blue.reversi.client;

/**
 * Created by John on 3/13/2017.
 * A class for storing coordinate data.
 */
public class Coordinate {

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
