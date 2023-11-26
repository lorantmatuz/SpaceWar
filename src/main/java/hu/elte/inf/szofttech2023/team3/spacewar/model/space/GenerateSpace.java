package hu.elte.inf.szofttech2023.team3.spacewar.model.space;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

import hu.elte.inf.szofttech2023.team3.spacewar.model.building.Building;
import hu.elte.inf.szofttech2023.team3.spacewar.model.building.BuildingEnum;
import hu.elte.inf.szofttech2023.team3.spacewar.model.building.Mine;
import hu.elte.inf.szofttech2023.team3.spacewar.model.building.SolarPowerPlant;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.*;

/**
 * This class provides a method called {@code run()} to generate the {@code Space} class.
 */
public class GenerateSpace {
    private final Space space;
    private static final ThreadLocalRandom rnd = ThreadLocalRandom.current();

    public GenerateSpace(Space space) {
        this.space = space;
    }

    /**
     * Generates the {@code Space} in which the game is played.
     */
    public void run(int numOfPlanets, int numOfAsteroids, int numOfBlackHoles) {
        space.erase();
        System.out.println("rendering planets...");
        create(numOfPlanets, Planet::new);
        System.out.println("rendering blackholes...");
        create(numOfBlackHoles, BlackHole::new);
        System.out.println("rendering asteroids...");
        create(numOfAsteroids, Asteroid::new);
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
                space.setSpaceObject(p, obj);
                //
                if( obj instanceof Planet)
                {
                    Planet planet = (Planet) obj;
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
