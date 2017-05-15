package plu.blue.reversi.client.firebase.handlers;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import plu.blue.reversi.client.firebase.FirebaseConnection;
import plu.blue.reversi.client.firebase.listeners.FirebaseMoveHistoryListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for handling move history changes for online games
 * @author Adam Grieger
 */
public class FirebaseMoveHistoryHandler {

    // Keeps track of which games registered FirebaseMoveHistoryListeners are listening to
    private HashMap<String, ArrayList<FirebaseMoveHistoryListener>> moveHistoryListeners;

    // Keeps track of ChildEventListeners that have been used to watch for database changes
    // Needed in order to delete the listeners at a later time
    private HashMap<String, ChildEventListener> eventListeners;

    /**
     * Constructor for FirebaseMoveHistoryHandler
     */
    public FirebaseMoveHistoryHandler() {
        moveHistoryListeners = new HashMap<>();
        eventListeners = new HashMap<>();
    }

    /**
     * Private data class for easily storing and retrieving move information
     */
    private class FirebaseMove {

        public int row, col, player;

        public FirebaseMove(int row, int col, int player) {
            this.row = row;
            this.col = col;
            this.player = player;
        }

    }

    /**
     * Registers a FirebaseMoveHistoryListener to receive move updates from an online game
     * @param gameName the name of the online game to listen to
     * @param lis the FirebaseMoveHistoryListener to register
     */
    public void addListener(String gameName, FirebaseMoveHistoryListener lis) {
        if (!moveHistoryListeners.containsKey(gameName)) {
            // Create a new ArrayList if no listener has been registered for the online game
            ArrayList<FirebaseMoveHistoryListener> listeners = new ArrayList<>();
            listeners.add(lis);
            moveHistoryListeners.put(gameName, listeners);
            watchGame(gameName);
        } else {
            // Add the listener to the online game if at least one other listener has been registered for it
            ArrayList<FirebaseMoveHistoryListener> listeners = moveHistoryListeners.get(gameName);
            listeners.add(lis);
            moveHistoryListeners.put(gameName, listeners);
        }
    }

    /**
     * Removes all FirebaseMoveHistoryListeners and ChildEventListeners from an online game.
     * This method is particularly useful for wiping a FirebaseConnection of its listeners before starting
     * a new online game.
     * @param gameName the name of the online game to remove listeners from
     */
    public void removeListeners(String gameName) {
        moveHistoryListeners.remove(gameName);
        FirebaseConnection conn = FirebaseConnection.getInstance();
        DatabaseReference ref = conn.getDatabase().getReference("games/" + gameName + "/moveHistory");
        ref.removeEventListener(eventListeners.get(gameName));
        eventListeners.remove(gameName);
    }

    /**
     * Adds a move to the move history of an online game
     * @param gameName the name of the online game to add a move to
     * @param row the row index of the move
     * @param col the column index of the move
     * @param player the player who made the move
     */
    public void addMove(String gameName, int row, int col, int player) {
        FirebaseConnection conn = FirebaseConnection.getInstance();
        DatabaseReference ref = conn.getDatabase().getReference("games/" + gameName + "/moveHistory");
        FirebaseMove move = new FirebaseMove(row, col, player);
        DatabaseReference newRef = ref.push();
        newRef.setValue(move);
    }

    /**
     * Attaches a ChildEventListener to the database that listens for changes to the move history
     * @param gameName the name of the online game to attach a ChildEventListener to
     */
    private void watchGame(String gameName) {
        FirebaseConnection conn = FirebaseConnection.getInstance();
        DatabaseReference ref = conn.getDatabase().getReference("games/" + gameName + "/moveHistory");
        ChildEventListener eventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseMove move = dataSnapshot.getValue(FirebaseMove.class);

                for (FirebaseMoveHistoryListener lis : moveHistoryListeners.get(gameName)) {
                    lis.onMoveAdded(move.row, move.col, move.player);
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
