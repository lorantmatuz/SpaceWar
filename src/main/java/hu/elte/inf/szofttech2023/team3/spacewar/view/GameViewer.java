package hu.elte.inf.szofttech2023.team3.spacewar.view;

import hu.elte.inf.szofttech2023.team3.spacewar.controller.GameController;
import hu.elte.inf.szofttech2023.team3.spacewar.display.DisplayEngine;
import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;

public class GameViewer
{
    private GameController controller;
    private GameStateRenderer gameStateRenderer;

    public GameViewer(){};

    public void init( GameController controller, DisplayEngine displayEngine )
    {
        this.controller = controller;
        gameStateRenderer = new GameStateRenderer( displayEngine );
    }

    public GameStateRenderer getGameStateRenderer()
    {
        return this.gameStateRenderer;
    }

    public void renderGame( GameState gameState )
    {
        gameStateRenderer.apply( gameState );
    }

    public void transmitMessage()
    {
    //    controller.shuffle();
    }

}


