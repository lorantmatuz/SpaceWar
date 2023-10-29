package hu.elte.inf.szofttech2023.team3.spacewar.space;

import hu.elte.inf.szofttech2023.team3.spacewar.space.objects.SpaceObject;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * This class stores the board for the game and serves as the basis of the
 * {@code GenerateSpace} and {@code ShortestPath} classes.
 */
public class Space {
    private static Space instance;
    public final int width, height;
    protected final boolean[][] isSpaceObject;
    protected final List<SpaceObject> objects;

    private Space(int width, int height) {
        this.width = width;
        this.height = height;
        isSpaceObject = new boolean[width][height];
        objects = new ArrayList<>();
    }

    public static Space getInstance(int width, int height) {
        if(instance == null) {
            instance = new Space(width,height);
        }
        return instance;
    }

    /**
     * Prints the boolean array.
     */
    public void print() {
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
    protected void setSpaceObject(Point p, SpaceObject object) {
        objects.add(object);
        isSpaceObject[p.x][p.y] = true;
    }
}
