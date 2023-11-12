package hu.elte.inf.szofttech2023.team3.spacewar.view;

import hu.elte.inf.szofttech2023.team3.spacewar.display.DisplayEngine;
import hu.elte.inf.szofttech2023.team3.spacewar.model.Fighterjet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;
import hu.elte.inf.szofttech2023.team3.spacewar.display.BoardDisplay;
import hu.elte.inf.szofttech2023.team3.spacewar.display.Displayable;
import hu.elte.inf.szofttech2023.team3.spacewar.model.Spacecraft;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Asteroid;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.BlackHole;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.SpaceObject;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Fleet;

public class GameStateRenderer {

    private final DisplayEngine displayEngine;
    //private final BoardDisplay boardDisplay;

    /*
    public GameStateRenderer(BoardDisplay boardDisplay) {
        this.boardDisplay = boardDisplay;
    }
    */

    public GameStateRenderer(DisplayEngine displayEngine) {
        this.displayEngine = displayEngine;
    }
    
    public BoardDisplay getBoardDisplay() {
        //return displayEboardDisplay;
        return displayEngine.getBoardDisplay();
    }
    
    public void apply(GameState gameState) {
//        int rowCount = boardDisplay.getRowCount();
//        int columnCount = boardDisplay.getColumnCount();
        int rowCount = displayEngine.getBoardDisplay().getRowCount();
        int columnCount = displayEngine.getBoardDisplay().getColumnCount();
        Displayable[][] displayables = new Displayable[rowCount][columnCount];
        for ( SpaceObject entry : gameState.getSpaceObjects() )
        {
            int row = (int)entry.getX();
            int column = (int)entry.getY();
            displayables[row][column] = displayableOf( entry );
        }
        //boardDisplay.apply(displayables);
        displayEngine.getBoardDisplay().apply(displayables);
    }
    

    private Displayable displayableOf(SpaceObject spaceObject ) {
        if (spaceObject instanceof Fleet) {
            return new BoardItem("fighterjet", () -> System.out.println("Fleet clicked!"));
        } else if (spaceObject instanceof Asteroid) {
            return new BoardItem("asteroid", () -> System.out.println("asteroid clicked!"));
        } else if (spaceObject instanceof BlackHole) {
            return new BoardItem("blackhole", () -> System.out.println("blackhole clicked!"));
        } else if (spaceObject instanceof Planet) {
            return new BoardItem("planet", () -> System.out.println("planet clicked!"));
        } else {
            throw new IllegalArgumentException("Unknown spacecraft type");
        }
    }
    
}
