package hu.elte.inf.szofttech2023.team3.spacewar.controller;

import hu.elte.inf.szofttech2023.team3.spacewar.controller.generator.SpaceGenerator;
import hu.elte.inf.szofttech2023.team3.spacewar.display.DisplayEngine;
import hu.elte.inf.szofttech2023.team3.spacewar.display.SpecialAction;
import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;
import hu.elte.inf.szofttech2023.team3.spacewar.model.building.Building;
import hu.elte.inf.szofttech2023.team3.spacewar.model.building.BuildingEnum;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.Battle;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.Player;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.TurnManager;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.TurnState;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.GenerateSpace;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Path;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Owned;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.SpaceObject;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Fleet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Spaceship;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.SpaceshipEnum;
import hu.elte.inf.szofttech2023.team3.spacewar.view.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class GameController {
    
    private final GameStateRenderer renderer;
    
    private final SpaceGenerator spaceGenerator;
    
    private final List<Player> players;

    private GameState gameState = null;


    public GameController(DisplayEngine display, SpaceGenerator spaceGenerator, List<Player> players) {
        this.renderer = new GameStateRenderer(display);
        this.spaceGenerator = spaceGenerator;
        this.players = new ArrayList<>(players);
        initializeGame();
    }
    
    private void initializeGame() {
        Space space = spaceGenerator.generate();
        for(final var object: space.getObjects() ){
            if(object instanceof Planet){
                ((Planet) object).updateConstructions();
            }
        }
        gameState = new GameState(space, players);
        TurnManager turnManager = gameState.getTurnManager();
        createPlayersAndFleets(space);
        renderer.apply(gameState, this::handleBoardEvent);
        System.out.println("Elso kor " + turnManager.getCurrentPlayer().getName()+ " "+ turnManager.getTurnCounter() );
        renderer.displayNextTurn( gameState );
    }
    
    public GameState getGameState() {
        return gameState;
    }
    
    public void endTurn() {
        TurnManager turnManager = gameState.getTurnManager();
        Optional<Player> winner = detectWinner();
        if (winner.isPresent()) {
            handleWin(winner.get());
            return;
        }
        
        nextTurn();
        renderer.displayNothing("New Round!");
        System.out.println("End Turn: " + turnManager.getCurrentPlayer().getName()+ " "+ turnManager.getTurnCounter());
    }
    
    private void handleWin(Player player) {
        gameState.getTurnManager().setWinFor(player);
        renderer.apply(gameState, this::handleBoardEvent);
        renderer.displayNothing("Winner: " + player.getName());
        System.out.println("Player won: " + player.getName());
    }

    private void nextTurn() {
        TurnManager turnManager = gameState.getTurnManager();
        if (turnManager.getState() == TurnState.WIN) {
            return;
        }
        
        Player currentPlayer = turnManager.nextPlayer();
        updatePlanetsResourcesForCurrentPlayer(currentPlayer, gameState.getSpace());
        updateAllPlanetsConstructions(currentPlayer);
        renderer.displayNextTurn( gameState );
        refreshGameBoard();
    }
    private void refreshGameBoard() {
        renderer.apply(gameState, this::handleBoardEvent);
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
    private void updateAllPlanetsConstructions(Player currentPlayer) {
        Space space = gameState.getSpace();
        for (SpaceObject spaceObject : new ArrayList<>(space.getSpaceObjects())) {
            if (spaceObject instanceof Planet) {
                Planet planet = (Planet) spaceObject;
                if (planet.getOwner().equals(currentPlayer)) {
                    planet.updateConstructions();
                }
            }
        }
    }


    public void updateGraphics() {
        renderer.apply(gameState, this::handleBoardEvent);
    }
    private void createPlayersAndFleets(Space space) {
        Player player1 = gameState.getPlayers().get(0);
        Player player2 = gameState.getPlayers().get(1);
        Point player1StartPosition = space.findStartPositionForFleet(space, new Point(space.width - 1, 0));
        Point player2StartPosition = space.findStartPositionForFleet(space, new Point(0, space.height - 1));
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
        if (state.getTurnManager().getState() == TurnState.WIN) {
            return;
        }
        
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
        TurnManager turnManager = gameState.getTurnManager();
        SpaceObject target = gameState.getSpace().getObjectAt(position);
        Player currentPlayer = turnManager.getCurrentPlayer();
        System.out.println("Most ennek a kore megy felül" + turnManager.getCurrentPlayer().getName());


       /* // FIXME
        state.getSpace().print();
        System.out.println(position + " --> " + target);
*/
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
        } else if (target instanceof Planet planet) {
            if (target instanceof Planet) {
                if (planet.getOwner().equals(currentPlayer)) {
                    renderer.displayInfo("Choose planet operations.");
                    Map<BuildingEnum, Building> buildingMap = planet.getBuildingMap();
                    for (Map.Entry<BuildingEnum, Building> entry : buildingMap.entrySet()) {
                        Building building = entry.getValue();
                        System.out.println("Building Type: " + entry.getKey() +
                                ", Level: " + building.getLevel() +
                                ", Size: " + building.getSize() +
                                ", Functional: " + building.getFunctionality());
                    }
                    showObjectInfo = true;
                    renderer.apply(gameState, this::handleBoardEvent);
                    renderer.apply(planet, showObjectInfo, state, this::handleActionEvent);
                }
                } else {
                renderer.displayInfo("Hostile planet.");
                System.out.println("Hostile planet");
                // TODO : check if the enemy planet is in observation distance
            }
        } else if (target == null) {
            renderer.displayNothing("There is nothing there.");
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
        
        if (selectedPosition == null) {
            System.out.println("Nincs kijeloles.");
            return;
        }
        
        SpaceObject target = state.getSpace().getObjectAt(position);
        Player currentPlayer = turnManager.getCurrentPlayer();

        
        // FIXME
        /*state.getSpace().print();
        System.out.println(position + " --> " + target);*/
        
        // Player already selected a fleet.
        if (target == null) {
            Point targetPoint = new Point(position.getColumn(), position.getRow());
            Point fleetPoint = new Point(selectedPosition.getColumn(), selectedPosition.getRow());
            SpaceObject potentialFleet = gameState.getSpace().getObjectAt(selectedPosition);

            if (potentialFleet instanceof Fleet selectedFleet ) {
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
        if (target instanceof Fleet targetFleet ) {
            SpaceObject selectedObject = gameState.getSelectedObject();
            if (selectedObject instanceof Fleet selectedFleet ) {
                if (selectedFleet.getOwner().equals(currentPlayer) && targetFleet.getOwner().equals(currentPlayer) && isAdjacentToFleet(selectedFleet, targetFleet)) {
                    if(selectedFleet == targetFleet) {
                        renderer.displayInfo("The selected fleets are the same. Cannot be merged");
                    }else {
                        mergeFleets(selectedFleet, targetFleet);
                        renderer.displayInfo("The merging of fleets has been succsessfull.");
                        System.out.println("Flottak sikeresen osszevonva.");
                    }
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
        }else if (target instanceof Planet targetPlanet ) {
            SpaceObject selectedObject = gameState.getSelectedObject();
            if (selectedObject instanceof Fleet selectedFleet) {
                if (isAdjacentToPlanet(selectedFleet, targetPlanet)) {
                    if (targetPlanet.getOwner() == null ){
                        List<Spaceship> spaceships = selectedFleet.getSpaceships();
                        int i = 0;
                        while (i < spaceships.size() && spaceships.get(i).getSpaceshipType() != SpaceshipEnum.COLONY) {
                            i++;
                        }
                        if( i < spaceships.size() )
                        {
                            targetPlanet.setOwner(currentPlayer);
                            System.out.println("A bolygo elfoglalasa megtortent.");
                            renderer.displayInfo("The planet has been colonized!");
                        }
                        else {
                            renderer.displayInfo("Cannot colonize unhabitant planet without COLONY SHIP!");
                        }
                    } else if ( !targetPlanet.getOwner().equals(currentPlayer) )
                    {
                        List<Spaceship> spaceships = selectedFleet.getSpaceships();
                        int i = 0;
                        while (i < spaceships.size() && spaceships.get(i).getSpaceshipType() != SpaceshipEnum.MOTHER_SHIP) {
                            i++;
                        }
                        if( i < spaceships.size() )
                        {
                            targetPlanet.setOwner(currentPlayer);
                            System.out.println("A bolygo elfoglalasa megtortent.");
                            renderer.displayInfo("The enemy planet has been conquered!");
                        }
                        else {
                            renderer.displayInfo("Cannot attack enemy planet without MOTHER SHIP!");
                        }
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
        if (state.getTurnManager().getState() == TurnState.WIN) {
            return;
        }
        
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
        else if (actionEvent.getType() == SpecialAction.START_SHIP_CONSTRUCTION) {
            int shipID = renderer.getSelectedRow();
            SpaceshipEnum requestedShip = SpaceshipEnum.values()[shipID - 1];
            Planet planet = (Planet) gameState.getSelectedObject();

            if (planet.getMaterial() >= requestedShip.getMetalCost()) {
                planet.buildSpaceship(requestedShip);
                planet.setMaterial(planet.getMaterial() - requestedShip.getMetalCost()); // Levonjuk a nyersanyagot
                renderer.displayInfo("Construction of " + requestedShip + " started on planet " + planet.getName());
            } else {
                renderer.displayInfo("Not enough materials for building " + requestedShip);
            }

            renderer.apply(gameState.getSelectedObject(), true, gameState, this::handleActionEvent);
        }
        else if ( actionEvent.getType() == SpecialAction.BACK )
        {
            System.out.println("Back action");
            renderer.apply( gameState.getSelectedObject(), true, gameState, this::handleActionEvent);
        }
        else if (actionEvent.getType() == SpecialAction.START_BUILDING_CONSTRUCTION) {
            int buildingID = renderer.getSelectedRow();
            BuildingEnum requestedBuilding = BuildingEnum.values()[buildingID - 1];
            SpaceObject selectedObject = gameState.getSelectedObject();
            if (selectedObject instanceof Planet planet) {
                Building building = planet.getBuilding(requestedBuilding);

                int cost = (building == null) ? Building.getBaseMaterialCost() : building.getConstructionCost();

                if (planet.getMaterial() >= cost) {
                    planet.build(requestedBuilding);
                    planet.setMaterial(planet.getMaterial() - cost); // Levonjuk az anyagot
                    renderer.displayInfo("Construction of " + requestedBuilding + " started on planet " + planet.getName());
                } else {
                    renderer.displayInfo("Not enough materials for building " + requestedBuilding);
                }
            }
            renderer.apply(gameState.getSelectedObject(), true, gameState, this::handleActionEvent);
        }
    }
    private void startSpaceshipConstruction(Planet planet, SpaceshipEnum spaceshipType) {
      planet.buildSpaceship(spaceshipType);
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
    
    private Optional<Player> detectWinner() {
        Set<Player> foundPlayers = Collections.newSetFromMap(new IdentityHashMap<>());
        for (SpaceObject spaceObject : gameState.getSpace().getObjects()) {
            if (spaceObject instanceof Owned owned) {
                foundPlayers.add(owned.getOwner());
                if (foundPlayers.size() > 1) {
                    return Optional.empty();
                }
            }
        }
        
        if (foundPlayers.size() != 1) {
            return Optional.empty();
        }

        return Optional.of(foundPlayers.iterator().next());
    }
    
}
