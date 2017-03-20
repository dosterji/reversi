package plu.blue.reversi.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import plu.blue.reversi.client.GameHistory;
import plu.blue.reversi.client.LocalStorage;

import java.io.File;

/**
 * Class that tests LocalStorage methods
 * @author Adam Grieger
 */
public class LocalStorageTest {

    private LocalStorage ls;

    @Before
    public void init() {
        ls = new LocalStorage();
    }

    @Test
    public void testSaveGame() {
        ls.saveGame("Test save", new GameHistory());
        File saveFile = new File("src/main/resources/savedGames.ser");

        Assert.assertTrue(saveFile.exists());
        Assert.assertTrue(saveFile.delete());
    }

}
