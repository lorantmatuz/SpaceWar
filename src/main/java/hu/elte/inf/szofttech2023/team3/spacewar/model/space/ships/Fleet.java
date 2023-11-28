package hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships;

import hu.elte.inf.szofttech2023.team3.spacewar.model.game.Player;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.SpaceObject;

public final class Fleet extends SpaceObject {
    private static int id = -1;
    private final Spaceship[] spaceships = new Spaceship[SpaceshipEnum.values().length];
    private Player owner;
    private int totalConsumption = 0;
    private int minSpeed = Integer.MAX_VALUE;

    public Fleet(int x, int y, Player owner) {
        super(x, y);
        this.owner = owner;
        ++id;
    }

    public boolean addShip(Spaceship ship) {
        if(getShip(ship.spaceship) != null) {
            return false;
        }
        spaceships[ship.spaceship.ordinal()] = ship;
        totalConsumption += ship.spaceship.consumption;
        if(minSpeed > ship.spaceship.speed) {
            minSpeed = ship.spaceship.speed;
        }
        return true;
    }

    public boolean mergeFleet(Fleet fleet) {
        boolean retVal = true;
        for(final var ship : fleet.getSpaceships()) {
            retVal = retVal && addShip(ship);
        }
        return retVal;
    }

    public boolean canAttack() {
        return getShip(SpaceshipEnum.MOTHER_SHIP) != null;
    }

    public boolean canColonize() {
        return getShip(SpaceshipEnum.COLONY) != null;
    }

    public boolean canCarryResources() {
        return getShip(SpaceshipEnum.SUPPLIER) != null;
    }

    public Spaceship getShip(SpaceshipEnum ship) {
        return spaceships[ship.ordinal()];
    }

    public int getMinSpeed() {
        return minSpeed;
    }

    public int getTotalConsumption() {
        return totalConsumption;
    }

    public Spaceship[] getSpaceships() {
        return spaceships.clone();
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public static void main(String[] args) {
        var fleet = new Fleet(1, 3, new Player(1, "John"));
        fleet.addShip(new Spaceship(SpaceshipEnum.MOTHER_SHIP));
        fleet.replace(2,3);
    }
}
