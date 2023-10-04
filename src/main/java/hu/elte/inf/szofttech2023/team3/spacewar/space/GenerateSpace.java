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
        int i = 0;
        for (int j = 0; j < numOfPlanets; j++) {
            final int size = rnd.nextInt(minPlanetSize,maxPlanetSize);
            final int temp = rnd.nextInt(minPlanetTemp,maxPlanetTemp);
            Point p = findFreeSpace();
            setSpaceObject(p,i++,new Planet(p.x,p.y,size,temp));
        }
        for (int j = 0; j < numOfBlackHoles; j++) {
            Point p = findFreeSpace();
            setSpaceObject(p,i++,new BlackHole(p.x,p.y));
        }
        for (int j = 0; j < numOfAsteroids; j++) {
            Point p = findFreeSpace();
            setSpaceObject(p,i++,new Asteroid(p.x,p.y));
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
