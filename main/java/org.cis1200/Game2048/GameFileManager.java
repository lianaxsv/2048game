package org.cis1200.Game2048;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

class GameFileManager {
    private static final String SAVE_FILE_PATH = "game_state.ser";

    public static void saveGameState(GameBoard game) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(SAVE_FILE_PATH)
        )) {
            oos.writeObject(game);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameBoard loadGameState() {
        GameBoard loadedGame = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE_PATH))) {
            loadedGame = (GameBoard) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loadedGame;
    }
}
