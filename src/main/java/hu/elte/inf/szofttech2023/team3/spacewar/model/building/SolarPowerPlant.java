package hu.elte.inf.szofttech2023.team3.spacewar.model.building;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;

/**
 * This class provides an implementation of the {@code SolarPowerPlant}
 * {@code Building} of a {@code Planet}.
 * Note that the {@code efficiency} of the building depends on both the
 * {@code level} and the {@code temperature} of its planet.
 */
public class SolarPowerPlant extends Building {
    // TODO: modify the efficiency consistently
    private int efficiency = planet.temperature;

    public SolarPowerPlant(Planet planet) {
        super(planet);
    }

    @Override
    public void upgrade() {
        ++level;
        efficiency = efficiencyOfLevel();
    }

    public int exportEnergy() {
        return efficiency;
    }

    // TODO: modify the efficiency of a level
    public int efficiencyOfLevel() {
        return level + planet.temperature ;
    }

}
