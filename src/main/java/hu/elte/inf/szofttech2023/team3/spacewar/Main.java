package hu.elte.inf.szofttech2023.team3.spacewar;

import hu.elte.inf.szofttech2023.team3.spacewar.controller.GameController;
import hu.elte.inf.szofttech2023.team3.spacewar.display.SwingBoardDisplay;
import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;
import hu.elte.inf.szofttech2023.team3.spacewar.view.GameStateRenderer;

public class Main {

    private static final int BOARD_COLUMNS = 20;
    private static final int BOARD_ROWS = 20;
    private static final int FIELD_WIDTH = 40;
    private static final int FIELD_HEIGHT = 40;

    public static void main(String[] args) {
        Space space = new Space(BOARD_COLUMNS, BOARD_ROWS);
        GameState state = new GameState(space);
        SwingBoardDisplay display = new SwingBoardDisplay(BOARD_ROWS, BOARD_COLUMNS, FIELD_WIDTH, FIELD_HEIGHT);
        GameStateRenderer renderer = new GameStateRenderer(display);
        GameController controller = new GameController(state, renderer);
        controller.shuffle();
    }
    
}
