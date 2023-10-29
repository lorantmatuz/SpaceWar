package hu.elte.inf.szofttech2023.team3.spacewar.space;

import hu.elte.inf.szofttech2023.team3.spacewar.space.objects.Asteroid;
import hu.elte.inf.szofttech2023.team3.spacewar.space.objects.BlackHole;
import hu.elte.inf.szofttech2023.team3.spacewar.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.space.objects.SpaceObject;

import java.awt.*;
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
                if (isSpaceObject[i][j]) {
                    SpaceObject obj = getObjectAt(i, j);
                    if (obj instanceof BlackHole) {
                        System.out.print("F ");
                    } else if (obj instanceof Planet) {
                        System.out.print("B ");
                    } else if (obj instanceof Asteroid) {
                        System.out.print("A ");
                    } else {
                        System.out.print("X ");
                    }
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    private SpaceObject getObjectAt(int x, int y) {
        for (SpaceObject obj : objects) {
            if (obj.coordinate.x == x && obj.coordinate.y == y) {
                return obj;
            }
        }
        return null;
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
