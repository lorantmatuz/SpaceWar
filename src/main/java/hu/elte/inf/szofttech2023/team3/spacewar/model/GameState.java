package hu.elte.inf.szofttech2023.team3.spacewar.model;

import hu.elte.inf.szofttech2023.team3.spacewar.model.building.BuildingEnum;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.Player;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.TurnManager;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.GenerateSpace;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ShortestPath;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Fleet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Spaceship;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameState {
    private final Space space;
    private final List<Player> players;
    private final TurnManager manager;


    public GameState(Space space, List<Player> players) {
        this.space = space;
        this.players = players;
        this.manager = new TurnManager(players);
    }

    public TurnManager getManager() {
        return manager;
    }

    public Space getSpace() {
        return space;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public static void main(String[] args) {
        /**
         * Dummy user's guide
         */
        // space
        final var space = new Space(10,10);
        // generator
        {
            final var generator = new GenerateSpace(space);
            generator.run(10,4,3);
        }
        // players
        final var players = List.of(new Player("A"),
                                    new Player("B"));
        // setup
        {
            final var objects = space.getObjects();
            for (final var player : players) {
                // first planets
                // find and link one planet to each player
                // make sure the number of planets reaches the number of players
                // TODO: make it more sophisticated
                for(final var object : objects) {
                    if(object instanceof Planet planet) {
                        player.addPlanet(planet);
                        break;
                    }
                }
                // first fleet
                // TODO: figure the position of the first fleets of the players
                final var dummyPosition = 2;
                final var fleet = new Fleet(dummyPosition,dummyPosition);
                // TODO: normally, no ships can be added directly,
                // TODO: they must be produced by a shipfactory
                fleet.addShip(Spaceship.MOTHER_SHIP);
                final var fleets = List.of(fleet);
                player.addFleet(fleet);
            }
        }
        // state
        final var gameState = new GameState(space,players);
        // manager
        final var manager = gameState.getManager();

        // let's play!!
        int i = 0;
        // TODO: remove dummy i later on
        while(manager.getWinner() == null && ++i <= 10) {
            final var player = manager.nextPlayer();

            // clicking on gameObject
            {
                // TODO: unite with clicking gui(?)
                // TODO: use space.getObjectAt(x,y) instead
                // random clicked example
                final var clickedObject = space.getObjects().get(2);
                manager.setSelectedObject(clickedObject);
            }
            // planet building, upgrade
            {
                // click on planet
                final var planet = player.getPlanets().get(0);
                // build
                planet.build(BuildingEnum.MINE);
                planet.build(BuildingEnum.SPACE_SHIP_FACTORY);
                final var ship = planet.produceShip(Spaceship.MOTHER_SHIP);
                if(ship != null) {
                    System.out.println(ship + " DONE:)");
                }
                // upgrade
                //
                //planet.upgrade(BuildingEnum.MINE);
                planet.scheduleUpgrade(BuildingEnum.MINE);
                // and check for upgrades if they finished
                // recommended to use it in all loop
                planet.checkUpgrade();

            }
            // travelling
            {
                // click on fleet
                // TODO: find fleet of player via (x,y) coordinates
                final var fleet = player.getFleets().get(0);
                final var start = new Point(fleet.x,fleet.y);
                // "random" clicked target point example
                final var target = new Point(6,9);
                manager.setTargetPoint(target);
                final var pathSearch = new ShortestPath(space);
                final var path = pathSearch.run(start,target);
                final var totalCost = path.getTotalCost();
                // TODO: handle case if cost > actionPoints
                final var actionPoints = manager.getActionPoint();
                var decreaseByPoint = actionPoints;
                var endPoint = target;
                if(actionPoints < totalCost) {
                    // example usage of path longer than action points
                    final List<Point> travelPath = new ArrayList<>();
                    while(path.hasNext()) {
                        final var pair = path.next();
                        // path.accumulatingCost represents the total cost of path so far
                        if(pair.accumulatingCost() > actionPoints) {
                            break;
                        }
                        // means that the fleet can travel until the given point
                        travelPath.add(pair.node());
                        decreaseByPoint = pair.accumulatingCost();
                    }
                    // now travelPath represent the path that can be taken
                    System.out.println(travelPath);
                    endPoint = travelPath.get(travelPath.size() - 1);
                }
                // fly to the endpoint
                // TODO: simulate moving along the path?
                space.moveObject(fleet,endPoint);
                // decrease the action points for flying
                manager.decreaseActionPointBy(decreaseByPoint);
            }

        }
    }
}
