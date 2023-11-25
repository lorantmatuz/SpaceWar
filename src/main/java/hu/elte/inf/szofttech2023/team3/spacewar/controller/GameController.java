package hu.elte.inf.szofttech2023.team3.spacewar.controller;

import hu.elte.inf.szofttech2023.team3.spacewar.display.SpecialAction;
import hu.elte.inf.szofttech2023.team3.spacewar.display.SwingBoardDisplay;
import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.Player;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.*;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.SpaceObject;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Fleet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Spaceship;
import hu.elte.inf.szofttech2023.team3.spacewar.view.FieldPosition;
import hu.elte.inf.szofttech2023.team3.spacewar.view.GameActionListener;
import hu.elte.inf.szofttech2023.team3.spacewar.view.GameStateRenderer;

import java.awt.*;

public class GameController {

    private final  GameState gameState;
    private int tempFleetX = -1;
    private int tempFleetY = -1;
    
    private final GameStateRenderer renderer;
    
    public GameController(GameState gameState, GameStateRenderer renderer) {
        this.gameState = gameState;
        this.renderer = renderer;
        initializeGame();
    }
    private void initializeGame() {
        // Térkép generálása
        Space space = gameState.getSpace();
        GenerateSpace generator = new GenerateSpace(space);
        generator.run(10, 5, 5);

        // Játékosok és flották létrehozása
        createPlayersAndFleets(space);

        // Játékállapot frissítése
        renderer.apply(gameState, this::handleAnyAction);
    }
    private Fleet createFleetWithMothership(Point startPosition) {
        if (startPosition != null) {
            Fleet fleet = new Fleet(startPosition.x, startPosition.y);
            fleet.addShip(Spaceship.MOTHER_SHIP);
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
        Fleet fleet1 = createFleetWithMothership(player1StartPosition);
        Fleet fleet2 = createFleetWithMothership(player2StartPosition);
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
        GenerateSpace generator = new GenerateSpace(space);
        generator.run(10, 5, 5);
        renderer.apply(gameState, this::handleAnyAction);
    }
    public void setupActionListener(SwingBoardDisplay display) {
        GameActionListener actionListener = new GameActionListener() {
            @Override
            public void actionPerformed(Object target, GameState gameState) {
                handleAnyAction(target, gameState);
            }
        };
        display.setActionListener(actionListener);
    }

    public void handleAnyAction(Object target, GameState state) {

        // Player already selected a fleet.
        if(state.getActionState()){
            if(target instanceof Spaceship) {
                // TODO: Ellenséges hajó támadása / saját hajó flottalapítás, ha a saját hajó anyahajó vagy a cél anyahajó
            } else if (target instanceof Planet) {
                // TODO: Ha ellenséges, bolygó akkor támadás, ha saját akkor kilépünk.
            }else if (target instanceof FieldPosition) {
                FieldPosition position = (FieldPosition) target;
                int row = position.getRow();
                int column = position.getColumn();
                System.out.println("Üres űrre kattintottak, és volt előtte flotta vagy űrhajó kattintva: Sor=" + row + ", Oszlop=" + column);

                Point targetPoint = new Point(row, column);
                Point fleetPoint = new Point(tempFleetX, tempFleetY);

                SpaceObject potentialFleet = gameState.getSpace().getObjectAt(tempFleetX, tempFleetY);
                if (potentialFleet instanceof Fleet) {
                    Fleet selectedFleet = (Fleet) potentialFleet;

                    if (!gameState.getSpace().isSpaceObject[targetPoint.x][targetPoint.y]) {
                        final var shortestPath = new ShortestPath(gameState.getSpace());
                        try {
                            Path path = shortestPath.run(fleetPoint, targetPoint);
                            System.out.println("Az útvonal pontjai:");
                            while (path.hasNext()) {
                                Point point = path.next().node();
                                System.out.println(point);
                                gameState.getSpace().moveObject(selectedFleet, point);
                                renderer.apply(gameState, this::handleAnyAction);
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
                state.setActionState(false);
            }
        }
        else{
            if (target instanceof Fleet) {
                Fleet fleet = (Fleet) target;
                System.out.println("Fleet state:");
                System.out.println("Spaceships: " + fleet.getSpaceships());
                System.out.println("Minspeed: " + fleet.getMinSpeed());
                System.out.println("X: " + fleet.x);
                System.out.println("Y " + fleet.y);
                tempFleetY = fleet.y;
                tempFleetX = fleet.x;
                state.setActionState(true);
            }else if (target instanceof Planet) {
                Planet planet = (Planet) target;
                System.out.println("Planet state:");
                System.out.println("Energy: " + planet.getEnergy());
                System.out.println("Material: " + planet.getMaterial());
            }else if (target instanceof FieldPosition) {
                FieldPosition position = (FieldPosition) target;
                int row = position.getRow();
                int column = position.getColumn();
                System.out.println("Ures urre kattintottak, de nem volt előtte flotta vagy űrhajó kattintva: Sor=" + row + ", Oszlop=" + column);
            }
        }
        if (target == SpecialAction.SHUFFLE) {
            shuffle();
        }else if (target instanceof SpaceObject) {
            // TODO
            System.out.println(String.format("A(n) %s object was clicked", target.getClass().getSimpleName()));
        }


    }
    
}
