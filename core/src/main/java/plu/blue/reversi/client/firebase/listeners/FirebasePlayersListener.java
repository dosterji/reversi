package plu.blue.reversi.client.firebase.listeners;

/**
 * Interface for receiving player updates from an online game
 * @author Adam Grieger
 */
public interface FirebasePlayersListener {

    /**
     * Called every time the name for player one changes in an online game
     * @param playerOneName the new name for player one
     */
    void onPlayerOneNameChanged(String playerOneName);

    /**
     * Called every time the name for player two changes in an online game
     * @param playerTwoName the new name for player two
     */
    void onPlayerTwoNameChanged(String playerTwoName);

    /**
     * Called every time the score for player one changes in an online game
     * @param playerOneScore the new score for player one
     */
    void onPlayerOneScoreChanged(int playerOneScore);

    /**
     * Called every time the score for player two changes in an online game
     * @param playerTwoScore the new score for player two
     */
    void onPlayerTwoScoreChanged(int playerTwoScore);

}
