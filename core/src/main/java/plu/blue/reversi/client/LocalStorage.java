package plu.blue.reversi.client;

import java.io.*;
import java.util.HashMap;

/**
 * Class that stores saved games and manages save files
 * @author Adam Grieger
 */
public class LocalStorage {

    private HashMap<String, ReversiGame> savedGames;

    /**
     * LocalStorage constructor
     */
    public LocalStorage() {
        savedGames = new HashMap<>();
        preloadGames();
    }

    /**
     * Loads local save games into memory for easy retrieval
     */
    @SuppressWarnings("unchecked")
    private void preloadGames() {
        File saveFile = new File("savedGames.ser");

        try {
            if (saveFile.createNewFile()) {
                System.out.println("savedGames.ser created");
            } else {
                FileInputStream fileIn = new FileInputStream(saveFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);

                savedGames = (HashMap<String, ReversiGame>) in.readObject();

                fileIn.close();
                in.close();
            }
        } catch (EOFException e) {
            System.out.println("No saved games found");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves a GameHistory to a flat file with a given save name
     * @param saveName the name given to the saved game
     * @param game the ReversiGame to save
     */
    public void saveGame(String saveName, ReversiGame game) {
        savedGames.put(saveName, game);

        File saveFile = new File("savedGames.ser");

        // Saves list of saved games to local serialized file
        try {
            if (saveFile.createNewFile()) {
                System.out.println("savedGames.ser created");
            } else {
                FileOutputStream fileOut = new FileOutputStream(saveFile);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);

                out.writeObject(savedGames);

                fileOut.close();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object[] getSavedGameNames() {
        return savedGames.keySet().toArray();
    }

    public ReversiGame getSavedGame(String saveName) {
        return savedGames.get(saveName);
    }

}
