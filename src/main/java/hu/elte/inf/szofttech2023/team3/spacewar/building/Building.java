package hu.elte.inf.szofttech2023.team3.spacewar.building;

import hu.elte.inf.szofttech2023.team3.spacewar.space.objects.Planet;

/**
 * This abstract class provides an implementation of the {@code Building}.
 *
 */
public abstract class Building {
    protected Planet planet;
    protected int level = 1;

    public Building(Planet planet) {
        this.planet = planet;
    }

    public abstract void upgrade() ;


    public int getLevel() {
        return level;
    }

    // TODO: modify the upgrade cost of level function
    public double upgradeCostOfLevel() {
        return Math.pow(2.0, level);
    }

    // TODO: adapt duration of upgrade
    public int upgradeDurationOfLevel() {
        return level;
    }
}
