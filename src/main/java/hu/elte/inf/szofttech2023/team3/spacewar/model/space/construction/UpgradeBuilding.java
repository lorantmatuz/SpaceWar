package hu.elte.inf.szofttech2023.team3.spacewar.model.space.construction;

import hu.elte.inf.szofttech2023.team3.spacewar.model.building.Building;
import hu.elte.inf.szofttech2023.team3.spacewar.model.building.BuildingEnum;

public class UpgradeBuilding extends Constructable {
    private final BuildingEnum type;
    private final Building building;


    public UpgradeBuilding(Building building, int timeLeftOfConstruction) {
        this.type = null;
        this.building = building;
        this.timeLeftOfConstruction = timeLeftOfConstruction;
        building.setFunctional(false);
    }
    public UpgradeBuilding(BuildingEnum type, Building building, int timeLeftOfConstruction) {
        this.type = type;
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
    public BuildingEnum getBuildingType(){ return type; }


}