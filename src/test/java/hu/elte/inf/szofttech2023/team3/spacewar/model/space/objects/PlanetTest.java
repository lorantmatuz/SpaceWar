package hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Color;

import hu.elte.inf.szofttech2023.team3.spacewar.model.building.*;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.Player;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;

public class PlanetTest {
    private Planet planet;

    @BeforeEach
    public void beforeEach() {
        planet = new Planet(0, 0, new Player(1, "Test Player", Color.WHITE), new Space(5, 5));
    }

    @Test
    public void buildTest() {
        Building solarPowerPlant = planet.build(BuildingEnum.SOLAR_POWER_PLANT);
        /* Successfully built */
        assertThat(solarPowerPlant).isNotNull();
        Building solarPowerPlant2 = planet.build(BuildingEnum.SOLAR_POWER_PLANT);
        /* Same object */
        assertThat(solarPowerPlant).isSameAs(solarPowerPlant2);
        /* Not generated yet */
        //assertThat(planet.getBuilding(BuildingEnum.MINE)).isNull();
        Building mine = planet.build(BuildingEnum.MINE);
        /* Successfully built */
        assertThat(mine).isNotNull();
        Building factory = planet.build(BuildingEnum.SPACE_SHIP_FACTORY);
        /* Successfully built */
        assertThat(factory).isNotNull();
    }

    @Test
    public void upgradeTest() {
        Building mine = planet.build(BuildingEnum.MINE);
        assertThat(mine.getLevel()).isEqualTo(1);
        //planet.upgrade(BuildingEnum.MINE);
        //assertThat(mine.getLevel()).isEqualTo(2);
    }
}
