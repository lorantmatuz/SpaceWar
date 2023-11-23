package hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects;

import java.util.*;

import hu.elte.inf.szofttech2023.team3.spacewar.model.building.*;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.Player;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.construction.UpgradeBuilding;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.SpaceshipEnum;

import static hu.elte.inf.szofttech2023.team3.spacewar.model.building.BuildingEnum.*;

public class Planet extends SpaceObject {
    private static int id = -1;
    public final int maxSize = 3 * 1024;
    public final int temperature = 100;
    private int size = 1;
    private Player owner = null;
    private int energy = 0;
    private int material = 0;
    private final Map<BuildingEnum, Building> buildingMap = new HashMap<>();

    public Planet(int x, int y) {
        super(x,y);
        ++id;
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

    public int getId() {
        return id;
    }

    public Player getOwner() {
        return owner;
    }
}
