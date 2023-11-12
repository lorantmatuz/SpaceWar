package hu.elte.inf.szofttech2023.team3.spacewar.space;

import hu.elte.inf.szofttech2023.team3.spacewar.model.building.Building;
import hu.elte.inf.szofttech2023.team3.spacewar.model.building.Mine;
import hu.elte.inf.szofttech2023.team3.spacewar.model.building.SolarPowerPlant;
import hu.elte.inf.szofttech2023.team3.spacewar.model.building.SpaceShipFactory;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PlanetTest {
    private Planet planet;

    @BeforeEach
    public void beforeEach() {
        planet = new Planet(0,0);
    }

    @Test
    public void buildTest() {
        Building solarPowerPlant = planet.build(SolarPowerPlant.class);
        /* Successfully built */
        assertThat(solarPowerPlant).isNotNull();
        Building solarPowerPlant2 = planet.build(SolarPowerPlant.class);
        /* Same object */
        assertThat(solarPowerPlant).isSameAs(solarPowerPlant2);
        /* Not generated yet */
        assertThat(planet.getBuilding(Mine.class)).isNull();
        Building mine = planet.build(Mine.class);
        /* Successfully built */
        assertThat(mine).isNotNull();
        Building factory = planet.build(SpaceShipFactory.class);
        /* Successfully built */
        assertThat(factory).isNotNull();
    }

    @Test
    public void upgradeTest() {
        Building mine = planet.build(Mine.class);
        assertThat(mine.getLevel()).isEqualTo(1);
        planet.upgrade(Mine.class);
        assertThat(mine.getLevel()).isEqualTo(2);
    }
}
