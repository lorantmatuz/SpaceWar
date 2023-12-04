package hu.elte.inf.szofttech2023.team3.spacewar.view;

import hu.elte.inf.szofttech2023.team3.spacewar.display.BoardDisplay;
import hu.elte.inf.szofttech2023.team3.spacewar.display.DisplayEngine;
import hu.elte.inf.szofttech2023.team3.spacewar.display.Displayable;
import hu.elte.inf.szofttech2023.team3.spacewar.display.SpecialAction;
import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;
import hu.elte.inf.szofttech2023.team3.spacewar.model.building.Building;
import hu.elte.inf.szofttech2023.team3.spacewar.model.building.BuildingEnum;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.TurnManager;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Asteroid;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.BlackHole;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.SpaceObject;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Fleet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Spaceship;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.SpaceshipEnum;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GameStateRenderer {
    
    private final DisplayEngine displayEngine;

    public GameStateRenderer(DisplayEngine displayEngine) {
        this.displayEngine = displayEngine;
    }


    public void apply(GameState gameState, BoardEventListener boardEventListener) {
        BoardDisplay boardDisplay = displayEngine.getBoardDisplay();
        int rowCount = boardDisplay.getRowCount();
        int columnCount = boardDisplay.getColumnCount();
        Displayable[][] displayables = new Displayable[rowCount][columnCount];
        collectSpaceObjects(gameState, displayables);
        if( gameState.getSelectedObject()!= null && gameState.getSelectedObject().getClass() == Fleet.class ) {
            collectPathObjects(gameState, displayables);
        }
        displayEngine.applyBoard(displayables);
        boardDisplay.setBoardListener((type, position) -> boardEventListener.actionPerformed(new BoardEvent(type, position), gameState));
    }
    
    private void collectSpaceObjects(GameState gameState, Displayable[][] displayables) {
        Space space = gameState.getSpace();
        for (int row = 0; row < space.height; row++) {
            for (int column = 0; column < space.width; column++) {
                SpaceObject spaceObject = space.getObjectAt(column, row);
                if (spaceObject != null) {
                    displayables[row][column] = displayableOf(spaceObject, gameState);
                }
            }
        }
    }

    private void collectPathObjects(GameState gameState, Displayable[][] displayables) {
        TurnManager turnManager = gameState.getTurnManager();
        List<FieldPosition> plannedPath = turnManager.getPlannedPath();
        if (plannedPath == null) {
            return;
        }
        
        for (FieldPosition position : plannedPath) {
            displayables[position.getRow()][position.getColumn()] = new BoardItem("plannedpath", false);
        }
    }

    public void apply(Object object , Boolean showObjectInfo, GameState gameState, ActionEventListener actionEventListener) {
        String title = "";
        List<Map.Entry<String, Integer>> attributeContent = new ArrayList<>();
        String collectionTitle = "";
        ArrayList<String> collectionHeader = new ArrayList<>();
        List<Map.Entry<String, List<Integer>>> collectionContent = new ArrayList<>();
        String actionsTitle = "";
        List<Map.Entry<String, Runnable >> actionContent = new ArrayList<>();
        if (object instanceof Planet planet) {
            title = "Unknown planet";
            if( showObjectInfo )
            {
                title = planet.getName();
                attributeContent.add(Map.entry("energy", planet.getEnergy()));
                attributeContent.add(Map.entry("material", planet.getMaterial()));
                attributeContent.add(Map.entry("temperature", planet.getTemperature()));
                attributeContent.add(Map.entry("space capacity", planet.getMaxSize()));
                attributeContent.add(Map.entry("used capacity", planet.getSize()));
                collectionTitle = "Buildings";
                collectionHeader.add("Building type");
                collectionHeader.add("Level");
                collectionHeader.add("Size");
                Map<BuildingEnum, Building> buildingList = planet.getBuildingMap();
                for (Map.Entry<BuildingEnum, Building> set : buildingList.entrySet()) {
                    List<Integer> listElementAttributes = new ArrayList<>();
                    listElementAttributes.add(set.getValue().getLevel());
                listElementAttributes.add(set.getValue().getSize());
                    collectionContent.add(Map.entry( set.getKey().name(), listElementAttributes )
                    );
                }
                actionsTitle = "Planet Operations";
                actionContent.add(
                        Map.entry(
                                "Build Building",
                                createActionEvent(actionEventListener, SpecialAction.BUILD_BUILDING, gameState)
                        )
                );
                actionContent.add(
                        Map.entry(
                                "Build Ship",
                                createActionEvent(actionEventListener, SpecialAction.BUILD_SHIP, gameState)
                        )
                );
                /*
                actionContent.add(
                        Map.entry(
                                "Transfer Resources",
                                createActionEvent(actionEventListener, SpecialAction.TRANSFER, gameState)
                        )
                );
                 */
            }
        }
        else if (object instanceof Fleet fleet)
        {
                title = "Unknown Fleet";
                if( showObjectInfo )
                {
                    title = "Fleet-" + fleet.getId();
                    ArrayList<Spaceship> spaceships = fleet.getSpaceships();
                    attributeContent.add(Map.entry("Total # of ships", fleet.getTotalShipNumber() ));
                    attributeContent.add(Map.entry("Total HP", fleet.getTotalHP() ));
                    attributeContent.add(Map.entry("Total offense", fleet.getTotalOffense() ));
                    attributeContent.add(Map.entry("Total defense", fleet.getTotalDefense() ));
                    attributeContent.add(Map.entry("Speed", fleet.getMinSpeed() ));
                    attributeContent.add(Map.entry("Max Transported resource", fleet.getMaxTransportedResources() ));
                    attributeContent.add(Map.entry("Transported resource", fleet.getTransportedResources() ));
                    // write the number of ships for all ship types
                    collectionTitle = "Ships";
                    collectionHeader.add("Ship type");
                    collectionHeader.add("Number of ship");
                    for (SpaceshipEnum ship : SpaceshipEnum.values() )
                    {
                        List<Integer> listElementAttributes = new ArrayList<>();
                        int numberOfShips = fleet.getNumberOf( ship );
                        if( numberOfShips > 0 )
                        {
                          listElementAttributes.add( fleet.getNumberOf( ship ) );
                          collectionContent.add(Map.entry( ship.name(), listElementAttributes ) );
                        }
                    }
                    /*
                    actionsTitle = "Fleet Operations";
                    actionContent.add(
                            Map.entry(
                                    "Merge fleet",
                                    createActionEvent(actionEventListener, SpecialAction.MERGE_FLEET, gameState)
                            )
                    );
                    actionContent.add(
                            Map.entry(
                                    "Create new fleet",
                                    createActionEvent(actionEventListener, SpecialAction.CREATE_FLEET, gameState)
                            )
                    );
                     */
            }
        }
        else if (object instanceof Asteroid)
        {
            title = "An asteroid";
        }
        else if (object instanceof BlackHole)
        {
            title = "A black hole";
        }
        displayEngine.applyObjectInfo( title , attributeContent );
        displayEngine.applyObjectItemsInfo( true , collectionTitle, collectionHeader , collectionContent );
        displayEngine.applyObjectActionPalette( actionsTitle , actionContent  );
    }

    public void applyBuildBuildingSelectAction(GameState gameState , ActionEventListener listener )
    {
        System.out.println("Rendering buildings....");
        Planet selectedPlanet = (Planet) gameState.getSelectedObject();
        Map<BuildingEnum, Building>  buildingMap = selectedPlanet.getBuildingMap();
        String collectionTitle = "Buildings to develop";
        ArrayList<String> collectionHeader = new ArrayList<>();
        collectionHeader.add("Building type");
        collectionHeader.add("Level");
        collectionHeader.add("Cost");
        collectionHeader.add("Build time");
        Object[][] collectionContent = new Object[ BuildingEnum.values().length + 1 ][ collectionHeader.size() ];
        for( int icol = 0; icol < collectionHeader.size() ; ++icol )
        {
            collectionContent[ 0 ][ icol ] = collectionHeader.get( icol );
        }
        int row = 1;
        for( BuildingEnum building : BuildingEnum.values() )
        {
            int icol = 0;
            collectionContent[ row ][ icol++ ] = building.name();
            collectionContent[ row ][ icol++ ] = 1;
            collectionContent[ row ][ icol++ ] = 1;
            collectionContent[ row ][ icol   ] = 1;
            for ( Map.Entry<BuildingEnum, Building> entry : buildingMap.entrySet() ) {
                if (!entry.getValue().getFunctionality()) {
                    displayEngine.setInfoLabel(entry.getValue().getClass().getSimpleName() + " is under construction!");
                    return;
                }
                if( entry.getKey() == building )
                {
                    icol = 0;
                    collectionContent[ row ][ icol++ ] = entry.getKey().name();
                    collectionContent[ row ][ icol++ ] = entry.getValue().getLevel() + 1;
                    collectionContent[ row ][ icol++ ] = entry.getValue().getSize() + 1;
                    collectionContent[ row ][ icol   ] = entry.getValue().getDurationOfUpgrade() + 1;
                }
            }
            row++;
        }
        String actionsTitle = "Development Operations";
        List<Map.Entry<String, Runnable >> actionContent = new ArrayList<>();
        actionContent.add(
                Map.entry(
                        "Start building",
                        createActionEvent( listener, SpecialAction.START_BUILDING_CONSTRUCTION, gameState)
                )
        );
        actionContent.add(
                Map.entry(
                        "Back",
                        createActionEvent( listener, SpecialAction.BACK, gameState)
                )
        );
        displayEngine.applyItemSelector(true , collectionTitle, collectionHeader.toArray() , collectionContent );
        displayEngine.setInfoLabel("Choose a building for development!");
        displayEngine.applyObjectActionPalette( actionsTitle , actionContent  );
    }
    
    public void applyBuildShipSelectAction(GameState gameState , ActionEventListener listener )
    {
        System.out.println("Rendering ships....");
        String collectionTitle = "Spaceships to build";
        ArrayList<String> collectionHeader = new ArrayList<>();
        collectionHeader.add("Ship type");
        collectionHeader.add("Cost");
        collectionHeader.add("Build time");
        Object[][] collectionContent = new Object[ SpaceshipEnum.values().length + 1 ][ collectionHeader.size() ];
        for( int icol = 0; icol < collectionHeader.size() ; ++icol )
        {
            collectionContent[ 0 ][ icol ] = collectionHeader.get( icol );
        }
        int row = 1;
        for( SpaceshipEnum ship : SpaceshipEnum.values() )
        {
            int icol = 0;
            collectionContent[ row ][ icol++ ] = ship.name();
            collectionContent[ row ][ icol++ ] = ship.getMetalCost();
            collectionContent[ row ][ icol   ] = ship.getTurnsToComplete();
            row++;
        }
        String actionsTitle = "Development Operations";
        List<Map.Entry<String, Runnable >> actionContent = new ArrayList<>();
        actionContent.add(
                Map.entry(
                        "Start building",
                        createActionEvent( listener, SpecialAction.START_SHIP_CONSTRUCTION, gameState)
                )
        );
        actionContent.add(
                Map.entry(
                        "Back",
                        createActionEvent( listener, SpecialAction.BACK, gameState)
                )
        );
        displayEngine.applyItemSelector(true , collectionTitle, collectionHeader.toArray() , collectionContent );
        displayEngine.setInfoLabel("Choose a spaceship to build");
        displayEngine.applyObjectActionPalette( actionsTitle , actionContent  );
    }


    public void displayNextTurn(GameState gameState)
    {
        TurnManager manager = gameState.getTurnManager();
        displayInfo("New round for player " + manager.getCurrentPlayer().getName() + "!" );
        displayTurnInfo( manager );
    }

    public void displayTurnInfo( TurnManager manager )
    {
        displayEngine.setTurnLabel(
                manager.getTurnCounter() ,
                manager.getCurrentPlayer().getName() ,
                manager.getActionPoint()
        );
    }
    public void displayInfo( String theInfo )
    {
        displayEngine.setInfoLabel( theInfo );
    }
    private Runnable createActionEvent(ActionEventListener actionEventListener, Object type, GameState gameState) {
        return () -> actionEventListener.actionPerformed(new ActionEvent(type), gameState);
    }

    private Displayable displayableOf(SpaceObject spaceObject, GameState gameState) {
        String imageName;
        if (spaceObject instanceof Fleet fleet) {
            imageName = "fighterjet-" + fleet.getOwner().getNo();
        } else if (spaceObject instanceof Planet planet) {
                imageName = "planet-" + planet.getOwner().getNo();
        } else {
            imageName = spaceObject.getClass().getSimpleName().toLowerCase(); // Minden más esetben
        }
        FieldPosition objectPosition = FieldPosition.of(spaceObject.y, spaceObject.x);
        boolean selected = Objects.equals(gameState.getTurnManager().getSelectedPosition(), objectPosition);
        return new BoardItem(imageName, selected);
    }

    public int getSelectedRow()
    {
        System.out.println(" Selected row: " + displayEngine.getSelectedRow() );
        return displayEngine.getSelectedRow();
    }
}
