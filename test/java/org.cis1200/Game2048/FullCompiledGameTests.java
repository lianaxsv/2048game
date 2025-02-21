package org.cis1200.Game2048;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javax.swing.JLabel;
import java.awt.event.*;
import java.awt.Color;

public class FullCompiledGameTests {

    @Test
    public void testInitialTileValues() {
        Tile tile1 = new Tile();
        assertEquals(0, tile1.getValue());

        Tile tile2 = new Tile(4);
        assertEquals(4, tile2.getValue());
    }

    @Test
    public void testTileSetValue() {
        Tile tile = new Tile();
        tile.setValue(8);
        assertEquals(8, tile.getValue());
    }

    @Test
    public void testTileToString() {
        Tile tile = new Tile(16);
        assertEquals("16", tile.toString());
    }

    @Test
    public void testTileColor() {
        Tile tile1 = new Tile(2);
        assertEquals(new Color(255, 203, 242), tile1.getColor());

        Tile tile2 = new Tile(64);
        assertEquals(new Color(222, 170, 255), tile2.getColor());
    }

    @Test
    public void testGameBoardInitialGameState() {
        GameBoard gameBoard = new GameBoard();
        assertEquals(0, gameBoard.getScore());
        assertEquals(0, gameBoard.getHighestTile());

        Tile[][] board = gameBoard.getBoard();
        assertNotNull(board);
        assertEquals(4, board.length);
        assertEquals(4, board[0].length);

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                assertEquals(0, board[i][j].getValue());
            }
        }
    }

    @Test
    public void testGameBoardSpawn2or4() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.moveUp(); // Creating open space
        gameBoard.spawn2or4();

        boolean validSpawn = false;
        Tile[][] newBoard = gameBoard.getBoard();
        for (int i = 0; i < newBoard.length; i++) {
            for (int j = 0; j < newBoard[i].length; j++) {
                if (newBoard[i][j].getValue() == 2 || newBoard[i][j].getValue() == 4) {
                    validSpawn = true;
                }
            }
        }
        assertTrue(validSpawn);
    }

    @Test
    public void testGameCourtReset() {
        GameCourt gameCourt = new GameCourt(new JLabel());
        gameCourt.reset();
        assertNotNull(gameCourt.getGame());
    }

    @Test
    public void testGameCourtSaveAndLoad() {
        GameCourt gameCourt = new GameCourt(new JLabel());
        gameCourt.reset();
        GameBoard originalGame = gameCourt.getGame();

        gameCourt.save();
        gameCourt.reset();
        gameCourt.load();

        GameBoard loadedGame = gameCourt.getGame();
        assertNotNull(loadedGame);
        assertEquals(originalGame.getScore(), loadedGame.getScore());
    }

    @Test
    public void testGameCourtUndo() {
        GameCourt gameCourt = new GameCourt(new JLabel());
        gameCourt.reset();
        gameCourt.getGame().moveUp();
        gameCourt.undo();
    }

    @Test
    public void testGameCourtKeyUndo() {
        GameCourt gameCourt = new GameCourt(new JLabel());
        gameCourt.reset();

        gameCourt.keyPressed(createKeyEvent(KeyEvent.VK_UP));
        GameBoard originalGame = gameCourt.getGame();

        gameCourt.keyPressed(createKeyEvent(KeyEvent.VK_LEFT));
        gameCourt.keyPressed(createKeyEvent(KeyEvent.VK_U));

        GameBoard afterUndoGame = gameCourt.getGame();
        assertNotNull(afterUndoGame);
        assertEquals(originalGame.getScore(), afterUndoGame.getScore());
    }

    @Test
    public void testGameCourtKeySaveAndLoad() {
        GameCourt gameCourt = new GameCourt(new JLabel());
        gameCourt.reset();

        gameCourt.keyPressed(createKeyEvent(KeyEvent.VK_UP));
        GameBoard originalGame = gameCourt.getGame();

        gameCourt.keyPressed(createKeyEvent(KeyEvent.VK_S));
        gameCourt.keyPressed(createKeyEvent(KeyEvent.VK_L));

        GameBoard loadedGame = gameCourt.getGame();
        assertNotNull(loadedGame);
        assertEquals(originalGame.getScore(), loadedGame.getScore());
    }

    @Test
    public void testGameBoardMergeTiles() {
        GameBoard gameBoard = new GameBoard();

        gameBoard.getBoard()[0][0].setValue(2);
        gameBoard.getBoard()[0][1].setValue(2);

        gameBoard.moveRight();
        assertEquals(4, gameBoard.getBoard()[0][3].getValue());
    }

    @Test
    public void testGameBoardSpawn2or4MultipleTimes() {
        GameBoard gameBoard = new GameBoard();

        gameBoard.spawn2or4();
        gameBoard.spawn2or4();
    }

    @Test
    public void testGameBoardScoreAfterMerge() {
        GameBoard gameBoard = new GameBoard();

        gameBoard.getBoard()[0][0].setValue(2);
        gameBoard.getBoard()[0][1].setValue(2);

        gameBoard.moveRight();
        assertEquals(4, gameBoard.getScore());
    }

    @Test
    public void testGameBoardSpawn2or4AfterMove() {
        GameBoard gameBoard = new GameBoard();

        gameBoard.moveUp();
        gameBoard.spawn2or4();

        boolean validSpawn = false;
        Tile[][] newBoard = gameBoard.getBoard();
        for (int i = 0; i < newBoard.length; i++) {
            for (int j = 0; j < newBoard[i].length; j++) {
                if (newBoard[i][j].getValue() == 2 || newBoard[i][j].getValue() == 4) {
                    validSpawn = true;
                }
            }
        }
        assertTrue(validSpawn);
    }

    @Test
    public void testGameCourtKeyPressEventsWithInvalidKey() {
        GameCourt gameCourt = new GameCourt(new JLabel());
        gameCourt.reset();

        gameCourt.keyPressed(createKeyEvent(KeyEvent.VK_X)); // Invalid key
    }

    @Test
    public void testGameCourtKeyPressEventsWithMultipleActions() {
        GameCourt gameCourt = new GameCourt(new JLabel());
        gameCourt.reset();

        gameCourt.keyPressed(createKeyEvent(KeyEvent.VK_UP));
        gameCourt.keyPressed(createKeyEvent(KeyEvent.VK_DOWN));
        gameCourt.keyPressed(createKeyEvent(KeyEvent.VK_LEFT));
        gameCourt.keyPressed(createKeyEvent(KeyEvent.VK_RIGHT));
    }

    @Test
    public void testGameBoardMoveDown() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.getBoard()[0][0].setValue(2);
        gameBoard.getBoard()[0][1].setValue(2);

        gameBoard.moveDown();
        assertEquals(4, gameBoard.getBoard()[3][0].getValue());
    }

    @Test
    public void testGameBoardMoveLeft() {
        GameBoard gameBoard = new GameBoard();

        gameBoard.getBoard()[0][1].setValue(2);
        gameBoard.getBoard()[1][1].setValue(2);

        gameBoard.moveLeft();
        assertEquals(4, gameBoard.getBoard()[1][0].getValue());
    }

    @Test
    public void testGameBoardMoveRight() {
        GameBoard gameBoard = new GameBoard();

        gameBoard.getBoard()[0][1].setValue(2);
        gameBoard.getBoard()[1][1].setValue(2);

        gameBoard.moveRight();
        assertEquals(4, gameBoard.getBoard()[1][3].getValue());
    }

    @Test
    public void testGameBoardGameOver() {
        GameBoard gameBoard = new GameBoard();

        gameBoard.getBoard()[0][0].setValue(2);
        gameBoard.getBoard()[0][1].setValue(4);
        gameBoard.getBoard()[0][2].setValue(8);
        gameBoard.getBoard()[0][3].setValue(16);
        gameBoard.getBoard()[1][0].setValue(32);
        gameBoard.getBoard()[1][1].setValue(64);
        gameBoard.getBoard()[1][2].setValue(128);
        gameBoard.getBoard()[1][3].setValue(256);
        gameBoard.getBoard()[2][0].setValue(512);
        gameBoard.getBoard()[2][1].setValue(1024);
        gameBoard.getBoard()[2][2].setValue(2048);
        gameBoard.getBoard()[2][3].setValue(4096);
        gameBoard.getBoard()[3][0].setValue(8192);
        gameBoard.getBoard()[3][1].setValue(16384);
        gameBoard.getBoard()[3][2].setValue(32768);
        gameBoard.getBoard()[3][3].setValue(65536);

        assertTrue(gameBoard.gameOver());
    }

    @Test
    public void testGameCourtKeyPressEventsForUndo() {
        GameCourt gameCourt = new GameCourt(new JLabel());
        gameCourt.reset();

        gameCourt.keyPressed(createKeyEvent(KeyEvent.VK_UP));
        gameCourt.keyPressed(createKeyEvent(KeyEvent.VK_DOWN));
        gameCourt.keyPressed(createKeyEvent(KeyEvent.VK_LEFT));

        gameCourt.keyPressed(createKeyEvent(KeyEvent.VK_U));
    }

    private KeyEvent createKeyEvent(int keyCode) {
        return new KeyEvent(
                new JLabel(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, keyCode,
                KeyEvent.CHAR_UNDEFINED
        );

    }

}
