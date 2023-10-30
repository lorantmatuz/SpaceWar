package hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects;

import java.awt.Point;

public abstract class SpaceObject extends Point {

    public SpaceObject(int x, int y) {
        super(x,y);
    }

    public void replace(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
