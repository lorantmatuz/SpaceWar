package hu.elte.inf.szofttech2023.team3.spacewar.model.space.construction;

import hu.elte.inf.szofttech2023.team3.spacewar.model.building.Building;

public class UpgradeBuilding extends Constructable {
    private final Building building;

    public UpgradeBuilding(Building building) {
        this.building = building;
        timeLeftOfConstruction = building.getDurationOfUpgrade();
    }

    @Override
    protected void construct() {
        building.upgrade();
    }
}