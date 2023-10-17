package hu.elte.inf.szofttech2023.team3.spacewar.space;

import hu.elte.inf.szofttech2023.team3.spacewar.space.objects.*;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

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
        create(numOfPlanets, Planet.class);
        create(numOfBlackHoles, BlackHole.class);
        create(numOfAsteroids, Asteroid.class);
    }

    /**
     * Creates SpaceObjects in Space.
     * @param numOfObjects the number of objects to generate
     * @param objectType the type of the object to generate
     */
    private void create(int numOfObjects, Class<? extends SpaceObject> objectType) {
        for (int j = 0; j < numOfObjects; j++) {
            Point p = findFreeSpace();
            try {
                SpaceObject obj = objectType.getDeclaredConstructor(int.class, int.class)
                                    .newInstance(p.x, p.y);
                space.setSpaceObject(p, obj);
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

}
