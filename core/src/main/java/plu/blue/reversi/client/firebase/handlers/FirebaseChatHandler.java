package plu.blue.reversi.client.firebase.handlers;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import plu.blue.reversi.client.firebase.FirebaseConnection;
import plu.blue.reversi.client.firebase.listeners.FirebaseChatListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for handling chat messages for online games
 * @author Adam Grieger
 */
public class FirebaseChatHandler {

    // Keep track of which games registered FirebaseChatListeners are listening to
    private HashMap<String, ArrayList<FirebaseChatListener>> chatListeners;

    // Keeps track of ChildEventListeners that have been used to watch for database changes
    // Needed in order to delete the listeners at a later time
    private HashMap<String, ChildEventListener> eventListeners;

    /**
     * Constructor for FirebaseChatHandler
     */
    public FirebaseChatHandler() {
        chatListeners = new HashMap<>();
        eventListeners = new HashMap<>();
    }

    /**
     * Private data class for easily storing and retrieving message information
     */
    private class FirebaseMessage {

        public String playerName, message;

        public FirebaseMessage(String playerName, String message) {
            this.playerName = playerName;
            this.message = message;
        }

    }

    /**
     * Registers a FirebaseChatListener to receive chat updates from an online game
     * @param gameName the name of the online game to listen to
     * @param lis the FirebaseChatListener to register
     */
    public void addListener(String gameName, FirebaseChatListener lis) {
        if (!chatListeners.containsKey(gameName)) {
            // Create new ArrayList if no listener has been registered for the online game
            ArrayList<FirebaseChatListener> listeners = new ArrayList<>();
            listeners.add(lis);
            chatListeners.put(gameName, listeners);
            watchGame(gameName);
        } else {
            // Add the listener to the online game if at least one other listener has been registered for it
            ArrayList<FirebaseChatListener> listeners = chatListeners.get(gameName);
            listeners.add(lis);
            chatListeners.put(gameName, listeners);
        }
    }

    /**
     * Removes all FirebaseChatListeners and ChildEventListeners from an online game.
     * This method is particularly useful for wiping a FirebaseConnection of its listeners before starting
     * a new online game.
     * @param gameName the name of the online game to remove listeners from
     */
    public void removeListeners(String gameName) {
        chatListeners.remove(gameName);
        FirebaseConnection conn = FirebaseConnection.getInstance();
        DatabaseReference ref = conn.getDatabase().getReference("chat/" + gameName + "/messages");
        ref.removeEventListener(eventListeners.get(gameName));
        eventListeners.remove(gameName);
    }

    /**
     * Adds a message to the chat of an online game
     * @param gameName the name of the online game to add a chat message to
     * @param playerName the name of the player who sent the chat message
     * @param message the contents of the chat message sent by the player
     */
    public void addMessage(String gameName, String playerName, String message) {
        FirebaseConnection conn = FirebaseConnection.getInstance();
        DatabaseReference ref = conn.getDatabase().getReference("chat/" + gameName + "/messages");
        FirebaseMessage msg = new FirebaseMessage(playerName, message);
        DatabaseReference newRef = ref.push();
        newRef.setValue(msg);
    }

    /**
     * Attaches a ChildEventListener to the database that listens for changes to the chat
     * @param gameName the name of the online game to attach a ChildEventListener to
     */
    private void watchGame(String gameName) {
        FirebaseConnection conn = FirebaseConnection.getInstance();
        DatabaseReference ref = conn.getDatabase().getReference("chat/" + gameName + "/messages");
        ChildEventListener eventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseMessage msg = dataSnapshot.getValue(FirebaseMessage.class);

                for (FirebaseChatListener lis : chatListeners.get(gameName)) {
                    lis.onMessageAdded(msg.playerName, msg.message);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.getMessage();
            }
        };

        // Store event listener and add it to the database reference
        eventListeners.put(gameName, eventListener);
        ref.addChildEventListener(eventListener);
    }

}
