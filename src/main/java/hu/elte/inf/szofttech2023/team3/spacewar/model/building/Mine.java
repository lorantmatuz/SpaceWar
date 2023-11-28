package hu.elte.inf.szofttech2023.team3.spacewar.model.building;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;

/**
 * This class provides an implementation of the {@code Mine}
 * {@code Building} of a {@code Planet}.
 * Note that the {@code amount} of the {@code Building} depends on
 * the {@code level}.
 */
public class Mine extends Building {
    // TODO: modify the period, minEnergy, amount consistently
    private final int peroid = 2;
    private final int minEnergy = 10;
    private int amount = 1;

    public Mine(Planet planet) {
        super(planet);
    }

    @Override
    public void upgrade() {
        ++level;
        amount = amountOfLevel();
    }

    // TODO: adapt the period for exporting
    public int exportMaterial() {
        return planet.getEnergy() > minEnergy ? amount : 0;
    }

    // TODO: modify the amount of a level consistently
    public int amountOfLevel() {
        return level;
    }

}
