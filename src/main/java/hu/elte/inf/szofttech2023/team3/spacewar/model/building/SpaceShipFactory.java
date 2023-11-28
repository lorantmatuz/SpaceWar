package hu.elte.inf.szofttech2023.team3.spacewar.model.building;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.construction.ConstructSpaceship;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.SpaceshipEnum;

public class SpaceShipFactory extends Building {

    public SpaceShipFactory(Planet planet) {
        super(planet);
    }

    @Override
    public void upgrade() {
        ++level;
    }

    public void produce(SpaceshipEnum ship) {
        if(ship.minLevelToBuild < level) {
            return;
        }
        final var player = planet.getOwner();
        player.addConstruction(new ConstructSpaceship(planet,ship));
    }
}
