package hu.elte.inf.szofttech2023.team3.spacewar.model.building;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;

/**
 * This abstract class provides an implementation of the {@code Building}.
 *
 */
public abstract class Building {
    protected Planet planet;
    protected int level = 1;
    private boolean isUnderConstruction = false;
    private int timeLeft = 0;

    public Building(Planet planet) {
        this.planet = planet;
    }

    public abstract void upgrade() ;

    public void scheduleUpgrade() {
        timeLeft = getDurationOfConstruction();
        isUnderConstruction = true;
    }

    public boolean checkUpgrade() {
        if(!isUnderConstruction) {
            return false;
        }
        if(--timeLeft == 0) {
            upgrade();
            isUnderConstruction = false;
            return true;
        }
        return false;
    }

    public int getLevel() {
        return level;
    }

    public int getDurationOfConstruction() {
        return level;
    }

    public boolean isUnderConstruction() {
        return isUnderConstruction;
    }

    // TODO: modify the upgrade cost of level function
    public int upgradeCostOfLevel() {
        return (int)Math.pow(2,level);
    }

}
