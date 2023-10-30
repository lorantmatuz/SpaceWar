package hu.elte.inf.szofttech2023.team3.spacewar.model.building;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;

public class SpaceShipFactory extends Building {

    public SpaceShipFactory(Planet planet) {
        super(planet);
    }

    @Override
    public void upgrade() {
        ++level;
    }

    //public SpaceShip produce(Class<? extends SpaceShip> type) {
    //  if(ship.minLevelToBuild() < level) return null;
    //  TODO
    // }
}