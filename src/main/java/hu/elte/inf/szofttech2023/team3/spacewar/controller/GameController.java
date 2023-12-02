package hu.elte.inf.szofttech2023.team3.spacewar.controller;

import hu.elte.inf.szofttech2023.team3.spacewar.display.SpecialAction;
import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.Player;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.TurnManager;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.GenerateSpace;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Path;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ShortestPath;
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
        Player currentPlayer = turnManager.nextPlayer(); // Váltás a következő játékosra
        renderer.displayNextTurn( gameState );
    }

    private void updateTurnDisplay(Player currentPlayer) {

    }

    private Fleet createFleetWithMothership(Point startPosition, Player owner) {
        if (startPosition != null) {
            Fleet fleet = new Fleet(startPosition.x, startPosition.y, owner);
            fleet.addShip(new Spaceship(SpaceshipEnum.MOTHER_SHIP));
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
            space.setSpaceObject(fleet1, fleet1);
        }
        if (fleet2 != null) {
            space.setSpaceObject(fleet2, fleet2);
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
        if (eventType == BoardEventType.CLICK) {
            handleBoardClick(event.getFieldPosition(), state);
        } else if (eventType == BoardEventType.HOVER) {
            handleBoardHover(event.getFieldPosition(), state);
        } else if (eventType == BoardEventType.OUT) {
            handleBoardOut(state);
        } else {
            System.out.println("Unknown event type: " + eventType);
        }
    }

    public void handleBoardClick(FieldPosition position, GameState state) {
        state = gameState;
        TurnManager turnManager = state.getTurnManager();
        FieldPosition selectedPosition = turnManager.getSelectedPosition();
        SpaceObject target = state.getSpace().getObjectAt(position);
        Player currentPlayer = turnManager.getCurrentPlayer();
        System.out.println("Most ennek a kore megy felül" + turnManager.getCurrentPlayer().getName());

        
        // FIXME
        state.getSpace().print();
        System.out.println(position + " --> " + target);
        
        // Player already selected a fleet.
        if (selectedPosition != null){
            System.out.println("selectedPosition: " + selectedPosition);
            if (target instanceof Planet) {
                // TODO: Ha ellenséges, bolygó akkor támadás, ha saját akkor kilépünk.
            }else if (target == null) {
                int row = position.getRow();
                int column = position.getColumn();
                System.out.println("Üres űrre kattintottak, és volt előtte flotta vagy űrhajó kattintva: Sor=" + row + ", Oszlop=" + column);

                Point targetPoint = new Point(column, row);
                Point fleetPoint = new Point(selectedPosition.getColumn(), selectedPosition.getRow());

                SpaceObject potentialFleet = gameState.getSpace().getObjectAt(selectedPosition);
                if (potentialFleet instanceof Fleet) {
                    Fleet selectedFleet = (Fleet) potentialFleet;
                    if (selectedFleet.getOwner().equals(turnManager.getCurrentPlayer())) {
                        if (!gameState.getSpace().isSpaceObject[targetPoint.x][targetPoint.y]) {
                            final var shortestPath = new ShortestPath(gameState.getSpace());
                            try {
                                Path path = shortestPath.run(fleetPoint, targetPoint);
                                System.out.println("Az útvonal pontjai:");
                                while (path.hasNext()) {
                                    Point point = path.next().node();
                                    System.out.println(point);
                                    gameState.getSpace().moveObject(selectedFleet, point);
                                    renderer.apply(gameState, this::handleBoardEvent);
                                }
                            } catch (IllegalArgumentException e) {
                                System.err.println("Hiba: " + e.getMessage());
                            }
                        } else {
                            System.err.println("A cél pont foglalt vagy nem érvényes.");
                        }
                    } else {
                        System.err.println("A kiválasztott objektum nem flotta.");
                    }
                    turnManager.setSelectedPosition(null);
                    turnManager.setPlannedPath(null);
                }
            }else {
                System.out.println("Nem a te flottád, nem mozgathatod.");
            }
        }
        else {
            gameState.setSelectedObject(target);

            if (target instanceof Fleet) {
                Fleet fleet = (Fleet) target;
                if (fleet.getOwner().equals(currentPlayer)) {
                    System.out.println("Fleet state:");
                    System.out.println("Spaceships: " + fleet.getSpaceships());
                    System.out.println("Minspeed: " + fleet.getMinSpeed());
                    System.out.println("X: " + fleet.x);
                    System.out.println("Y " + fleet.y);
                    System.out.println("owner" + fleet.getOwner().getName());
                    turnManager.setSelectedPosition(FieldPosition.of(fleet.y, fleet.x));
                }else {
                    System.out.println("Hostile fleet");
                }
            } else if (target instanceof Planet) {
                Planet planet = (Planet) target;
                System.out.println("Most ennek a kore megy " + turnManager.getCurrentPlayer().getName());
                if (planet.getOwner().equals(currentPlayer)) {
                    System.out.println("Planet state:");
                    System.out.println("Energy: " + planet.getEnergy());
                    System.out.println("Material: " + planet.getMaterial());
                    System.out.println("Owner: " + planet.getOwner().getName());
                } else {
                    System.out.println("Hostile planet");
                }
            } else if (target == null) {
                int row = position.getRow();
                int column = position.getColumn();
                System.out.println("Ures urre kattintottak, de nem volt elotte flotta vagy urhajó kattintva: Sor=" + row + ", Oszlop=" + column);
            }
        }
        
        renderer.apply(gameState, this::handleBoardEvent);

        if (target != null) {
            System.out.println(String.format("A(n) %s object was clicked", target.getClass().getSimpleName()));
            renderer.apply(target, gameState, this::handleActionEvent);
        }

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
        final var shortestPath = new ShortestPath(gameState.getSpace());
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
            renderer.applyBuildSelectAction( gameState, this::handleActionEvent );
        }
    }
    
}
