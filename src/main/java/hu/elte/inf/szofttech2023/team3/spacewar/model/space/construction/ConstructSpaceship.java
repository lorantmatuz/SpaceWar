package hu.elte.inf.szofttech2023.team3.spacewar.model.space.construction;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Fleet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Spaceship;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.SpaceshipEnum;

public class ConstructSpaceship extends Constructable {
    private final SpaceshipEnum spaceship;
    private final Planet planet;

    public ConstructSpaceship(Planet planet, SpaceshipEnum spaceshipEnum) {
        this.planet = planet;
        this.spaceship = spaceshipEnum;
        timeLeftOfConstruction = spaceshipEnum.metalCost;
    }

    @Override
    protected void construct() {
        // TODO: find an adjacent coordinate
        final var fleet = new Fleet(planet.x + 1,planet.y + 1);
        fleet.addShip(new Spaceship(spaceship));
        planet.getOwner().addFleet(fleet);
    }
}