package hu.elte.inf.szofttech2023.team3.spacewar.model.game;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Fleet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Spaceship;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.SpaceshipEnum;

public final class Battle {
    private static final ThreadLocalRandom rnd = ThreadLocalRandom.current();
    private static List<Spaceship> attackerList;
    private static List<Spaceship> defenderList;
    private static int swapCounter = 0;

    public static boolean fight(Fleet attacker, Fleet defender) {
        if(!attacker.canAttack()) {
            throw new IllegalArgumentException("Invalid attack, no mother_ship in fleet");
        }

        attackerList = attacker.getSpaceships();
        defenderList = defender.getSpaceships();

        while (true) {
            final var attackerShip = randomShip(attackerList);
            final var defenderShip = randomShip(defenderList);
            if (attackerShip.attack(defenderShip)) {
                defenderList.remove(defenderShip);
                if(defenderList.isEmpty()) {
                    return winner();
                }
            }
            swap();
        }
    }

    private static Spaceship randomShip(List<Spaceship> list) {
        return list.get(rnd.nextInt(list.size()));
    }

    private static void swap() {
        ++swapCounter;
        final var temporaryList = attackerList;
        attackerList = defenderList;
        defenderList = temporaryList;
    }

    private static boolean winner() {
        return swapCounter % 2 == 0;
    }

    public static void main(String[] args) {
        Player p = new Player(2, "John");
        Fleet f1 = new Fleet(0,1, p);
        f1.addShip(new Spaceship(SpaceshipEnum.MOTHER_SHIP));
        Fleet f2 = new Fleet(2,3, p);
        f2.addShip(new Spaceship(SpaceshipEnum.COLONY));
        System.out.println(Battle.fight(f1,f2));
    }
}
