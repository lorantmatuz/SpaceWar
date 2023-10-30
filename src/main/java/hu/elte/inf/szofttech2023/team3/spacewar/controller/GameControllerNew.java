package hu.elte.inf.szofttech2023.team3.spacewar.controller;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.GenerateSpace;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;

public class GameControllerNew {

    private static final int MAP_WIDTH = 20;
    private static final int MAP_HEIGHT = 20;

    private static final int NUM_OF_PLANETS = 10;
    private static final int NUM_OF_ASTEROIDS = 5;
    private static final int NUM_OF_BLACKHOLES = 3;

    private Frame frame;
    private Canvas canvas;

    public GameControllerNew() {
        initializeGUI();
        initializeGameSpace();
    }

    private void initializeGUI() {
        frame = new Frame("SpaceWar Game");
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());

        canvas = new Canvas();
        frame.add(canvas, BorderLayout.CENTER);

        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                System.exit(0);
            }
        });
    }

    private void initializeGameSpace() {
        Space gameSpace = new Space(MAP_WIDTH, MAP_HEIGHT);

        GenerateSpace generator = new GenerateSpace(gameSpace);
        generator.run(NUM_OF_PLANETS, NUM_OF_ASTEROIDS, NUM_OF_BLACKHOLES);

        gameSpace.print();
    }
}