package hu.elte.inf.szofttech2023.team3.spacewar.space.objects;

import java.awt.Point;

public abstract class SpaceObject {
    public Point coordinate ;

    public SpaceObject(int x, int y) {
        coordinate = new Point(x,y);
    }
}
