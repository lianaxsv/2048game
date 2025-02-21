package org.cis1200.Game2048;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javax.swing.JLabel;
import java.awt.event.*;

public class GameCourtTest {

    @Test
    public void testGameInitialization() {
        GameCourt gameCourt = new GameCourt(new JLabel());
        gameCourt.reset();
        assertNotNull(gameCourt.getGame());
    }

    @Test
    public void testGameReset() {
        GameCourt gameCourt = new GameCourt(new JLabel());
        gameCourt.reset();
        assertNotNull(gameCourt.getGame());
        assertEquals(gameCourt.getGame().getScore(), 0);
    }

    @Test
    public void testGameSaveAndLoad() {
        GameCourt gameCourt = new GameCourt(new JLabel());
        gameCourt.reset();
        gameCourt.save();

        GameBoard originalGame = gameCourt.getGame();
        gameCourt.load();
        GameBoard loadedGame = gameCourt.getGame();

        assertNotNull(loadedGame);
        assertEquals(originalGame.getScore(), loadedGame.getScore());
    }

    @Test
    public void testGameUndo() {
        GameCourt gameCourt = new GameCourt(new JLabel());
        gameCourt.reset();
        int originalScore = gameCourt.getGame().getScore();
        gameCourt.undo();
        assertEquals(originalScore, gameCourt.getGame().getScore());
    }

    private KeyEvent createKeyEvent(int keyCode) {
        return new KeyEvent(
                new JLabel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, keyCode, ' '
        );
    }

    @Test
    public void testMoveUpAndMerge() {
        GameCourt gameCourt = new GameCourt(new JLabel());
        gameCourt.reset();
        GameBoard game = gameCourt.getGame();
        game.getBoard()[0][0].setValue(2);
        game.getBoard()[1][0].setValue(2);

        gameCourt.keyPressed(createKeyEvent(KeyEvent.VK_UP));
        assertEquals(4, game.getBoard()[0][0].getValue());
        assertEquals(0, game.getBoard()[1][0].getValue());
    }

    @Test
    public void testGameNotOver() {
        GameCourt gameCourt = new GameCourt(new JLabel());
        gameCourt.reset();

        assertFalse(gameCourt.getGame().gameOver());

        gameCourt.keyPressed(createKeyEvent(KeyEvent.VK_UP));

        assertFalse(gameCourt.getGame().gameOver());
    }
}
