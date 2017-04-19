package plu.blue.reversi.client.gui;


import java.awt.*;

/**
 * Enum of Colors
 */
public enum ColorSet {
    WHITE(Color.WHITE),
    LIGHT_GRAY(Color.LIGHT_GRAY),
    GRAY(Color.GRAY),
    DARK_GRAY(Color.DARK_GRAY),
    BLACK(Color.BLACK),
    RED(Color.RED),
    PINK(Color.PINK),
    ORANGE(Color.ORANGE),
    YELLOW(Color.YELLOW),
    GREEN(Color.GREEN),
    MAGENTA(Color.MAGENTA),
    CYAN(Color.CYAN),
    BLUE(Color.BLUE);


    private Color color = null;

    ColorSet(Color c) {
        this.color = c;
    }

    public static int Length() {
        return ColorSet.values().length;
    }

    public Color GetColor() {
        return color;
    }

}





