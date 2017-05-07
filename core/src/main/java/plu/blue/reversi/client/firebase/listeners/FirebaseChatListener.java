package plu.blue.reversi.client.firebase.listeners;

/**
 * Interface for receiving chat updates from an online game
 * @author Adam Grieger
 */
public interface FirebaseChatListener {

    /**
     * Called every time a chat message is added to an online game
     * @param playerName the name of the player who added the message
     * @param message the chat message sent by the player
     */
    void onMessageAdded(String playerName, String message);

}
