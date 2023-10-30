package hu.elte.inf.szofttech2023.team3.spacewar.view;

import java.util.Map;

import hu.elte.inf.szofttech2023.team3.spacewar.display.BoardDisplay;
import hu.elte.inf.szofttech2023.team3.spacewar.display.Displayable;
import hu.elte.inf.szofttech2023.team3.spacewar.model.old.FieldPosition;
import hu.elte.inf.szofttech2023.team3.spacewar.model.old.Fighterjet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.old.GameState;
import hu.elte.inf.szofttech2023.team3.spacewar.model.old.Spacecraft;

public class GameStateRendererOld {
    
    private final BoardDisplay boardDisplay;
    
    public GameStateRendererOld(BoardDisplay boardDisplay) {
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
