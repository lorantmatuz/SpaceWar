package hu.elte.inf.szofttech2023.team3.spacewar.model.space.construction;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Fleet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Spaceship;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.SpaceshipEnum;

import java.awt.Point;

public class ConstructSpaceship extends Constructable {
    private final SpaceshipEnum spaceship;
    private final Planet planet;

    public ConstructSpaceship(Planet planet, SpaceshipEnum spaceshipEnum, int constructionTime) {
        this.planet = planet;
        this.spaceship = spaceshipEnum;
        this.timeLeftOfConstruction = constructionTime;
    }

    @Override
    protected void construct() {
        Point startPosition = planet.getSpace().findStartPositionForFleet(planet.getSpace(), new Point(planet.x, planet.y));
        final var fleet = new Fleet(startPosition.x, startPosition.y, planet.getOwner());
        fleet.addShip(new Spaceship(spaceship));
        planet.getOwner().addFleet(fleet);
        planet.getSpace().setSpaceObject(fleet);
    }

    public SpaceshipEnum getSpaceship() {
        return spaceship;
    }
}