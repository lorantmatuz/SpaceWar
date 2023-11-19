package hu.elte.inf.szofttech2023.team3.spacewar.model.space;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

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
        create(numOfPlanets, Planet::new);
        create(numOfBlackHoles, BlackHole::new);
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
            SpaceObject obj = factory.create(p.x, p.y);
            space.setSpaceObject(p, obj);
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
