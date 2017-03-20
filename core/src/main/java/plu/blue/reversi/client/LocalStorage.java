package plu.blue.reversi.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Class that stores saved games and manages save files
 * @author Adam Grieger
 */
public class LocalStorage {

    // Field declarations
    private HashMap<String, GameHistory> savedGames;

    /**
     * LocalStorage constructor
     */
    public LocalStorage() {
        savedGames = new HashMap<>();
    }

    /**
     * Saves a GameHistory to a flat file with a given save name
     * @param saveName the name given to the saved game
     * @param game the GameHistory to save
     */
    public void saveGame(String saveName, GameHistory game) {
        savedGames.put(saveName, game);

        File saveFile = new File("src/main/resources/savedGames.ser");

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

}
