package hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects;

import java.util.*;

import hu.elte.inf.szofttech2023.team3.spacewar.model.building.*;

public class Planet extends SpaceObject {
    private String name;
    public int maxSize;
    public int temperature;
    private int size;
    //private Player owner = null;
    private int energy;
    private int material;
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


    public int getEnergy() {
        return energy;
    }

    public int getMaterial() {
        return material;
    }
    public int getTemperature(){ return temperature; }
    public int getMaxSize(){ return maxSize; }
    public int getSize(){ return size; }
    public String getName() { return name; }
    public void setEnergy( int energy ){ this.energy = energy; }
    public void setMaterial( int material ){ this.material = material; }
    public void setTemperature( int temperature ){ this.temperature = temperature; }
    public void setMaxSize( int maSize ){  this.maxSize = maxSize; }
    public void setName( String name ){ this.name = name; }

    public Map<Class<? extends Building>, Building> getBuildingMap() {
        return buildingMap;
    }
}
