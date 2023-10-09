package hu.elte.inf.szofttech2023.team3.spacewar.space;

import hu.elte.inf.szofttech2023.team3.spacewar.space.objects.*;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class extends the {@code Space} class with a generating {@code static}
 * method called {@code run()}.
 */
public class GenerateSpace extends Space {
    private static final ThreadLocalRandom rnd = ThreadLocalRandom.current();

    /**
     * Generates the {@code Space} in which the game is played.
     */
    public static void run() {
        create(numOfPlanets, Planet.class);
        create(numOfBlackHoles, BlackHole.class);
        create(numOfAsteroids, Asteroid.class);
    }

    /**
     * Creates SpaceObjects in Space.
     * @param numOfObjects the number of objects to generate
     * @param objectType the type of the object to generate
     */
    private static void create(int numOfObjects, Class<? extends SpaceObject> objectType) {
        for (int j = 0; j < numOfObjects; j++) {
            Point p = findFreeSpace();
            try {
                SpaceObject obj = objectType.getDeclaredConstructor(Integer.class, Integer.class)
                                    .newInstance(p.x, p.y);
                setSpaceObject(p, obj);
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
    private static Point findFreeSpace() {
        int x, y;
        do {
            x = rnd.nextInt(width);
            y = rnd.nextInt(height);
        }
        while(isSpaceObject[x][y]);
        return new Point(x,y);
    }

}
