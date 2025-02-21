package org.cis1200.Game2048;

import java.awt.Color;
import java.io.Serializable;

/**
 * This is the basic class that represents a tile in the 2048 game.
 * It gives the tile a value and a corresponding color to the value
 * The tile has the ability to merge with other tiles with the same
 * value as long as they are in the same row or column without any
 * other tile in between them.
 */
public class Tile implements Serializable {
    private int value;
    private Color color;

    /**
     * Constructs a tile with a default value of 0 and sets its color.
     */
    public Tile() {
        this.value = 0;
        setColor();
    }

    /**
     * Constructs a tile with a specified value and sets its color.
     *
     * @param value The value of the tile.
     */
    public Tile(int value) {
        this.value = value;
        setColor();
    }

    /**
     * Copy constructor that creates a new Tile object with the same properties as
     * the existing one.
     * Useful for the undo method.
     *
     * @param other The tile to be copied.
     */
    public Tile(Tile other) {
        this.value = other.value;
        this.color = other.color;
    }

    /**
     * Gets the value of the tile
     *
     * @return Returns the value of the tile
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of the tile to a diff value
     *
     * @param value Value that the tile should be set to
     */
    public void setValue(int value) {
        this.value = value;
        setColor();
    }

    /**
     * Converts the tile's value to a string.
     *
     * @return A string representation of the tile's value.
     */
    public String toString() {
        return "" + value;
    }

    /**
     * Gets the color associated with the tile's value.
     *
     * @return The color of the tile based on its value.
     */

    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the tile based on its value.
     * Uses a switch statement to determine the color for different values.
     */
    public void setColor() {
        switch (value) {
            case 2:
                color = new Color(255, 203, 242);
                break;
            case 4:
                color = new Color(243, 196, 251);
                break;
            case 8:
                color = new Color(236, 188, 253);
                break;
            case 16:
                color = new Color(229, 179, 254);
                break;
            case 32:
                color = new Color(226, 175, 255);
                break;
            case 64:
                color = new Color(222, 170, 255);
                break;
            case 128:
                color = new Color(216, 187, 255);
                break;
            case 256:
                color = new Color(208, 209, 255);
                break;
            case 512:
                color = new Color(200, 231, 255);
                break;
            case 1024:
                color = new Color(192, 253, 255);
                break;
            default:
                color = new Color(192, 255, 238);
                break;
        }
    }
}
