package hu.elte.inf.szofttech2023.team3.spacewar.model.space;

import java.awt.Point;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import hu.elte.inf.szofttech2023.team3.spacewar.model.building.BuildingEnum;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.Player;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.*;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Fleet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Spaceship;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.SpaceshipEnum;

/**
 * This class provides a method called {@code run()} to generate the {@code Space} class.
 */
public class GenerateSpace {
    private final Space space;
    private final List<Player> players;
    private static final ThreadLocalRandom rnd = ThreadLocalRandom.current();

    public GenerateSpace(Space space, List<Player> players) {
        this.space = space;
        this.players = players;
    }

    /**
     * Generates the {@code Space} in which the game is played.
     */
    public void run(int numOfFleets, int numOfPlanets, int numOfAsteroids, int numOfBlackHoles) {
        space.erase();
        System.out.println("rendering fleets...");
        create(numOfFleets / 2, (r, c) -> createFleet(r, c, players.get(0)));
        create(numOfFleets - (numOfFleets / 2), (r, c) -> createFleet(r, c, players.get(1)));
        System.out.println("rendering planets...");
        create(numOfPlanets / 2, (r, c) -> new Planet(r, c, players.get(0)));
        create(numOfPlanets - (numOfPlanets / 2), (r, c) -> new Planet(r, c, players.get(1)));
        System.out.println("rendering blackholes...");
        create(numOfBlackHoles, BlackHole::new);
        System.out.println("rendering asteroids...");
        create(numOfAsteroids, Asteroid::new);
    }

    private Fleet createFleet(int r, int c, Player player) {
        Fleet fleet = new Fleet(r, c, player);
        fleet.addShip(new Spaceship(SpaceshipEnum.MOTHER_SHIP));
        return fleet;
    }

    /**
     * Creates SpaceObjects in Space.
     * @param numOfObjects the number of objects to generate
     * @param factory the type of the object to create
     */
    private void create(int numOfObjects, SpaceObjectFactory factory) {
        for (int j = 0; j < numOfObjects; j++) {
            Point p = findFreeSpace();
            /*
            SpaceObject obj = factory.create(p.x, p.y);
            space.setSpaceObject(p, obj);
            */
            try {
                SpaceObject obj = factory.create(p.x, p.y);
                space.setSpaceObject(obj);
                //
                if(obj instanceof Planet planet)
                {
                    planet.setEnergy(rnd.nextInt());
                    planet.setMaterial(rnd.nextInt());
                    planet.setEnergy(rnd.nextInt());
                    planet.setMaxSize(rnd.nextInt());
                    planet.setTemperature(rnd.nextInt());
                    planet.setName("Planet-X" );
                    planet.build(BuildingEnum.MINE);
                    planet.build(BuildingEnum.SOLAR_POWER_PLANT);
                }
                //
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Finds a {@code Point} in the grid where no {@code SpaceObject}
     * has been placed yet.
     * @return a random {@code Point} in the {@code Space} having no {@code SpaceObject}
     */
    private Point findFreeSpace() {
        int x, y;
        do {
            x = rnd.nextInt(space.width);
            y = rnd.nextInt(space.height);
        }
        while(space.isSpaceObject[x][y]);
        return new Point(x,y);
    }
    
    
    @FunctionalInterface
    private interface SpaceObjectFactory {
        
        public SpaceObject create(int x, int y);
        
    }

}
