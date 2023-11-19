package hu.elte.inf.szofttech2023.team3.spacewar.model.building;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;

public enum BuildingEnum {
    MINE(Mine::new),
    SOLAR_POWER_PLANT(SolarPowerPlant::new),
    SPACE_SHIP_FACTORY(SpaceShipFactory::new);

    private final BuildingFactory factory;

    BuildingEnum(BuildingFactory factory) {
        this.factory = factory;
    }

    public Building build(Planet planet) {
        return factory.build(planet);
    }

    @FunctionalInterface
    private interface BuildingFactory {
        Building build(Planet planet);
    }

}
