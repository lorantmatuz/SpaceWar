package hu.elte.inf.szofttech2023.team3.spacewar.model.building;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;

/**
 * This abstract class provides an implementation of the {@code Building}.
 *
 */
public abstract class Building {

    protected  BuildingEnum type;
    protected Planet planet;
    protected int level = 1;
    protected int size = 1;
    protected  boolean isFunctional = true;
    protected int roundWhenFunctioning;
    private static final int BASE_MATERIAL_COST = 10;

    public Building(BuildingEnum type, Planet planet, int level, int size, int roundWhenFunctioning )
    {
        this.type = type;
        this.planet = planet;
        this.level = level;
        this.size  = size;
        this.roundWhenFunctioning = roundWhenFunctioning;
    }
    public int getConstructionCost() {
        return BASE_MATERIAL_COST * this.level;
    }
    public static int getBaseMaterialCost() {
        return BASE_MATERIAL_COST;
    }
    public Building(Planet planet) {
        this.planet = planet;
    }

    public void setFunctional(boolean isFunctional) {
        this.isFunctional = isFunctional;
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

    public Boolean getFunctionality(){ return isFunctional; }

    public String getBuildingType(){ return this.type.toString(); }

}
