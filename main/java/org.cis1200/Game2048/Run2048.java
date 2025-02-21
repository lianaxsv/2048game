package org.cis1200.Game2048;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Run2048 implements Runnable {
    private static final String SAVE_FILE_PATH = "game_state.ser";
    private static final String START_IMAGE_PATH =
            "src/main/java/org/cis1200/Game2048/pictures/Start.png";
    private static final String LOSE_IMAGE_PATH =
            "src/main/java/org/cis1200/Game2048/pictures/Lose.jpg";

    public void run() {
        final JFrame frame = new JFrame("2048 Game");
        frame.setLocation(300, 300);

        JPanel introPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon(START_IMAGE_PATH);
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        introPanel.setLayout(new BoxLayout(introPanel, BoxLayout.Y_AXIS));

        JButton startButton = new JButton("Start");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.addActionListener(e -> {
            showGamePanel(frame);
        });
        introPanel.add(Box.createVerticalStrut(300));
        introPanel.add(startButton);

        frame.add(introPanel, BorderLayout.CENTER);

        final JPanel statusPanel = new JPanel();
        frame.add(statusPanel, BorderLayout.SOUTH);
        final JLabel statusLabel = new JLabel("Press S to save and L to load.");
        statusPanel.add(statusLabel);

        ImageIcon imageIcon = new ImageIcon(START_IMAGE_PATH);
        int imageWidth = imageIcon.getIconWidth();
        int imageHeight = imageIcon.getIconHeight();
        frame.setSize(imageWidth, imageHeight);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Shows the Instructions Window with relevant information.
    private void showInstructionsWindow() {
        JFrame instructionsFrame = new JFrame("Game Instructions");
        JTextArea instructionsText = new JTextArea();
        instructionsText.setEditable(false);
        instructionsText.setLineWrap(true);
        instructionsText.setWrapStyleWord(true);
        instructionsText.setText(
                "Welcome to Liana's 2048 Game!\n\n" +
                        "Instructions:\n" +
                        "Use the arrow keys to move tiles.\n" +
                        "Combine matching tiles to reach 2048.\n" +
                        "Press 'S' to save, 'L' to load, 'U' to undo.\n" +
                        "Click 'Reset' to start a new game.\n" +
                        "\nEnjoy playing!"
        );

        JScrollPane scrollPane = new JScrollPane(instructionsText);
        instructionsFrame.add(scrollPane);
        instructionsFrame.setSize(400, 300);
        instructionsFrame.setLocationRelativeTo(null);
        instructionsFrame.setVisible(true);
    }

    private void showGamePanel(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        // Status panel
        final JPanel statusPanel = new JPanel();
        frame.add(statusPanel, BorderLayout.SOUTH);
        final JLabel statusLabel = new JLabel("Running... Press S to save and L to load.");
        statusPanel.add(statusLabel);

        // Main playing area
        final GameCourt court = new GameCourt(statusLabel);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel controlPanel = new JPanel();
        frame.add(controlPanel, BorderLayout.NORTH);

        final JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            court.reset();
            statusLabel.setText("Running... Press S to save and L to load.");
            court.repaint();
            court.requestFocusInWindow();
        });
        controlPanel.add(resetButton);

        // Undo button
        final JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> {
            court.undo();
            statusLabel.setText("Undoing move...");
            court.repaint();
            court.requestFocusInWindow();
        });
        controlPanel.add(undoButton);

        // Save and Load buttons
        final JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            court.save();
            court.requestFocusInWindow();
        });
        controlPanel.add(saveButton);

        final JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> {
            court.load();
            court.requestFocusInWindow();
        });
        controlPanel.add(loadButton);

        // Instructions button
        final JButton instructionsButton = new JButton("Instructions");
        instructionsButton.addActionListener(e -> {
            showInstructionsWindow();
        });
        controlPanel.add(instructionsButton);

        frame.pack();
        frame.revalidate();
        frame.repaint();

        // Start game
        court.reset();
    }
}