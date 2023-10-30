package hu.elte.inf.szofttech2023.team3.spacewar.controller;

import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.GenerateSpace;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;
import hu.elte.inf.szofttech2023.team3.spacewar.view.GameStateRenderer;

public class GameController {
    
    private final GameState gameState;
    
    private final GameStateRenderer renderer;
    
    public GameController(GameState gameState, GameStateRenderer renderer) {
        this.gameState = gameState;
        this.renderer = renderer;
    }
    
    public void shuffle() {
        Space space = gameState.getSpace();
        GenerateSpace generator = new GenerateSpace(space);
        generator.run(10, 5, 5);
        renderer.apply(gameState);
    }
    
}
