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
                fleet.addShip(Spaceship.MOTHERSHIP);
                final var fleets = List.of(fleet);
                player.addFleet(fleet);
            }
        }
        // state
        final var gameState = new GameState(space,players);
        // manager
        final var manager = gameState.getManager();
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
            final var ssf = planet.build(BuildingEnum.SPACE_SHIP_FACTORY);
            // ssf.build(...);
            // upgrade
            planet.upgrade(BuildingEnum.MINE);
        }
        // travelling
        {
            // click on fleet
            // TODO: find fleet of player via (x,y) coordinates
            final var fleet = player.getFleets().get(2);
            final var start = new Point(fleet.x,fleet.y);
            // "random" clicked target point example
            final var target = new Point(6,9);
            manager.setTargetPoint(target);
            final var pathSearch = new ShortestPath(space);
            final var path = pathSearch.run(start,target);
            final var cost = path.getTotalCost();
            // TODO: handle case if cost > actionPoints
            final var actionPoints = manager.getActionPoint();
            if(actionPoints < cost) {
                while(path.hasNext()) {
                    final var pair = path.next();
                    if(pair.cost() > actionPoints) {
                        break;
                    }
                }
            }
            // TODO: get max-cost path not exceeding 'cost'
            //manager.decreaseActionPointBy(cost);
        }


        //player.get
    }
}
