package hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects;

import hu.elte.inf.szofttech2023.team3.spacewar.view.FieldPosition;

public class Asteroid extends SpaceObject {

    public Asteroid(FieldPosition position) {
        super(position);
    }
    
    public Asteroid(int x, int y) {
        super(x,y);
    }
    
}
