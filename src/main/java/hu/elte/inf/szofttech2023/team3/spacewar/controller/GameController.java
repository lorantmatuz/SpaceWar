package hu.elte.inf.szofttech2023.team3.spacewar.controller;

import hu.elte.inf.szofttech2023.team3.spacewar.display.SpecialAction;
import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;
import hu.elte.inf.szofttech2023.team3.spacewar.model.building.BuildingEnum;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.Battle;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.Player;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.TurnManager;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.GenerateSpace;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Path;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.SpaceObject;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Fleet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Spaceship;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.SpaceshipEnum;
import hu.elte.inf.szofttech2023.team3.spacewar.view.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameController {

    private final  GameState gameState;
    
    private final GameStateRenderer renderer;

    private final TurnManager turnManager;


    public GameController(GameState gameState, GameStateRenderer renderer) {
        this.gameState = gameState;
        this.renderer = renderer;
        turnManager = gameState.getTurnManager();
        initializeGame();
    }
    private void initializeGame() {
        // Térkép generálása
        Space space = gameState.getSpace();
        GenerateSpace generator = new GenerateSpace(space, gameState.getPlayers());
        generator.run(4, 10, 5, 5);
        //updateTurnDisplay(turnManager.getCurrentPlayer());
        createPlayersAndFleets(space);
        renderer.apply(gameState, this::handleBoardEvent);
        System.out.println("Elso kor " + turnManager.getCurrentPlayer().getName()+ " "+ turnManager.getTurnCounter() );
        renderer.displayNextTurn( gameState );
    }
    private void startTurn() {
        Player currentPlayer = turnManager.getCurrentPlayer();
    }

    public void endTurn() {
        nextTurn();
        System.out.println("End Turn: " + turnManager.getCurrentPlayer().getName()+ " "+ turnManager.getTurnCounter() );
    }

    private void nextTurn() {
        Player currentPlayer = turnManager.nextPlayer();
        updatePlanetsResourcesForCurrentPlayer(currentPlayer, gameState.getSpace());
        renderer.displayNextTurn( gameState );
    }

    private Fleet createFleetWithMothership(Point startPosition, Player owner) {
        if (startPosition != null) {
            Fleet fleet = new Fleet(startPosition.x, startPosition.y, owner);
            fleet.addShip(new Spaceship(SpaceshipEnum.MOTHER_SHIP));
            fleet.addShip(new Spaceship(SpaceshipEnum.SUPPLIER));
            return fleet;
        }
        return null;
    }
    private Point findStartPositionForFleet(Space space, Point nearPoint) {
        if (space.isSpaceObject[nearPoint.x][nearPoint.y] == false) {
            // Ha a kezdőpont szabad, akkor azt használjuk
            return nearPoint;
        }

        // Spirális keresés a kezdőpont körül
        int radius = 1;
        while (radius < Math.max(space.width, space.height)) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    int x = nearPoint.x + dx;
                    int y = nearPoint.y + dy;

                    // Ellenőrizzük, hogy a pont a térképen belül van-e
                    if (x >= 0 && x < space.width && y >= 0 && y < space.height) {
                        // Ellenőrizzük, hogy a pont szabad-e
                        if (!space.isSpaceObject[x][y]) {
                            return new Point(x, y);
                        }
                    }
                }
            }
            radius++;
        }

        return null; // Nem találtunk szabad helyet
    }

    private void createPlayersAndFleets(Space space) {
        Player player1 = gameState.getPlayers().get(0);
        Player player2 = gameState.getPlayers().get(1);
        Point player1StartPosition = findStartPositionForFleet(space, new Point(space.width - 1, 0));
        Point player2StartPosition = findStartPositionForFleet(space, new Point(0, space.height - 1));
        Fleet fleet1 = createFleetWithMothership(player1StartPosition, player1);
        Fleet fleet2 = createFleetWithMothership(player2StartPosition, player2);
        if (fleet1 != null) {
            space.setSpaceObject(fleet1);
        }
        if (fleet2 != null) {
            space.setSpaceObject(fleet2);
        }

        player1.addFleet(fleet1);
        player2.addFleet(fleet2);
    }
    
    public void shuffle() {
        // TODO
        Space space = gameState.getSpace();
        GenerateSpace generator = new GenerateSpace(space, gameState.getPlayers());
        generator.run(8, 10, 5, 5);
        renderer.apply(gameState, this::handleBoardEvent);
    }
    
    public void handleBoardEvent(BoardEvent event, GameState state) {
        BoardEventType eventType = event.getType();
        if (eventType == BoardEventType.LEFT_CLICK) {
            handleBoardLeftClick(event.getFieldPosition(), state);
        } else if (eventType == BoardEventType.RIGHT_CLICK) {
            handleBoardRightClick(event.getFieldPosition(), state);
        } else if (eventType == BoardEventType.HOVER) {
            handleBoardHover(event.getFieldPosition(), state);
        } else if (eventType == BoardEventType.OUT) {
            handleBoardOut(state);
        } else {
            System.out.println("Unknown event type: " + eventType);
        }
    }

    public void handleBoardLeftClick(FieldPosition position, GameState state) {

        boolean showObjectInfo = false;
        state = gameState;
        TurnManager turnManager = state.getTurnManager();
        FieldPosition selectedPosition = turnManager.getSelectedPosition();
        SpaceObject target = state.getSpace().getObjectAt(position);
        Player currentPlayer = turnManager.getCurrentPlayer();
        System.out.println("Most ennek a kore megy felül" + turnManager.getCurrentPlayer().getName());


        // FIXME
        state.getSpace().print();
        System.out.println(position + " --> " + target);

        gameState.setSelectedObject(target);

        if (target instanceof Fleet) {
            Fleet fleet = (Fleet) target;
            if (fleet.getOwner().equals(currentPlayer)) {
                renderer.displayInfo("Choose target!");
                showObjectInfo = true;
                System.out.println("Fleet state:");
                System.out.println("Spaceships: " + fleet.getSpaceships());
                System.out.println("Minspeed: " + fleet.getMinSpeed());
                System.out.println("X: " + fleet.x);
                System.out.println("Y " + fleet.y);
                System.out.println("owner" + fleet.getOwner().getName());
                turnManager.setSelectedPosition(FieldPosition.of(fleet.y, fleet.x));
            }else {
                renderer.displayInfo("Hostile fleet.");
                System.out.println("Hostile fleet");
                // TODO : check if the enemy fleet is in observation distance
            }
        } else if (target instanceof Planet) {
            Planet planet = (Planet) target;
            if (target instanceof Planet) {
                if (planet.getOwner().equals(currentPlayer)) {
                    renderer.displayInfo("Choose planet operations.");
                    showObjectInfo = true;
                    renderer.apply(state, this::handleBoardEvent);
                    renderer.apply(planet, showObjectInfo, state, this::handleActionEvent);
                }
                } else {
                renderer.displayInfo("Hostile planet.");
                System.out.println("Hostile planet");
                // TODO : check if the enemy planet is in observation distance
            }
        } else if (target == null) {
            renderer.displayInfo("There is nothing there.");
            int row = position.getRow();
            int column = position.getColumn();
            System.out.println("Ures urre kattintottak: Sor=" + row + ", Oszlop=" + column);
        }

        renderer.apply(gameState, this::handleBoardEvent);

        if (target != null) {
            System.out.println(String.format("A(n) %s object was clicked", target.getClass().getSimpleName()));
            renderer.apply( target, showObjectInfo, gameState, this::handleActionEvent);
        }

    }
    public void handleBoardRightClick(FieldPosition position, GameState state) {
        state = gameState;
        TurnManager turnManager = state.getTurnManager();
        FieldPosition selectedPosition = turnManager.getSelectedPosition();
        SpaceObject target = state.getSpace().getObjectAt(position);
        Player currentPlayer = turnManager.getCurrentPlayer();

        
        // FIXME
        state.getSpace().print();
        System.out.println(position + " --> " + target);
        
        // Player already selected a fleet.
        if (target == null) {
            Point targetPoint = new Point(position.getColumn(), position.getRow());
            Point fleetPoint = new Point(selectedPosition.getColumn(), selectedPosition.getRow());
            SpaceObject potentialFleet = gameState.getSpace().getObjectAt(selectedPosition);

            if (potentialFleet instanceof Fleet) {
                Fleet selectedFleet = (Fleet) potentialFleet;
                if (selectedFleet.getOwner().equals(currentPlayer)) {
                    //final var shortestPath = new ShortestPath(gameState.getSpace());
                    final var shortestPath = gameState.getShortestPath();
                    try {
                        Path path = shortestPath.run(fleetPoint, targetPoint);
                        int actionCost = (int) path.getTotalCost() * selectedFleet.getMinSpeed();

                        if (turnManager.getActionPoint() >= actionCost) {
                            executeTravel(selectedFleet, path);
                            turnManager.decreaseActionPointBy(actionCost);
                            renderer.displayInfo("Travel accomplished.");
                            renderer.displayTurnInfo( turnManager );
                        } else {

                            System.out.println("Nincs eleg akciopont az utazashoz.");

                            renderer.displayInfo("Not enough ActionPoints for that travel!");
                            System.out.println("Nincs eleg akciópont az utazashoz.");

                        }
                    } catch (IllegalArgumentException e) {
                        System.err.println("Hiba: " + e.getMessage());
                    }
                } else {
                    renderer.displayInfo("The selected fleet is not yours!");
                    System.err.println("A kivalasztott objektum nem a sajat flottad.");
                }
                turnManager.setSelectedPosition(null);
                turnManager.setPlannedPath(null);
            }else {
                renderer.displayInfo("The fleet is not yours to control!");
                System.out.println("Nem a te flottad, nem mozgathatod.");
            }

        }
        if (target instanceof Fleet) {
            Fleet targetFleet = (Fleet) target;
            SpaceObject selectedObject = gameState.getSelectedObject();
            if (selectedObject instanceof Fleet) {
                Fleet selectedFleet = (Fleet) selectedObject;
                if (selectedFleet.getOwner().equals(currentPlayer) && targetFleet.getOwner().equals(currentPlayer) && isAdjacentToFleet(selectedFleet, targetFleet)) {
                    mergeFleets(selectedFleet, targetFleet);
                    renderer.displayInfo("The merging of fleets has been succsessfull.");
                    System.out.println("Flottak sikeresen osszevonva.");
                } else if (selectedFleet.getOwner().equals(currentPlayer) && !targetFleet.getOwner().equals(currentPlayer) && isAdjacentToFleet(selectedFleet, targetFleet)) {
                    boolean battleResult = Battle.fight(selectedFleet, targetFleet);

                    if (battleResult) {
                        gameState.getSpace().removeFleet(targetFleet);
                        renderer.displayInfo("Victory!");
                    } else {
                        gameState.getSpace().removeFleet(selectedFleet);
                        renderer.displayInfo("Defeat!");
                    }
                    renderer.apply(gameState, this::handleBoardEvent);
                }else {
                    renderer.displayInfo("The selected fleets cannot be merged!");
                    System.out.println("A kivalasztott flottak nem osszevonhatoak.");
                }
            }
        }else if (target instanceof Planet) {
            Planet targetPlanet = (Planet) target;
            SpaceObject selectedObject = gameState.getSelectedObject();
            if (selectedObject instanceof Fleet) {
                Fleet selectedFleet = (Fleet) selectedObject;
                if (isAdjacentToPlanet(selectedFleet, targetPlanet)) {
                    if (targetPlanet.getOwner() == null ||
                            !targetPlanet.getOwner().equals(currentPlayer)) {
                        targetPlanet.setOwner(currentPlayer);
                        System.out.println("A bolygo elfoglalasa megtortent.");
                        renderer.displayInfo("The enemy planet has been conquered!");
                    } else if (isAdjacentToPlanet(selectedFleet, targetPlanet) && targetPlanet.getOwner().equals(currentPlayer)) {
                        if (selectedFleet.getTransportedResources() == 0) {
                            int resourceToLoad = Math.min(targetPlanet.getMaterial(), selectedFleet.getMaxTransportedResources());
                            selectedFleet.modifyTransportedResources(resourceToLoad);
                            targetPlanet.setMaterial(targetPlanet.getMaterial() - resourceToLoad);
                            renderer.displayInfo("The resources of the planet has been transported to the fleet");
                            System.out.println("Nyersanyagok felvetele a bolygorol.");
                        } else {
                            int resourcesToTransfer = selectedFleet.getTransportedResources();
                            targetPlanet.setMaterial(targetPlanet.getMaterial() + resourcesToTransfer);
                            selectedFleet.modifyTransportedResources(-resourcesToTransfer);
                            renderer.displayInfo("??");
                            System.out.println("Nyersanyagok atadása a bolygonak.");
                        }
                    }
                } else {
                    System.out.println("A kivalasztott muvelet nem hajthato vegre.");
                    renderer.displayInfo("The operation cannot be executed!");
                }
            }
        }

        renderer.apply(gameState, this::handleBoardEvent);

    }

    private void handleBoardHover(FieldPosition position, GameState state) {
        TurnManager turnManager = state.getTurnManager();
        FieldPosition selectedPosition = turnManager.getSelectedPosition();
        SpaceObject target = state.getSpace().getObjectAt(position);
        if (selectedPosition != null) {
            if (target == null) {
                turnManager.setPlannedPath(calculateShortestPathBetween(selectedPosition, position));
            } else {
                turnManager.setPlannedPath(null);
            }
            renderer.apply(gameState, this::handleBoardEvent);
        }
        
    }
    private void handleBoardOut(GameState state) {
        TurnManager turnManager = state.getTurnManager();
        FieldPosition selectedPosition = turnManager.getSelectedPosition();
        if (selectedPosition != null) {
            turnManager.setPlannedPath(null);
            renderer.apply(gameState, this::handleBoardEvent);
        }
    }
    
    private List<FieldPosition> calculateShortestPathBetween(FieldPosition base, FieldPosition to) {
        List<FieldPosition> result = new ArrayList<>();
        //final var shortestPath = new ShortestPath(gameState.getSpace());
        final var shortestPath = gameState.getShortestPath();
        Path path = shortestPath.run(
                new Point(base.getColumn(), base.getRow()),
                new Point(to.getColumn(), to.getRow()));
        if (path.hasNext()) {
            path.next(); // skip start point
        }
        while (path.hasNext()) {
            Point point = path.next().node();
            result.add(FieldPosition.of(point.y, point.x));
        }
        return result;
    }

    private void handleActionEvent(ActionEvent actionEvent, GameState state) {
        // TODO
        System.out.println("ActionEvent: " + actionEvent.getType());
        if ( actionEvent.getType() == SpecialAction.BUILD_BUILDING )
        {
            System.out.println("Build building action");
            renderer.applyBuildBuildingSelectAction( gameState, this::handleActionEvent );
        }
        else if ( actionEvent.getType() == SpecialAction.BUILD_SHIP )
        {
            System.out.println("Build ship action");
            renderer.applyBuildShipSelectAction( gameState , this::handleActionEvent );
        }
        else if ( actionEvent.getType() == SpecialAction.START_SHIP_CONSTRUCTION )
        {
            int shipID = renderer.getSelectedRow();
            // TODO: handle spaceship building logic
            SpaceshipEnum requestedShip = null;
            for( SpaceshipEnum ship : SpaceshipEnum.values() )
            {
                if( shipID == ship.ordinal() + 1 )
                {
                    requestedShip = ship;
                }
            }
            renderer.displayInfo( "Development of " + requestedShip + " is requested." );
            renderer.apply( gameState.getSelectedObject(), true, gameState, this::handleActionEvent);
        }
        else if ( actionEvent.getType() == SpecialAction.BACK )
        {
            System.out.println("Back action");
            renderer.apply( gameState.getSelectedObject(), true, gameState, this::handleActionEvent);
        }
        else if (actionEvent.getType() == SpecialAction.START_BUILDING_CONSTRUCTION) {
            int buildingID = renderer.getSelectedRow();
            BuildingEnum requestedBuilding = BuildingEnum.values()[buildingID - 1]; // Az ID alapján meghatározzuk az épület típusát

            SpaceObject selectedObject = gameState.getSelectedObject();
            if (selectedObject instanceof Planet) {
                Planet planet = (Planet) selectedObject;
                planet.build(requestedBuilding); // Elindítjuk az építési folyamatot a bolygón
                renderer.displayInfo("Construction of " + requestedBuilding + " started on planet " + planet.getName());
            }
            renderer.apply(gameState.getSelectedObject(), true, gameState, this::handleActionEvent);
        }
    }
    private void handleBuildBuildingAction(GameState state) {
        SpaceObject selectedObject = state.getSelectedObject();
        if (selectedObject instanceof Planet) {
            Planet selectedPlanet = (Planet) selectedObject;
            renderer.applyBuildBuildingSelectAction(state, this::handleBuildingEvent);
        } else {
            renderer.displayInfo("No planet selected for building.");
        }
    }
    private void handleBuildingEvent(ActionEvent actionEvent, GameState state) {
        BuildingEnum buildingType = (BuildingEnum) actionEvent.getType();
        Planet selectedPlanet = (Planet) state.getSelectedObject();

        try {
            selectedPlanet.build(buildingType); // Építési folyamat indítása
            renderer.displayInfo(buildingType.name() + " building has started construction.");
        } catch (Exception e) {
            renderer.displayInfo("Error: Unable to start building construction.");
        }

        renderer.apply(state, this::handleBoardEvent); // Frissíti a játék állapotát
    }
    private void executeTravel(Fleet fleet, Path path) {
        while (path.hasNext()) {
            Point point = path.next().node();
            gameState.getSpace().moveObject(fleet, point);
        }
        renderer.apply(gameState, this::handleBoardEvent);
    }
    private boolean isAdjacentToFleet(Fleet fleet, Fleet targetFleet) {
        int deltaX = Math.abs(fleet.x - targetFleet.x);
        int deltaY = Math.abs(fleet.y - targetFleet.y);
        return (deltaX <= 1 && deltaY <= 1);
    }
    private void mergeFleets(Fleet fleet1, Fleet fleet2) {
        if (fleet1.mergeFleet(fleet2)) {
            gameState.getSpace().removeFleet(fleet2);
            renderer.apply(gameState, this::handleBoardEvent);
            System.out.println("Flottak sikeresen osszevonva.");
            renderer.displayInfo("The fleets have been successfully merged!");
        } else {
            renderer.displayInfo("The merging of fleets has not been successfull...");
            System.out.println("Nem sikerult osszevonni a flottakat.");
        }
    }
    private boolean isAdjacentToPlanet(Fleet fleet, Planet targetPlanet) {
        int deltaX = Math.abs(fleet.x - targetPlanet.x);
        int deltaY = Math.abs(fleet.y - targetPlanet.y);
        return (deltaX <= 1 && deltaY <= 1);
    }
    private void updatePlanetsResourcesForCurrentPlayer(Player currentPlayer, Space space) {
        for (SpaceObject spaceObject : space.getSpaceObjects()) {
            if (spaceObject instanceof Planet) {
                Planet planet = (Planet) spaceObject;
                if (planet.getOwner().equals(currentPlayer)) {
                    planet.importEnergy();
                    planet.importMaterial();
                }
            }
        }
    }
    
}
