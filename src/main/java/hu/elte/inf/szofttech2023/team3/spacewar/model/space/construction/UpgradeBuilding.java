package hu.elte.inf.szofttech2023.team3.spacewar.model.space.construction;

import hu.elte.inf.szofttech2023.team3.spacewar.model.building.Building;

public class UpgradeBuilding extends Constructable {
    private final Building building;


    public UpgradeBuilding(Building building, int timeLeftOfConstruction) {
        this.building = building;
        this.timeLeftOfConstruction = timeLeftOfConstruction;
        building.setFunctional(false);
    }



    @Override
    protected void construct() {
        building.upgrade();
        building.setFunctional(true);
    }

    public Building getBuilding() {
        return building;
    }


}