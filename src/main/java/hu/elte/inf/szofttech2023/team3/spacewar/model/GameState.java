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
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.SpaceshipEnum;
import hu.elte.inf.szofttech2023.team3.spacewar.view.FieldPosition;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameState {

    private final Space space;
    private final List<Player> players;
    private final TurnManager turnManager;


    public GameState(Space space, List<Player> players) {
        this.space = space;
        this.players = players;
        this.turnManager = new TurnManager(players);
    }

    public TurnManager getTurnManager() {
        return turnManager;
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
        // players
        final var players = List.of(new Player(1, "A"), new Player(2, "B"));
        // generator
        {
            final var generator = new GenerateSpace(space, players);
            generator.run(4,10,4,3);
        }
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
                final var fleet = new Fleet(dummyPosition,dummyPosition,players.get(0));
                // TODO: normally, no ships can be added directly,
                // TODO: they must be produced by a shipfactory
                fleet.addShip(new Spaceship(SpaceshipEnum.MOTHER_SHIP));
                final var fleets = List.of(fleet);
                player.addFleet(fleet);
            }
        }
        // state
        final var gameState = new GameState(space,players);
        // manager
        final var manager = gameState.getTurnManager();

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
                manager.setSelectedPosition(FieldPosition.of(clickedObject.y, clickedObject.x));
            }
            // planet building, upgrade
            {
                // click on planet
                final var planet = player.getPlanets().get(0);
                // build
                planet.build(BuildingEnum.MINE);
                planet.build(BuildingEnum.SPACE_SHIP_FACTORY);
                planet.produceShip(SpaceshipEnum.MOTHER_SHIP);
                // upgrade
                //
                //planet.upgrade(BuildingEnum.MINE);
                planet.scheduleUpgrade(BuildingEnum.MINE);

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
