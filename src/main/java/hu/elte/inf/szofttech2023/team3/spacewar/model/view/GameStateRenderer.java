package hu.elte.inf.szofttech2023.team3.spacewar.model.view;

import java.util.Map;

import hu.elte.inf.szofttech2023.team3.spacewar.model.FieldPosition;
import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;
import hu.elte.inf.szofttech2023.team3.spacewar.model.Fighterjet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.Spacecraft;
import hu.elte.inf.szofttech2023.team3.spacewar.model.display.BoardDisplay;
import hu.elte.inf.szofttech2023.team3.spacewar.model.display.Displayable;

public class GameStateRenderer {
    
    private final BoardDisplay boardDisplay;
    
    public GameStateRenderer(BoardDisplay boardDisplay) {
        this.boardDisplay = boardDisplay;
    }
    
    public BoardDisplay getBoardDisplay() {
        return boardDisplay;
    }
    
    public void apply(GameState gameState) {
        int rowCount = boardDisplay.getRowCount();
        int columnCount = boardDisplay.getColumnCount();
        Displayable[][] displayables = new Displayable[rowCount][columnCount];
        for (Map.Entry<FieldPosition, Spacecraft> entry : gameState.getSpacecrafts().entrySet()) {
            FieldPosition position = entry.getKey();
            int row = position.getRow();
            int column = position.getColumn();
            if (row < rowCount && column < columnCount) {
                Spacecraft spacecraft = entry.getValue();
                displayables[row][column] = displayableOf(spacecraft);
            }
        }
        boardDisplay.apply(displayables);
    }
    
    private Displayable displayableOf(Spacecraft spacecraft) {
        if (spacecraft instanceof Fighterjet) {
            return new BoardItem("fighterjet", () -> System.out.println("Fighterjet clicked!"));
        } else {
            throw new IllegalArgumentException("Unknown spacecraft type");
        }
    }
    
}
