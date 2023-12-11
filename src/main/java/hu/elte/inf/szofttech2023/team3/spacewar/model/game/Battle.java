package hu.elte.inf.szofttech2023.team3.spacewar.model.game;

import java.util.concurrent.ThreadLocalRandom;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Fleet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Spaceship;

public final class Battle {
    private static final ThreadLocalRandom rnd = ThreadLocalRandom.current();
    private static Fleet attacker, defender;
    private static int swapCounter = 0;

    public static boolean fight(Fleet a, Fleet d) {
        attacker = a;
        defender = d;
        if(!attacker.canAttack()) {
            throw new IllegalArgumentException("Invalid attack, no mother_ship in fleet");
        }

        while (true) {
            final var attackerShip = randomShip(attacker);
            final var defenderShip = randomShip(defender);
            if (attackerShip.attack(defenderShip)) {
                defender.removeShip(defenderShip);
                if(defender.getSpaceships().isEmpty()) {
                    return winner();
                }
            }
            swap();
        }
    }

    private static Spaceship randomShip(Fleet fleet) {
        final var list = fleet.getSpaceships();
        return list.get(rnd.nextInt(list.size()));
    }

    private static void swap() {
        ++swapCounter;
        final var temporary = attacker;
        attacker = defender;
        defender = temporary;
    }

    private static boolean winner() {
        return swapCounter % 2 == 0;
    }
}
