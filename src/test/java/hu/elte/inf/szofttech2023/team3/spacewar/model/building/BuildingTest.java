package hu.elte.inf.szofttech2023.team3.spacewar.model.building;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hu.elte.inf.szofttech2023.team3.spacewar.model.game.Player;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Color;

public class BuildingTest {
    final Planet planet = new Planet(0, 0, new Player(1, "Test Player", Color.WHITE), new Space(5, 5));
    Building solar, mine, factory;

    @BeforeEach
    public void beforeEach() {
        solar = new SolarPowerPlant(planet);
        mine = new Mine(planet);
        factory = new SpaceShipFactory(planet);
    }

    private void levelTest(Building building, int level) {
        assertThat(building.getLevel()).isEqualTo(level);
    }

    @Test
    public void initTest() {
        levelTest(solar,1);
        levelTest(mine,1);
        levelTest(factory,1);
    }

    @Test
    public void upgradeTest() {
        solar.upgrade();
        mine.upgrade();
        factory.upgrade();
        levelTest(solar,2);
        levelTest(mine,2);
        levelTest(factory,2);
    }
    
}
