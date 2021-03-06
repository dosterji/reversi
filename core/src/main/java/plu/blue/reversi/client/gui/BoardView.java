package plu.blue.reversi.client.gui;

import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.TimingTargetAdapter;
import plu.blue.reversi.client.BestMove;
import plu.blue.reversi.client.Coordinate;
import plu.blue.reversi.client.model.CPU;
import plu.blue.reversi.client.Move;
import plu.blue.reversi.client.model.ReversiGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static plu.blue.reversi.client.gui.BoardView.CellColor.*;

/**
 * The JPanel containing the board and its edges.
 */
public class BoardView extends JPanel implements MouseListener {

    /** The size (width and height) of the board in pixels */
    private int size;

    private PlayerInfoPanel panel;

    /** Used to help with animation */
    private FlipAnimator fAnimator;

    /** The state of each cell on the board */
    private CellState[][] boardState;

    /** The model for the game */
    private ReversiGame game;

    /** For help with the CPU animation */
    private AnimationState threads;

    /** The gui */
    private GameWindow gui;


    /** Sets the state of activating available moves for a player.*/
    private boolean activateMovesAvailable;
    private boolean activateBestAvailable;

    /**
     * This is an internal class used to manage the animation of pieces flipping over.
     */
    private class FlipAnimator extends TimingTargetAdapter {

        private ArrayList<CellState> cells;
        private CellColor startColor;
        private CellColor endColor;
        private Animator animator;

        @Override
        public void end(Animator source) {
            for( CellState c : cells ) {
                c.cellColor = endColor;
                c.height = 1.0f;
            }
            repaint();
            threads.pop();
            if(threads.peek())
                animateComputerMove();
        }

        @Override
        public void timingEvent(Animator source, double fraction) {
            double cellChunk = 1.0 / cells.size();
            double cellChunk2 = cellChunk / 2.0;
            for(int i = 0; i < cells.size(); i++ ) {
                double cellStart = (i * cellChunk);
                double cellEnd = ((i+1) * cellChunk);
                CellState c = cells.get(i);
                if( fraction > cellStart && fraction <= cellEnd ) {
                    double h = 0.0;
                    if( fraction < cellStart + cellChunk2 ) {
                        h = 1.0 - (fraction - cellStart) / cellChunk2;
                        c.cellColor = startColor;
                    } else {
                        h = 1.0 - (cellEnd - fraction) / cellChunk2;
                        c.cellColor = endColor;
                    }
                    c.height = (float)h;
                } else if( fraction > cellEnd ) {
                    c.cellColor = endColor;
                    c.height = 1.0f;
                }
            }

            repaint();
        }

        @Override
        public void begin(Animator source) {
            for( CellState c : cells ) {
                c.cellColor = startColor;
                c.height = 1.0f;
            }
            repaint();
            threads.push();
        }

        public FlipAnimator(int startRow, int startCol,
                            int endRow, int endCol,
                            CellColor start, CellColor end,
                            long milliseconds)
        {
            cells = new ArrayList<CellState>();
            this.startColor = start;
            this.endColor = end;

            // Gather a list of cells to animate

            if( startRow == endRow ) {
                if( startCol > endCol ) {
                    for( int col = startCol; col >= endCol; col--)
                        cells.add(boardState[startRow][col]);
                } else {
                    for( int col = startCol; col <= endCol; col++ )
                        cells.add(boardState[startRow][col]);
                }
            } else if( startCol == endCol ) {
                if( startRow > endRow ) {
                    for( int row = startRow; row >= endRow; row--)
                        cells.add(boardState[row][startCol]);
                } else {
                    for( int row = startRow; row <= endRow; row++)
                        cells.add(boardState[row][startCol]);
                }
            } else if( Math.abs(startRow - endRow) == Math.abs(startCol - endCol)) {
                if( startCol > endCol ) {
                    if( startRow > endRow ) {
                        for (int row = startRow, col = startCol; col >= endCol; col--, row--)
                            cells.add(boardState[row][col]);
                    } else {
                        for (int row = startRow, col = startCol; col >= endCol; col--, row++)
                            cells.add(boardState[row][col]);
                    }
                } else {
                    if( startRow > endRow ) {
                        for (int row = startRow, col = startCol; col <= endCol; col++, row--)
                            cells.add(boardState[row][col]);
                    } else {
                        for (int row = startRow, col = startCol; col <= endCol; col++, row++)
                            cells.add(boardState[row][col]);
                    }
                }
            } else {
                throw new IllegalArgumentException("Start/end must define a horizontal, vertical or diagonal line.");
            }

            if( cells.size() == 0 ) {
                throw new IllegalArgumentException("No cells in given range.");
            }

            animator = new Animator.Builder()
                    .setDuration(milliseconds * cells.size(), TimeUnit.MILLISECONDS)
                    .addTarget(this).build();
        }

        public void start() {
            animator.start();
        }

    }

    public enum CellColor {
        WHITE, BLACK, EMPTY, GRAY, BLUE
    }

    private class CellState {
        private float height;  // Fractional height used for animation
        private CellColor cellColor;
        private int row, col;

        public CellState( int row, int col ) {
            cellColor = CellColor.EMPTY;
            this.row = row;
            this.col = col;
            this.height = 1.0f;
        }

        public void setColor( CellColor c)
        {
            cellColor = c;
        }

        public CellColor getColor() { return cellColor; }

        public void draw(Graphics g, float cellSize) {

            if (cellColor != CellColor.EMPTY) {
                float pad = cellSize * 0.1f;
                float x = col * cellSize;
                float cy = cellSize * (row  + 0.5f);
                float h = height * (cellSize - 2.0f * pad);

                if (cellColor == CellColor.BLACK)
                    g.setColor(game.getP1().getPieceColor());
                else if(cellColor == CellColor.GRAY) {
                    g.setColor(Color.lightGray);
                }
                else if (cellColor == CellColor.BLUE){
                    g.setColor(Color.BLUE);
                }
                else
                    g.setColor(game.getP2().getPieceColor());

                g.fillOval(
                        Math.round(x + pad),
                        Math.round(cy - h / 2.0f),
                        Math.round(cellSize - 2 * pad),
                        Math.round(h));
            }
        }
    }

    /**
     * Constructs a new BoardView panel with the given size.
     *
     * @param size width/height in pixels
     */
    public BoardView( int size, ReversiGame game, PlayerInfoPanel playerPanel, GameWindow w )
    {
        threads = new AnimationState();
        gui = w;
        this.game = game;
        this.size = size;
        this.setPreferredSize(new Dimension(500,500) );
        this.setBackground(new Color(12, 169, 18));
        boardState = new CellState[size][size];
        for( int i = 0; i < size ; i++)
            for(int j = 0; j < size; j++ )
                boardState[i][j] = new CellState(i, j);

        //Initializes the score in PlayerInfoPanel
        panel = playerPanel;
        panel.setScore(1, game.getP1().getScore());
        panel.setScore(2, game.getP2().getScore());

        // Set up the initial board
        for (Move m : game.getGameHistory().getMoveHistory()) {
            boardState[m.getCoordinate().getRowLocation()][m.getCoordinate().getColLocation()]
                    .setColor(m.getPlayer().getColor() == -1 ? BLACK : WHITE);
        }

        fAnimator = null;
        this.addMouseListener(this);
    }

    public void setBoardColor(Color color) {
        this.setBackground(color);
    }

    public Color getBoardColor() {
        return this.getBackground();
    }

    public PlayerInfoPanel getPlayerInfoPanel() {
        return panel;
    }

    /**
     * Draws the board.
     *
     * @param g Graphics context
     */
    @Override
    public void paintComponent( Graphics g )
    {
        super.paintComponent(g);

        int w = this.getWidth();
        int h = this.getHeight();

        float cellSize = (float)w / size;

        for(int i = 1; i < size; i++ ) {
            int pos = Math.round( i * cellSize );
            g.drawLine(pos, 0, pos, h );
            g.drawLine(0, pos, w, pos);
        }

        for( int row = 0; row< size; row++ ) {
            for( int col = 0; col < size; col++ ) {
                boardState[row][col].draw(g, cellSize);
            }
        }
    }

    /**
     * Mouse clicked event.  Determines the cell where the mouse
     * was clicked and prints the row/column to the console.
     *
     * @param e
     */
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        int w = this.getWidth();
        int h = this.getHeight();

        float cellSize = (float)w / size;

        int cellRow = (int)Math.floor( y / cellSize );
        int cellCol = (int)Math.floor( x / cellSize );
        int color = game.getCurrentPlayerColor();
        System.out.printf("Cell row = %d col = %d PlayerColor = %d\n", cellRow, cellCol, color);

        int row, col = 0;
        Coordinate c;
        displayAvailableMoves(false);
        //Get it to animate
        ArrayList<Coordinate> flips= game.move(game.getCurrentPlayer(), cellRow, cellCol);
        //
        if(flips != null) {
            if (color == 1) { //if color is white
                //
                for (int i = 0; i < flips.size(); i++) {
                    row = flips.get(i).getRowLocation();
                    col = flips.get(i).getColLocation();
                    c = game.adjustCoordForFlipping(cellRow,cellCol,row,col);
                    animateFlipSequence(cellRow, cellCol, c.getRowLocation(), c.getColLocation(), EMPTY, WHITE, 150);
                }
                displayAvailableMoves(activateMovesAvailable);
                displayBestMove(activateBestAvailable);
                panel.setActivePlayer(1); //Set the active player back to 1 (black)

            } else {
                //
                for (int i = 0; i < flips.size(); i++) {
                    row = flips.get(i).getRowLocation();
                    col = flips.get(i).getColLocation();
                    c = game.adjustCoordForFlipping(cellRow,cellCol,row,col);
                    animateFlipSequence(cellRow, cellCol, c.getRowLocation(), c.getColLocation(), EMPTY, BLACK, 150);
                }
                displayAvailableMoves(activateMovesAvailable);
                displayBestMove(activateBestAvailable);
                panel.setActivePlayer(2); //Set the active player back to 2 (white)
            }
            System.out.println("\n" + game.toString());
            panel.setScore(1, game.getP1().getScore());
            panel.setScore(2, game.getP2().getScore());

            game.isEndGame(gui);
        }
        else{
            System.out.println("Invalid move");
        }
    }
    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void animateComputerMove() {
        if (game.getCurrentPlayer() instanceof CPU) {

            displayAvailableMoves(false);
            displayBestMove(false);

            int cellRow, cellCol, row, col;
            Coordinate c;
            ArrayList<Coordinate> flips;


            flips = game.makeCPUMove();
            cellRow = flips.get(0).getRowLocation();
            cellCol = flips.get(0).getColLocation();

            for (int i = 1; i < flips.size(); i++) {
                row = flips.get(i).getRowLocation();
                col = flips.get(i).getColLocation();
                c = game.adjustCoordForFlipping(cellRow, cellCol, row, col);
                animateFlipSequence(cellRow, cellCol, c.getRowLocation(), c.getColLocation(), EMPTY, WHITE, 150);
            }
            System.out.println("\n" + game.toString());
            panel.setScore(1, game.getP1().getScore());
            panel.setScore(2, game.getP2().getScore());
            displayAvailableMoves(activateMovesAvailable);
            displayBestMove(activateBestAvailable);
            panel.setActivePlayer(1); //Set the active player back to 1 (black)

            game.isEndGame(gui);
        }
    }

    /**
     * Start an animation of a number of pieces being flipped over.
     *
     * @param startRow start row number
     * @param startCol start column number
     * @param endRow end row number
     * @param endCol end column number
     * @param start the color of the cells before flipping
     * @param end the color of the cells after flipping
     * @param milliseconds the time of the entire animation in milliseconds
     */
    public void animateFlipSequence(int startRow, int startCol,
                                    int endRow, int endCol,
                                    CellColor start, CellColor end,
                                    long milliseconds) {
        fAnimator = new FlipAnimator(startRow, startCol, endRow, endCol, start, end, milliseconds);
        fAnimator.start();
    }

    /**
     * Starts a new Game
     */
    public void newGame() {
        for(int i = 0; i <= 7; i++) {
            for(int j = 0; j <= 7; j++) {
                boardState[i][j].setColor(EMPTY);
            }
        }

        animateFlipSequence(3, 3, 3, 3, EMPTY, BLACK, 150);
        animateFlipSequence(3, 4, 3, 4, EMPTY, WHITE, 150);
        animateFlipSequence(4, 3, 4, 3, EMPTY, WHITE, 150);
        animateFlipSequence(4, 4, 4, 4, EMPTY, BLACK, 150);

        panel.setActivePlayer(1);
        panel.setScore(1, game.getP1().getScore());
        panel.setScore(2, game.getP2().getScore());
    }

    public void setMovesDisplay(boolean activateState) {
        activateMovesAvailable = activateState;
    }

    public void displayAvailableMoves(boolean activateDisplay) {

        int color = game.getCurrentPlayerColor();
        ArrayList<Coordinate> playersMoves = game.getBoard().getLegalMoves(color);
        if(activateDisplay) {
            //System.out.println("Display Activated...");
            for(Coordinate coord : playersMoves) {
                //System.out.println("Entered Coordinates: (" + coord.getColLocation()
                       // + "," + coord.getRowLocation() + ")" );
                boardState[coord.getRowLocation()][coord.getColLocation()].setColor(GRAY);
            }
        }
        else {
            //Else don't display any available options.
            //Display As Empty
            /**/for(Coordinate coord : playersMoves) {
               // System.out.println("Entered Coordinates: (" + coord.getColLocation()
                 //       + "," + coord.getRowLocation() + ")" );
                boardState[coord.getRowLocation()][coord.getColLocation()].setColor(EMPTY);
            }
        }
        repaint();
    }

    public void setBestDisplay(boolean activateState) {
        activateBestAvailable = activateState;
    }

    public void displayBestMove(boolean activateDisplay) {

        int color = game.getCurrentPlayerColor();
        ArrayList<Coordinate> playersMoves = game.getBoard().getLegalMoves(color);
        if (playersMoves.size() != 0) {
            BestMove best = new BestMove(game, playersMoves);
            if (activateDisplay) {
                boardState[best.getBest().getRowLocation()][best.getBest().getColLocation()].setColor(BLUE);
            } else
                boardState[best.getBest().getRowLocation()][best.getBest().getColLocation()].setColor(EMPTY);
            repaint();
        }
    }
}
