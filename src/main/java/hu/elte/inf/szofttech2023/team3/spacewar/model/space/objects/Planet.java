package hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects;

import java.util.*;

import hu.elte.inf.szofttech2023.team3.spacewar.model.building.*;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.Player;

import static hu.elte.inf.szofttech2023.team3.spacewar.model.building.BuildingEnum.*;

public class Planet extends SpaceObject {
    public final double maxSize = 3 * 1024;
    public final int temperature = 100;
    private double size = 1.0;
    private Player owner = null;
    private double energy = 0;
    private double material = 0;
    private final Map<BuildingEnum, Building> buildingMap = new HashMap<>();

    public Planet(int x, int y) {
        super(x,y);
    }

    public void colonize(Player player) {
        owner = player;
    }

    public Building build(BuildingEnum buildingEnum) {
        var building = getBuilding(buildingEnum);
        if(building == null) {
            building = buildingEnum.build(this);
            buildingMap.put(buildingEnum, building);
        }
        return building;
    }

    public boolean scheduleUpgrade(BuildingEnum buildingEnum) {
        final var building = getBuilding(buildingEnum);
        if(building == null) {
           return false;
        }
        final double cost = 0;
        // TODO:
        //final double cost = building.upgradeCostOfLevel();
        if(cost + size > maxSize) {
            return false;
        }
        size += cost;
        building.scheduleUpgrade();
        return true;
    }

    public boolean importEnergy() {
        Building building = getBuilding(SOLAR_POWER_PLANT);
        if(building != null) {
            this.energy += ((SolarPowerPlant) building).exportEnergy();
        }
        return building != null;
    }

    public boolean importMaterial() {
        Building building = getBuilding(MINE);
        if(building != null) {
            this.material += ((Mine) building).exportMaterial();
        }
        return building != null;
    }

    public Building getBuilding(BuildingEnum buildingEnum) {
        return buildingMap.get(buildingEnum);
    }

    public double getEnergy() {
        return energy;
    }

    public double getMaterial() {
        return material;
    }
}
