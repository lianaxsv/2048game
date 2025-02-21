package org.cis1200.Game2048;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The GameCourt class represents the main playing area of the 2048 game. It
 * extends JPanel
 * and implements KeyListener to handle user input for game movements. The class
 * is responsible
 * for managing the game state, handling key events, and painting the game
 * board.
 */
public class GameCourt extends JPanel implements KeyListener {
    private static final String LOSE_IMAGE_PATH =
            "src/main/java/org/cis1200/Game2048/pictures/Lose.jpg";
    private GameBoard game;
    private boolean playing = false;
    private final JLabel status;

    /**
     * Constructs a GameCourt with the specified status label. It sets up the
     * panel's
     * properties, adds a KeyListener, and initializes the status label.
     *
     * @param status The JLabel used to display the game status.
     */
    public GameCourt(JLabel status) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);
        addKeyListener(this);

        this.status = status;
    }

    /**
     * Gets the current game instance.
     *
     * @return The current GameBoard instance.
     */
    public GameBoard getGame() {
        return game;
    }

    /**
     * Sets the current game instance.
     *
     * @param newGame The new GameBoard instance.
     */
    public void setGame(GameBoard newGame) {
        game = newGame;
        playing = true;
        status.setText("Running...");
        repaint();
    }

    /**
     * Resets the game state by creating a new GameBoard, spawning two tiles, and
     * setting
     * the playing status to true. It also updates the status label.
     */
    public void reset() {
        game = new GameBoard();
        game.spawn2or4();
        game.spawn2or4();
        playing = true;
        status.setText("Running...Press S to save and L to load.");
        requestFocusInWindow();
    }

    /**
     * Saves the current game state.
     */
    public void save() {
        GameFileManager.saveGameState(game);
        status.setText("Game state saved.");
    }

    /**
     * Loads a saved game state.
     */
    public void load() {
        GameBoard loadedGame = GameFileManager.loadGameState();
        if (loadedGame != null) {
            setGame(loadedGame);
            playing = true;
            status.setText("Game state loaded.");
        } else {
            status.setText("Unable to load game state.");
        }
    }

    /**
     * Undoes the last move by restoring the previous game state from the undo
     * stack.
     */
    public void undo() {
        game.restoreState();
    }

    /**
     * Handles key events to control game movements, undo moves, and reset the game.
     * Updates the status label accordingly and repaints the panel.
     *
     * @param e The KeyEvent representing the user's key press.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (playing) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                game.moveUp();
                game.spawn2or4();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                game.moveDown();
                game.spawn2or4();
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                game.moveLeft();
                game.spawn2or4();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                game.moveRight();
                game.spawn2or4();
            } else if (e.getKeyChar() == 'U' || e.getKeyChar() == 'u') {
                game.restoreState();
                status.setText("Undoing move...");
                repaint();
            } else if (e.getKeyChar() == 'L' || e.getKeyChar() == 'l') {
                load();
            } else if (e.getKeyChar() == 'S' || e.getKeyChar() == 's') {
                save();
            }

            status.setText(
                    "Score: " + game.getScore() + "   Highest Tile: " + game.getHighestTile()
            );
            repaint();

            if (game.gameOver()) {
                playing = false;
                status.setText("Game Over! Press the reset button to restart.");
            }
        }
    }

    /**
     * Not used in this class.
     *
     * @param e The KeyEvent representing a key release event.
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Not used in this class.
     *
     * @param e The KeyEvent representing a key typed event.
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Paints the game board by drawing the tiles on the panel.
     *
     * @param g The Graphics context used for painting.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (!playing) {
            drawLoseScreen(g2);
            return;
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                drawTiles(g2, game.getBoard()[i][j], j * 100 + 50, i * 100 + 50);
            }
        }
    }

    /**
     * Draws the lost screen with a "Lose.png" image.
     *
     * @param g2 The graphics context used for drawing.
     */
    private void drawLoseScreen(Graphics2D g2) {
        ImageIcon loseImageIcon = new ImageIcon(LOSE_IMAGE_PATH);
        Image loseImage = loseImageIcon.getImage();

        setPreferredSize(
                new Dimension(loseImageIcon.getIconWidth(), loseImageIcon.getIconHeight())
        );

        int x = (getWidth() - loseImageIcon.getIconWidth()) / 2;
        int y = (getHeight() - loseImageIcon.getIconHeight()) / 2;
        g2.drawImage(loseImage, x, y, this);

        revalidate();
    }

    /**
     * Draws a tile on the panel with its value and color.
     *
     * @param g2   The Graphics2D context used for drawing.
     * @param tile The Tile object to be drawn.
     * @param x    The x-coordinate of the tile's position.
     * @param y    The y-coordinate of the tile's position.
     */
    private void drawTiles(Graphics2D g2, Tile tile, int x, int y) {
        int tileValue = tile.getValue();
        int length = String.valueOf(tileValue).length();

        g2.setColor(Color.lightGray);
        g2.fillRoundRect(x, y, 80, 80, 10, 10);

        g2.setColor(Color.black);
        g2.setFont(new Font("Clear Sans", Font.PLAIN, 20));

        if (tileValue > 0) {
            g2.setColor(tile.getColor());
            g2.fillRoundRect(x, y, 80, 80, 10, 10);

            g2.setColor(Color.black);
            g2.drawString("" + tileValue, x + 40 - 3 * length, y + 40);
        }
    }

    /**
     * Gets the preferred size of the panel.
     *
     * @return The preferred size as a Dimension object.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 400);
    }
}