package plu.blue.reversi.client.firebase.listeners;

/**
 * Interface for receiving chat updates from an online game
 * @author Adam Grieger
 */
public interface FirebaseChatListener {

    /**
     * Called every time a chat message is added to an online game
     * @param playerName the name of the player who sent the chat message
     * @param message the contents of the chat message sent by the player
     */
    void onMessageAdded(String playerName, String message);

}
