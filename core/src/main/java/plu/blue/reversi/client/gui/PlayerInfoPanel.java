package plu.blue.reversi.client.gui;

import plu.blue.reversi.client.firebase.listeners.FirebaseCurrentPlayerListener;
import plu.blue.reversi.client.firebase.listeners.FirebasePlayersListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PlayerInfoPanel extends JPanel implements FirebaseCurrentPlayerListener, FirebasePlayersListener {

    private Border activeBorder;
    private Border inactiveBorder;
    private JPanel player1Panel, player2Panel;
    private JLabel player1NameLabel, player2NameLabel;
    private ScoreIcon player1Score, player2Score;
    private Color activeBackgroundColor = new Color(180, 250, 180);
    private Color Player1Color = Color.BLACK;
    private Color Player2Color = Color.WHITE;

    public PlayerInfoPanel() {

        final int BORDER_SIZE = 5;

        inactiveBorder = BorderFactory.createMatteBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE,
                new Color(0,0,0,0));
        activeBorder = BorderFactory.createMatteBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE,
                new Color(250,200,100));

        this.setLayout(new BorderLayout());
        player2Score = new ScoreIcon(Player2Color, Color.LIGHT_GRAY);
        player1Score = new ScoreIcon(Player1Color, Color.LIGHT_GRAY);

        player1NameLabel = new JLabel();
        player2NameLabel = new JLabel();

        player1Panel = new JPanel();
        player1Panel.add(player1Score);
        player1Panel.add(player1NameLabel);

        player2Panel = new JPanel();
        player2Panel.add(player2Score);
        player2Panel.add(player2NameLabel);

        setActivePlayer(1);

        this.add(player1Panel, BorderLayout.WEST);
        this.add(player2Panel, BorderLayout.EAST);
    }

    public void setPlayer1Color(Color c) {
        player1Score.setChipColor(c);
        repaint();
    }

    public void setPlayer2Color(Color c) {
        player2Score.setChipColor(c);
        repaint();
    }

    public void setActivePlayer( int player ){
        if( player == 1 ) {
            player1Panel.setBackground(activeBackgroundColor);
            player2Panel.setBackground(null);
            player1Panel.setBorder(activeBorder);
            player2Panel.setBorder(inactiveBorder);
        } else if (player == 2 ) {
            player2Panel.setBackground(activeBackgroundColor);
            player1Panel.setBackground(null);
            player1Panel.setBorder(inactiveBorder);
            player2Panel.setBorder(activeBorder);
        } else {
            throw new IllegalArgumentException("Invalid player: " + player);
        }
    }

    public void setScore( int player, int score ) {
        if( player == 1 ) {
            player1Score.setScoreValue(score);
        } else if (player == 2 ) {
            player2Score.setScoreValue(score);
        } else {
            throw new IllegalArgumentException("Invalid player: " + player);
        }
    }

    public void setPlayerName(int player, String name) {
        if( player == 1 ) {
            player1NameLabel.setText(name);
        } else if (player == 2 ) {
            player2NameLabel.setText(name);
        } else {
            throw new IllegalArgumentException("Invalid player: " + player);
        }
    }
    public String getPlayerName(int player){
        if(player == 1)
            return player1NameLabel.getText();
        else
            return player2NameLabel.getText();
    }

    @Override
    public void onCurrentPlayerChanged(int currentPlayer) {
        setActivePlayer(currentPlayer);
    }

    @Override
    public void onPlayerOneNameChanged(String playerOneName) {
        setPlayerName(1, playerOneName);
    }

    @Override
    public void onPlayerTwoNameChanged(String playerTwoName) {
        setPlayerName(2, playerTwoName);
    }

    @Override
    public void onPlayerOneScoreChanged(int playerOneScore) {
        setScore(1, playerOneScore);
    }

    @Override
    public void onPlayerTwoScoreChanged(int playerTwoScore) {
        setScore(2, playerTwoScore);
    }

}
