package hu.elte.inf.szofttech2023.team3.spacewar.model.space;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Asteroid;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.BlackHole;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.SpaceObject;

/**
 * This class implements the space class with a singleton pattern. This stores
 * the board for the game and serves as the basis of the
 * {@code GenerateSpace} and {@code ShortestPath} classes.
 */
public class Space {
    public final int width, height;
    protected final boolean[][] isSpaceObject;
    protected final List<SpaceObject> objects;

    public Space(int width, int height) {
        this.width = width;
        this.height = height;
        isSpaceObject = new boolean[width][height];
        objects = new ArrayList<>();
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
    
    public void erase() {
        objects.clear();
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                isSpaceObject[i][j] = false;
            }
        }
    }

    public SpaceObject getObjectAt(int x, int y) {
        for (SpaceObject obj : objects) {
            if (obj.x == x && obj.y == y) {
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

    public List<SpaceObject> getObjects() {
        return objects;
    }

    public List<SpaceObject> getSpaceObjects(){
        return this.objects;
    }
    
}
