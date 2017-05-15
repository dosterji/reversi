package plu.blue.reversi.client.firebase.handlers;

import com.google.firebase.database.*;
import plu.blue.reversi.client.firebase.FirebaseConnection;
import plu.blue.reversi.client.firebase.listeners.FirebaseCurrentPlayerListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for handling current player data changes for online games
 * @author Adam Grieger
 */
public class FirebaseCurrentPlayerHandler {

    // Keeps track of which games registered FirebaseCurrentPlayerListeners are listening to
    private HashMap<String, ArrayList<FirebaseCurrentPlayerListener>> currentPlayerListeners;

    // Keeps track of ValueEventListeners that have been used to watch for database changes
    // Needed in order to delete the listeners at a later time
    private HashMap<String, ValueEventListener> eventListeners;

    /**
     * Constructor for FirebaseCurrentPlayerHandler
     */
    public FirebaseCurrentPlayerHandler() {
        currentPlayerListeners = new HashMap<>();
        eventListeners = new HashMap<>();
    }

    /**
     * Registers a FirebaseCurrentPlayerListener to receive current player updates from an online game
     * @param gameName the name of the online game to listen to
     * @param lis the FirebaseCurrentPlayerListener to register
     */
    public void addListener(String gameName, FirebaseCurrentPlayerListener lis) {
        if (!currentPlayerListeners.containsKey(gameName)) {
            // Create new ArrayList if no listener has been registered for the online game
            ArrayList<FirebaseCurrentPlayerListener> listeners = new ArrayList<>();
            listeners.add(lis);
            currentPlayerListeners.put(gameName, listeners);
            watchGame(gameName);
        } else {
            // Add the listener to the online game if at least one other listener has been registered for it already
            ArrayList<FirebaseCurrentPlayerListener> listeners = currentPlayerListeners.get(gameName);
            listeners.add(lis);
            currentPlayerListeners.put(gameName, listeners);
        }
    }

    /**
     * Removes all FirebaseCurrentPlayerListeners and ValueEventListeners from an online game.
     * This method is particularly useful for wiping a FirebaseConnection of its listeners before starting
     * a new online game.
     * @param gameName the name of the online game to remove listeners for
     */
    public void removeListeners(String gameName) {
        FirebaseConnection conn = FirebaseConnection.getInstance();
        currentPlayerListeners.remove(gameName);
        DatabaseReference ref = conn.getDatabase().getReference("games/" + gameName + "/currentPlayer");
        ref.removeEventListener(eventListeners.get(gameName));
        eventListeners.remove(gameName);
    }

    /**
     * Attaches a ValueEventListener to the database that listens for changes to the current player
     * @param gameName the name of the online game to attach a ValueEventListener to
     */
    private void watchGame(String gameName) {
        FirebaseConnection conn = FirebaseConnection.getInstance();
        DatabaseReference ref = conn.getDatabase().getReference("games/" + gameName + "/currentPlayer");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int currentPlayer = Math.toIntExact((long) dataSnapshot.getValue());
                for (FirebaseCurrentPlayerListener lis : currentPlayerListeners.get(gameName)) {
                    lis.onCurrentPlayerChanged(currentPlayer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        };

        // Store event listener and add it to the database reference
        eventListeners.put(gameName, eventListener);
        ref.addValueEventListener(eventListener);
    }

}
