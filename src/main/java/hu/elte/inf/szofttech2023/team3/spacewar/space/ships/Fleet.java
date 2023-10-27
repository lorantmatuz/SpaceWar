package hu.elte.inf.szofttech2023.team3.spacewar.space.ships;

import hu.elte.inf.szofttech2023.team3.spacewar.space.objects.SpaceObject;

public final class Fleet extends SpaceObject {
    private final Spaceship[] spaceships = new Spaceship[Spaceship.values().length];
    private int totalConsumption = 0;
    private int minSpeed = Integer.MAX_VALUE;

    public Fleet(int x, int y) {
        super(x, y);
    }

    public boolean addShip(Spaceship ship) {
        if(getShip(ship) != null) {
            return false;
        }
        spaceships[ship.ordinal()] = ship;
        totalConsumption += ship.consumption;
        if(minSpeed > ship.speed) {
            minSpeed = ship.speed;
        }
        return true;
    }

    public boolean canAttack() {
        return getShip(Spaceship.MOTHERSHIP) != null;
    }

    public boolean canColonize() {
        return getShip(Spaceship.COLONY) != null;
    }

    public boolean canCarryResources() {
        return getShip(Spaceship.SUPPLIER) != null;
    }

    public Spaceship getShip(Spaceship ship) {
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

    public static void main(String[] args) {
        var fleet = new Fleet(1,3);
        fleet.addShip(Spaceship.MOTHERSHIP);
        fleet.replace(2,3);
    }
}
