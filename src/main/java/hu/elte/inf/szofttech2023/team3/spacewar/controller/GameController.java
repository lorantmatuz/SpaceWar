package hu.elte.inf.szofttech2023.team3.spacewar.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import hu.elte.inf.szofttech2023.team3.spacewar.model.FieldPosition;
import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;
import hu.elte.inf.szofttech2023.team3.spacewar.model.Fighterjet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.Spacecraft;
import hu.elte.inf.szofttech2023.team3.spacewar.model.display.BoardDisplay;
import hu.elte.inf.szofttech2023.team3.spacewar.model.view.GameStateRenderer;

public class GameController {
    
    private final GameState gameState;
    
    private final GameStateRenderer renderer;
    
    private final Random random = new Random();

    public GameController(GameState gameState, GameStateRenderer renderer) {
        this.gameState = gameState;
        this.renderer = renderer;
    }
    
    public void shuffle() {
        BoardDisplay display = renderer.getBoardDisplay();
        int rowCount = display.getRowCount();
        int columnCount = display.getColumnCount();
        Map<FieldPosition, Spacecraft> spacecrafts = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            int row = random.nextInt(rowCount);
            int column = random.nextInt(columnCount);
            spacecrafts.put(FieldPosition.of(row, column), new Fighterjet(52));
        }
        gameState.setSpacecrafts(spacecrafts);
        renderer.apply(gameState);
    }
    
}
