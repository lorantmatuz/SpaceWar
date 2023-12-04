package hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects;

import java.awt.Point;

import hu.elte.inf.szofttech2023.team3.spacewar.view.FieldPosition;

public abstract class SpaceObject extends Point {

    protected SpaceObject(FieldPosition fieldPosition) {
        this(fieldPosition.getColumn(), fieldPosition.getRow());
    }
    
    protected SpaceObject(int x, int y) {
        super(x, y);
    }

    public void replace(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
}
