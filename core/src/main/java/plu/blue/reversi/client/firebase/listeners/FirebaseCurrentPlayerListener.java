package plu.blue.reversi.client.firebase.listeners;

/**
 * Interface for receiving current player updates from an online game
 * @author Adam Grieger
 */
public interface FirebaseCurrentPlayerListener {

    /**
     * Called every time the current player switches for an online game
     */
    void onCurrentPlayerChanged();

}
