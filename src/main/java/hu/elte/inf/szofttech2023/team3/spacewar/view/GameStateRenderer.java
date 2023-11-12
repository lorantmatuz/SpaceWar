package hu.elte.inf.szofttech2023.team3.spacewar.view;

import hu.elte.inf.szofttech2023.team3.spacewar.display.BoardDisplay;
import hu.elte.inf.szofttech2023.team3.spacewar.display.Displayable;
import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.SpaceObject;

public class GameStateRenderer {
    
    private final BoardDisplay boardDisplay;
    
    public GameStateRenderer(BoardDisplay boardDisplay) {
        this.boardDisplay = boardDisplay;
    }
    
    public BoardDisplay getBoardDisplay() {
        return boardDisplay;
    }
    
    public void apply(GameState gameState, GameActionListener listener) {
        int rowCount = boardDisplay.getRowCount();
        int columnCount = boardDisplay.getColumnCount();
        Displayable[][] displayables = new Displayable[rowCount][columnCount];
        Space space = gameState.getSpace();
        for (int j = 0; j < space.height; j++) {
            for (int i = 0; i < space.width; i++) {
                SpaceObject spaceObject = space.getObjectAt(i, j);
                if (spaceObject != null) {
                    displayables[i][j] = displayableOf(spaceObject, gameState, listener);
                }
            }
        }
        boardDisplay.apply(displayables);
    }

    private Displayable displayableOf(SpaceObject spaceObject, GameState gameState, GameActionListener listener) {
        String imageName = spaceObject.getClass().getSimpleName().toLowerCase();
        Runnable action = () -> listener.actionPerformed(spaceObject, gameState);
        return new BoardItem(imageName, action);
    }
    
}
