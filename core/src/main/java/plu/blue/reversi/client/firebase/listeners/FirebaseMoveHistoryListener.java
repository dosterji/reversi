package plu.blue.reversi.client.firebase.listeners;

/**
 * Interface for receiving move updates from an online game
 * @author Adam Grieger
 */
public interface FirebaseMoveHistoryListener {

    /**
     * Called every time a move is added to an online game
     * @param row the row index of the move
     * @param col the column index of the move
     * @param player the player who made the move
     *               (1 if player one, 2 if player two)
     */
    void onMoveAdded(int row, int col, int player);

}
