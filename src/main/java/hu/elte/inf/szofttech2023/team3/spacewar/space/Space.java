package hu.elte.inf.szofttech2023.team3.spacewar.space;

import hu.elte.inf.szofttech2023.team3.spacewar.space.objects.SpaceObject;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the space class with a singleton pattern. This stores
 * the board for the game and serves as the basis of the
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

    /**
     * Attempts to move the given object to the gives point in the Space
     * @param object The SpaceObject to move
     * @param to the Point in the Space to move the object
     * @return true if the movement was successful
     */
    public boolean moveObject(SpaceObject object, Point to) {
        if(isSpaceObject[to.x][to.y]) {
            return false;
        }
        final var index = objects.indexOf(object);
        if(index == -1) {
            return false;
        }
        final var innerObject = objects.get(index);
        isSpaceObject[innerObject.x][innerObject.y] = false;
        isSpaceObject[to.x][to.y] = true;
        innerObject.replace(to.x,to.y);
        return true;
    }
}
