package org.cis1200.Game2048;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;

public class TileTest {

    @Test
    public void testDefaultValue() {
        Tile tile = new Tile();
        assertEquals(0, tile.getValue());
        assertEquals(new Color(192, 255, 238), tile.getColor());
    }

    @Test
    public void testCustomValue() {
        Tile tile = new Tile(8);
        assertEquals(8, tile.getValue());
        assertEquals(new Color(236, 188, 253), tile.getColor());
    }

    @Test
    public void testSetValue() {
        Tile tile = new Tile();
        tile.setValue(16);
        assertEquals(16, tile.getValue());
        assertEquals(new Color(229, 179, 254), tile.getColor());
    }

    @Test
    public void testToStrings() {
        Tile tile = new Tile(32);
        assertEquals("32", tile.toString());
    }

    @Test
    public void testCopyConstructors() {
        Tile originalTile = new Tile(64);
        Tile copiedTile = new Tile(originalTile);
        assertEquals(originalTile.getValue(), copiedTile.getValue());
        assertEquals(originalTile.getColor(), copiedTile.getColor());
    }

    @Test
    public void testDefaultValues() {
        Tile tile = new Tile();
        assertEquals(0, tile.getValue());
        assertEquals(new Color(192, 255, 238), tile.getColor());
    }

    @Test
    public void testCustomValues() {
        Tile tile = new Tile(8);
        assertEquals(8, tile.getValue());
        assertEquals(new Color(236, 188, 253), tile.getColor());
    }

    @Test
    public void testSetValues() {
        Tile tile = new Tile();
        tile.setValue(16);
        assertEquals(16, tile.getValue());
        assertEquals(new Color(229, 179, 254), tile.getColor());
    }

    @Test
    public void testToString() {
        Tile tile = new Tile(32);
        assertEquals("32", tile.toString());
    }

    @Test
    public void testCopyConstructor() {
        Tile originalTile = new Tile(64);
        Tile copiedTile = new Tile(originalTile);
        assertEquals(originalTile.getValue(), copiedTile.getValue());
        assertEquals(originalTile.getColor(), copiedTile.getColor());
    }

    @Test
    public void testSetColorWithNull() {
        Tile tile = new Tile();
        tile.setColor();
        assertEquals(new Color(192, 255, 238), tile.getColor());
    }

    @Test
    public void testNegativeValue() {
        Tile tile = new Tile(-5); // Negative value
        assertEquals(-5, tile.getValue());
        assertEquals(new Color(192, 255, 238), tile.getColor()); // Should use default color for
                                                                 // negative values
    }
}