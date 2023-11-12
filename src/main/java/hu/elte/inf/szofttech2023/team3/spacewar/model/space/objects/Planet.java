package hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects;

import hu.elte.inf.szofttech2023.team3.spacewar.model.building.*;
import hu.elte.inf.szofttech2023.team3.spacewar.model.building.Building;
import hu.elte.inf.szofttech2023.team3.spacewar.model.building.Mine;
import hu.elte.inf.szofttech2023.team3.spacewar.model.building.SolarPowerPlant;

import java.util.*;

public class Planet extends SpaceObject {
    public final double maxSize = 3 * 1024;
    public final int temperature = 100;
    private double size = 1.0;
    //private Player owner = null;
    private double energy = 0;
    private double material = 0;
    private final Map<Class<? extends Building>, Building> buildingMap
            = new HashMap<>();

    public Planet(int x, int y) {
        super(x,y);
    }

    public Building build(Class<? extends Building> type) {
        Building building = getBuilding(type);
        if(building == null) {
            try {
                building = type.getDeclaredConstructor(Planet.class)
                                .newInstance(this);
                buildingMap.put(type, building);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return building;
    }


    public boolean upgrade(Class<? extends Building> type) {
        Building building = getBuilding(type);
        if(building == null) {
           return false;
        }
        final double cost = building.upgradeCostOfLevel();
        if(cost + size > maxSize) {
            return false;
        }
        size += cost;
        building.upgrade();
        return true;
    }

    public boolean importEnergy() {
        Building building = getBuilding(SolarPowerPlant.class);
        if(building != null) {
            this.energy += ((SolarPowerPlant) building).exportEnergy();
        }
        return building != null;
    }

    public boolean importMaterial() {
        Building building = getBuilding(Mine.class);
        if(building != null) {
            this.material += ((Mine) building).exportMaterial();
        }
        return building != null;
    }

    public Building getBuilding(Class<? extends Building> type) {
        return buildingMap.get(type);
    }


    public double getEnergy() {
        return energy;
    }

    public double getMaterial() {
        return material;
    }
}
