package plu.blue.reversi.client.firebase.listeners;

/**
 * Interface for receiving chat updates from an online game
 * @author Adam Grieger
 */
public interface FirebaseChatListener {

    /**
     * Called every time a chat message is added to an online game
     * @param player player who added the message
     *               ("one" is player one, "two" if player two)
     * @param message the chat message sent by the player
     */
    void onMessageAdded(String player, String message);

}
