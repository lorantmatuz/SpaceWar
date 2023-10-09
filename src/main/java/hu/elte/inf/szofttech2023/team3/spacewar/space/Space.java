package hu.elte.inf.szofttech2023.team3.spacewar.space;

import hu.elte.inf.szofttech2023.team3.spacewar.space.objects.SpaceObject;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * This class stores the board for the game and serves as the basis of the
 * {@code GenerateSpace} and {@code ShortestPath} classes.
 */
public abstract class Space {
    public static final int width = 5, height = 4;
    public static final int numOfPlanets = 5, numOfBlackHoles = 2, numOfAsteroids = 1;
    protected static final boolean[][] isSpaceObject = new boolean[width][height];
    protected static final List<SpaceObject> objects = new ArrayList<>();


    /**
     * Prints the boolean array.
     */
    public static void print() {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                System.out.print(isSpaceObject[i][j] ? "X " : ". ");
            }
            System.out.println();
        }
    }

    /**
     * Sets a {@code SpaceObject} to the given index
     * @param p the {@code Point}, i.e. the place of the {@code SpaceObject}
     *         in the grid
     * @param object the {@code SpaceObject} to set
     */
    protected static void setSpaceObject(Point p, SpaceObject object) {
        objects.add(object);
        isSpaceObject[p.x][p.y] = true;
    }
}
