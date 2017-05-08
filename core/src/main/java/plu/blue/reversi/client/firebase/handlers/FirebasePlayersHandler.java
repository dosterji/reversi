package plu.blue.reversi.client.firebase.handlers;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import plu.blue.reversi.client.firebase.FirebaseConnection;
import plu.blue.reversi.client.firebase.listeners.FirebasePlayersListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for handling player data changes for online games
 * @author Adam Grieger
 */
public class FirebasePlayersHandler {

    // Keeps track of which games registered FirebasePlayersListeners are listening to
    private HashMap<String, ArrayList<FirebasePlayersListener>> playersListeners;

    // Keeps tracks of ValueEventListeners that have been used to watch for database changes
    // Needed in order to delete the listeners at a later time
    private HashMap<String, ArrayList<ValueEventListener>> eventListeners;

    /**
     * Constructor for FirebasePlayersHandler
     */
    public FirebasePlayersHandler() {
        playersListeners = new HashMap<>();
        eventListeners = new HashMap<>();
    }

    /**
     * Registers a FirebasePlayersListener to receive player updates from an online game
     * @param gameName the name of the online game to listen to
     * @param lis the FirebasePlayersListener to register
     */
    public void addListener(String gameName, FirebasePlayersListener lis) {
        if (!playersListeners.containsKey(gameName)) {
            // Create new ArrayList if no listener has been registered for the online game
            ArrayList<FirebasePlayersListener> listeners = new ArrayList<>();
            listeners.add(lis);
            playersListeners.put(gameName, listeners);
            watchGame(gameName);
        } else {
            // Add the listener to the online game if at least one other listener has been registered for it
            ArrayList<FirebasePlayersListener> listeners = playersListeners.get(gameName);
            listeners.add(lis);
            playersListeners.put(gameName, listeners);
        }
    }

    /**
     * Removes all FirebasePlayersListeners and ValueEventListeners from an online game.
     * This method is particularly useful for wiping a FirebaseConnection of its listeners before starting
     * a new online game.
     * @param gameName the name of the online game to remove listeners from
     */
    public void removeListeners(String gameName) {
        FirebaseConnection conn = FirebaseConnection.getInstance();
        playersListeners.remove(gameName);

        // Remove ValueEventListener from reference to the name of player one
        DatabaseReference ref = conn.getDatabase().getReference("games/" + gameName + "/playerOne/name");
        ref.removeEventListener(eventListeners.get(gameName).get(0));

        // Remove ValueEventListener from reference to the name of player two
        ref = conn.getDatabase().getReference("games/" + gameName + "/playerTwo/name");
        ref.removeEventListener(eventListeners.get(gameName).get(1));

        // Remove ValueEventListener from reference to the score of player one
        ref = conn.getDatabase().getReference("games/" + gameName + "/playerOne/score");
        ref.removeEventListener(eventListeners.get(gameName).get(2));

        // Remove ValueEventListener from reference to the score of player two
        ref = conn.getDatabase().getReference("games/" + gameName + "/playerTwo/score");
        ref.removeEventListener(eventListeners.get(gameName).get(3));

        eventListeners.remove(gameName);
    }

    /**
     * Attaches a ValueEventListener to each database reference that listens for changes to players
     * @param gameName the name of the online game to attach ChildEventListeners to
     */
    private void watchGame(String gameName) {
        watchPlayerOneName(gameName);
        watchPlayerTwoName(gameName);
        watchPlayerOneScore(gameName);
        watchPlayerTwoScore(gameName);
    }

    /**
     * Attaches a ValueEventListener to the database that listens for changes to the name of player one
     * @param gameName the name of the online game to attach a ValueEventListener to
     */
    private void watchPlayerOneName(String gameName) {
        FirebaseConnection conn = FirebaseConnection.getInstance();
        DatabaseReference ref = conn.getDatabase().getReference("games/" + gameName + "/playerOne/name");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String playerOneName = (String) dataSnapshot.getValue();

                for (FirebasePlayersListener lis : playersListeners.get(gameName)) {
                    lis.onPlayerOneNameChanged(playerOneName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        };

        // Store event listener and add it to the database reference
        if (!eventListeners.containsKey(gameName)) {
            ArrayList<ValueEventListener> listeners = new ArrayList<>();
            listeners.add(listener);
            eventListeners.put(gameName, listeners);
        } else {
            eventListeners.get(gameName).add(listener);
        }

        ref.addValueEventListener(listener);
    }

    /**
     * Attaches a ValueEventListener to the database that listens for changes to the name of player two
     * @param gameName the name of the online game to attach a ValueEventListener to
     */
    private void watchPlayerTwoName(String gameName) {
        FirebaseConnection conn = FirebaseConnection.getInstance();
        DatabaseReference ref = conn.getDatabase().getReference("games/" + gameName + "/playerTwo/name");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String playerTwoName = (String) dataSnapshot.getValue();

                for (FirebasePlayersListener lis : playersListeners.get(gameName)) {
                    lis.onPlayerTwoNameChanged(playerTwoName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        };

        // Store event listener and add it to the database reference
        if (!eventListeners.containsKey(gameName)) {
            ArrayList<ValueEventListener> listeners = new ArrayList<>();
            listeners.add(listener);
            eventListeners.put(gameName, listeners);
        } else {
            eventListeners.get(gameName).add(listener);
        }

        ref.addValueEventListener(listener);
    }

    /**
     * Attaches a ValueEventListener to the database that listens for changes to the score of player one
     * @param gameName the name of the online game to attach a ValueEventListener to
     */
    private void watchPlayerOneScore(String gameName) {
        FirebaseConnection conn = FirebaseConnection.getInstance();
        DatabaseReference ref = conn.getDatabase().getReference("games/" + gameName + "/playerOne/score");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int playerOneScore = (int) dataSnapshot.getValue();

                for (FirebasePlayersListener lis : playersListeners.get(gameName)) {
                    lis.onPlayerOneScoreChanged(playerOneScore);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        };

        // Store event listener and add it to the database reference
        if (!eventListeners.containsKey(gameName)) {
            ArrayList<ValueEventListener> listeners = new ArrayList<>();
            listeners.add(listener);
            eventListeners.put(gameName, listeners);
        } else {
            eventListeners.get(gameName).add(listener);
        }

        ref.addValueEventListener(listener);
    }

    /**
     * Attaches a ValueEventListener to the database that listens for changes to the score of player two
     * @param gameName the name of the online game to attach a ValueEventListener to
     */
    private void watchPlayerTwoScore(String gameName) {
        FirebaseConnection conn = FirebaseConnection.getInstance();
        DatabaseReference ref = conn.getDatabase().getReference("games/" + gameName + "playerTwo/score");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int playerTwoScore = (int) dataSnapshot.getValue();

                for (FirebasePlayersListener lis : playersListeners.get(gameName)) {
                    lis.onPlayerTwoScoreChanged(playerTwoScore);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        };

        // Store event listener and add it to the database reference
        if (!eventListeners.containsKey(gameName)) {
            ArrayList<ValueEventListener> listeners = new ArrayList<>();
            listeners.add(listener);
            eventListeners.put(gameName, listeners);
        } else {
            eventListeners.get(gameName).add(listener);
        }

        ref.addValueEventListener(listener);
    }

}
