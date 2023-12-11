package hu.elte.inf.szofttech2023.team3.spacewar.model.space.construction;

import hu.elte.inf.szofttech2023.team3.spacewar.model.building.Building;
import hu.elte.inf.szofttech2023.team3.spacewar.model.building.BuildingEnum;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;

public class ConstructBuilding extends Constructable {
    private final BuildingEnum buildingType;
    private final Planet planet;

    public ConstructBuilding(Planet planet, BuildingEnum buildingType, int constructionTime) {
        this.planet = planet;
        this.buildingType = buildingType;
        this.timeLeftOfConstruction = constructionTime;
    }

    @Override
    protected void construct() {
        Building building = planet.getBuilding(buildingType);
        if (building == null) {
            building = buildingType.build(planet);
            planet.getBuildingMap().put(buildingType, building);
        } else {
            building.upgrade();
        }
    }

    public BuildingEnum getBuildingType(){ return this.buildingType; }
}
