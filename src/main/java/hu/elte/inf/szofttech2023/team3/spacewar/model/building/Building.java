package hu.elte.inf.szofttech2023.team3.spacewar.model.building;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;

/**
 * This abstract class provides an implementation of the {@code Building}.
 *
 */
public abstract class Building {
    protected Planet planet;
    protected int level = 1;
    protected int size = 1;

    public Building(Planet planet) {
        this.planet = planet;
    }

    public abstract void upgrade();

    public int getLevel() {
        return level;
    }

    public int getDurationOfUpgrade() {
        return level;
    }
    
    public int getUpgradeCostOfLevel() {
        return (int)Math.pow(2,level);
    }

    public int getSize(){
        return this.size;
    }

}
