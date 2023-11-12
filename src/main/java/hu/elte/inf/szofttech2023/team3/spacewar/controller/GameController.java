package hu.elte.inf.szofttech2023.team3.spacewar.controller;

import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;
import hu.elte.inf.szofttech2023.team3.spacewar.view.GameViewer;
import hu.elte.inf.szofttech2023.team3.spacewar.display.DisplayEngine;

public class GameController {

    private GameState gameState;
    private GameViewer gameViewer;
    private DisplayEngine displayEngine;


    private static final int MAP_WIDTH = 20;
    private static final int MAP_HEIGHT = 20;

    private static final int NUM_OF_PLANETS = 10;
    private static final int NUM_OF_ASTEROIDS = 5;
    private static final int NUM_OF_BLACKHOLES = 3;

    private static final int NUM_OF_PLAYERS = 2;

    public GameController() {
        gameState = new GameState();
        gameViewer = new GameViewer();
        displayEngine = new DisplayEngine();

        gameViewer.init( this , displayEngine );
        displayEngine.init( gameViewer , MAP_WIDTH , MAP_HEIGHT );


        //initializeGUI();
        gameState.init( MAP_WIDTH, MAP_HEIGHT, NUM_OF_PLAYERS , NUM_OF_ASTEROIDS , NUM_OF_BLACKHOLES , NUM_OF_PLANETS );
        while( ! gameState.isGameOver() )
        {
            gameViewer.renderGame( gameState );
            gameState.setGameOver( true );
        }
        //initializeGameSpace();
    }

}
