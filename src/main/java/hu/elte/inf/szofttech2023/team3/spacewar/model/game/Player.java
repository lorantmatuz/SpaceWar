package hu.elte.inf.szofttech2023.team3.spacewar.model.game;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Fleet;

import java.util.ArrayList;
import java.util.List;

public class Player
{
    private final String name;
    private final List<Planet> planets = new ArrayList<>();
    private final List<Fleet> fleets = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public void addPlanet(Planet planet) {
        planets.add(planet);
    }

    public void addFleet(Fleet fleet) {
        fleets.add(fleet);
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
}
