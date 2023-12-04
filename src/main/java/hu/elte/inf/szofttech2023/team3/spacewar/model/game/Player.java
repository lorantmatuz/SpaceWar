package hu.elte.inf.szofttech2023.team3.spacewar.model.game;

import hu.elte.inf.szofttech2023.team3.spacewar.model.building.Building;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.construction.ConstructSpaceship;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.construction.Constructable;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.construction.UpgradeBuilding;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Fleet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.SpaceshipEnum;

import java.util.ArrayList;
import java.util.List;

public class Player
{
    private final int no;
    private final String name;
    private final List<Planet> planets = new ArrayList<>();
    private final List<Fleet> fleets = new ArrayList<>();
    private final List<Constructable> constructions = new ArrayList<>();

    public Player(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public void addConstruction(Constructable construction) {
        constructions.add(construction);
    }

    public void checkConstructions() {
        constructions.removeIf(Constructable::isEndOfConstruction);
    }

    public void addPlanet(Planet planet) {
        planets.add(planet);
    }

    public void addFleet(Fleet fleet) {
        fleets.add(fleet);
    }

    public boolean removeFleet(Fleet fleet) {
        return fleets.remove(fleet);
    }

    public int getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public List<Fleet> getFleets() {
        return fleets;
    }

    public List<Constructable> getConstructions() {
        return constructions;
    }
    
    public List<Building> getListOfConstructingBuildings() {
        List<Building> list = new ArrayList<>();
        for(final var construction : constructions) {
            if(construction instanceof UpgradeBuilding upgradeBuilding) {
                list.add(upgradeBuilding.getBuilding());
            }
        }
        return list;
    }

    public List<SpaceshipEnum> getListOfConstructingShips() {
        List<SpaceshipEnum> list = new ArrayList<>();
        for(final var construction : constructions) {
            if(construction instanceof ConstructSpaceship constructingShips) {
                list.add(constructingShips.getSpaceship());
            }
        }
        return list;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof Player player) {
            return this.no == player.getNo() && this.name.equals(player.getName());
        }
        return false;
    }
    
}
