package hu.elte.inf.szofttech2023.team3.spacewar.model.building;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Spaceship;

public class SpaceShipFactory extends Building {

    public SpaceShipFactory(Planet planet) {
        super(planet);
    }

    @Override
    public void upgrade() {
        ++level;
    }

    public Spaceship produce(Spaceship ship) {
        if(ship.minLevelToBuild < level) {
            return null;
        }
        return ship;
    }
}
