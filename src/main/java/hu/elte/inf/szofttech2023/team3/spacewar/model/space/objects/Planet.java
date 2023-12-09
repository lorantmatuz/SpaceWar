package hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects;

import hu.elte.inf.szofttech2023.team3.spacewar.model.building.*;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.Player;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.construction.UpgradeBuilding;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.SpaceshipEnum;
import hu.elte.inf.szofttech2023.team3.spacewar.view.FieldPosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hu.elte.inf.szofttech2023.team3.spacewar.model.building.BuildingEnum.*;

public class Planet extends SpaceObject {
    
    private String name = "";
    private int maxSize = 3 * 1024;
    private int temperature = 100;
    private int size = 1;
    private Player owner;
    private int energy = 0;
    private int material = 0;
    private final Map<BuildingEnum, Building> buildingMap = new HashMap<>();

    public Planet(FieldPosition position, Player owner) {
        super(position);
        this.owner = owner;
    }
    
    public Planet(int x, int y, Player owner) {
        super(x,y);
        this.owner = owner;
    }

    public void colonize(Player player) {
        owner = player;
        player.addPlanet(this);
    }

    public Building build(BuildingEnum buildingEnum) {
        Building building = getBuilding(buildingEnum);
        if(building == null) {
            building = buildingEnum.build(this);
            buildingMap.put(buildingEnum, building);
        } else {
            building.upgrade();
        }
        return building;
    }

    public boolean scheduleUpgrade(BuildingEnum buildingEnum) {
        final var building = getBuilding(buildingEnum);
        if(building == null) {
           return false;
        }
        final int cost = building.getUpgradeCostOfLevel();
        if(cost + size > maxSize) {
            return false;
        }
        owner.addConstruction(new UpgradeBuilding(building));
        size += cost;
        return true;
    }

    public boolean importEnergy() {
        Building building = getBuilding(SOLAR_POWER_PLANT);
        if(building != null) {
            this.energy += ((SolarPowerPlant) building).exportEnergy();
            return true;
        }
        return false;
    }

    public boolean importMaterial() {
        Building building = getBuilding(MINE);
        if(building != null) {
            this.material += ((Mine) building).exportMaterial();
            return true;
        }
        return false;
    }

    public void produceShip(SpaceshipEnum ship) {
        Building building = getBuilding(SPACE_SHIP_FACTORY);
        if(building != null) {
             ((SpaceShipFactory)building).produce(ship);
        }
    }

    private List<Building> buildingsToList() {
        final List<Building> list = new ArrayList<>();
        for(final var buildingEnum : BuildingEnum.values()) {
            final var building = getBuilding(buildingEnum);
            if(building != null) {
                list.add(building);
            }
        }
        return list;
    }

    private Building getBuilding(BuildingEnum buildingEnum) {
        return buildingMap.get(buildingEnum);
    }

    public int getEnergy() {
        return energy;
    }

    public int getMaterial() {
        return material;
    }

    public Player getOwner() {
        return owner;
    }
    
    public int getTemperature(){ return temperature; }
    public int getMaxSize(){ return maxSize; }
    public int getSize(){ return size; }
    public String getName() { return name; }
    public void setEnergy( int energy ){ this.energy = energy; }
    public void setMaterial( int material ){ this.material = material; }
    public void setTemperature( int temperature ){ this.temperature = temperature; }
    public void setMaxSize( int maxSize ){  this.maxSize = maxSize; }
    public void setName( String name ){ this.name = name; }

    public Map<BuildingEnum, Building> getBuildingMap() {
        return buildingMap;
    }

    public void setOwner(Player name) {
       this.owner = name;
    }
}
