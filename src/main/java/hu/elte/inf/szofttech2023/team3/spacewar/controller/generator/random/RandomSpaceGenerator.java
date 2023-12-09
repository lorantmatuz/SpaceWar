package hu.elte.inf.szofttech2023.team3.spacewar.controller.generator.random;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import hu.elte.inf.szofttech2023.team3.spacewar.controller.generator.SpaceGenerator;
import hu.elte.inf.szofttech2023.team3.spacewar.model.building.BuildingEnum;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.Player;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Asteroid;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.BlackHole;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.SpaceObject;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Fleet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Spaceship;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.SpaceshipEnum;
import hu.elte.inf.szofttech2023.team3.spacewar.view.FieldPosition;

public class RandomSpaceGenerator implements SpaceGenerator {
    
    private final int width;
    
    private final int height;
    
    private final Supplier<Random> randomSupplier;
    
    private final List<Player> players;
    
    private final ObjectStatSpecs objectStatSpecs;
    
    public RandomSpaceGenerator(int width, int height, Supplier<Random> randomSupplier, List<Player> players, ObjectStatSpecs objectStatSpecs) {
        this.width = width;
        this.height = height;
        this.randomSupplier = randomSupplier;
        this.players = new ArrayList<>(players);
        this.objectStatSpecs = objectStatSpecs;
    }

    public Space generate() {
        Space space = new Space(width, height);
        Random random = randomSupplier.get();
        int numOfFleets = objectStatSpecs.numOfFleets();
        int numOfPlanets = objectStatSpecs.numOfPlanets();
        int numOfBlackHoles = objectStatSpecs.numOfBlackHoles();
        int numOfAsteroids = objectStatSpecs.numOfAsteroids();
        System.out.println("rendering fleets...");
        create(random, space, numOfFleets / 2, p -> createFleet(p, players.get(0)));
        create(random, space, numOfFleets - (numOfFleets / 2), p -> createFleet(p, players.get(1)));
        System.out.println("rendering planets...");
        create(random, space, numOfPlanets / 2, p -> new Planet(p, players.get(0)));
        create(random, space, numOfPlanets - (numOfPlanets / 2), p -> new Planet(p.getColumn(), p.getRow(), players.get(1)));
        System.out.println("rendering blackholes...");
        create(random, space, numOfBlackHoles, BlackHole::new);
        System.out.println("rendering asteroids...");
        create(random, space, numOfAsteroids, Asteroid::new);
        return space;
    }

    private Fleet createFleet(FieldPosition position, Player player) {
        Fleet fleet = new Fleet(position, player);
        fleet.addShip(new Spaceship(SpaceshipEnum.MOTHER_SHIP));
        return fleet;
    }

    private void create(Random random, Space space, int numOfObjects, SpaceObjectFactory factory) {
        for (int j = 0; j < numOfObjects; j++) {
            FieldPosition position = findFreeSpace(random, space);
            try {
                SpaceObject obj = factory.create(position);
                space.setSpaceObject(obj);
                if(obj instanceof Planet planet) {
                    planet.setEnergy(100);
                    planet.setMaterial(100);
                    planet.setMaxSize(random.nextInt(10, 1000));
                    planet.setTemperature(random.nextInt(10, 1000));
                    planet.setName(obj.getClass().getSimpleName() + "-" + (j + 1));
                    planet.build(BuildingEnum.MINE);
                    planet.build(BuildingEnum.SOLAR_POWER_PLANT);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private FieldPosition findFreeSpace(Random random, Space space) {
        int row;
        int column;
        do {
            column = random.nextInt(space.width);
            row = random.nextInt(space.height);
        }
        while(space.isSpaceObject[column][row]);
        return FieldPosition.of(row, column);
    }
    
    
    @FunctionalInterface
    private interface SpaceObjectFactory {
        
        public SpaceObject create(FieldPosition position);
        
    }
    
}
