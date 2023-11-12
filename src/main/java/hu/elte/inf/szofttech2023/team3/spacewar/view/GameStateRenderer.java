package hu.elte.inf.szofttech2023.team3.spacewar.view;

import hu.elte.inf.szofttech2023.team3.spacewar.display.BoardDisplay;
import hu.elte.inf.szofttech2023.team3.spacewar.display.Displayable;
import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Asteroid;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.BlackHole;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.SpaceObject;

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
        Space space = gameState.getSpace();
        for (int j = 0; j < space.height; j++) {
            for (int i = 0; i < space.width; i++) {
                SpaceObject spaceObject = space.getObjectAt(i, j);
                if (spaceObject != null) {
                    displayables[i][j] = displayableOf(spaceObject);
                }
            }
        }
        boardDisplay.apply(displayables);
    }

    private Displayable displayableOf(SpaceObject spaceObject) {
        if (spaceObject instanceof Asteroid) {
            return new BoardItem("asteroid", () -> System.out.println("This is an asteroid"));
        } else if (spaceObject instanceof BlackHole) {
            return new BoardItem("blackhole", () -> System.out.println("This is a black hole"));
        } else if (spaceObject instanceof Planet) {
            return new BoardItem("planet", () -> System.out.println("This is a planet"));
        } else {
            return new BoardItem("fighterjet", () -> System.out.println("HELLO"));
        }
    }
    
}
