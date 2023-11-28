package hu.elte.inf.szofttech2023.team3.spacewar.model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Fleet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Spaceship;

public final class Battle {
    private static final ThreadLocalRandom rnd = ThreadLocalRandom.current();

    public static void fight(Fleet attacker, Fleet defender) {
        final List<Spaceship> attackerList = fleetToList(attacker);
        final List<Spaceship> defenderList = fleetToList(defender);
        Fleet fleet = attacker;
        while(!attackerList.isEmpty() && !defenderList.isEmpty()) {
            var att = randomShip(attackerList);
            var def = randomShip(defenderList);
            // TODO:
        }
    }

    private static List<Spaceship> fleetToList(Fleet fleet) {
        List<Spaceship> res = new ArrayList<>();
        final var ships = fleet.getSpaceships();
        for(var ship : ships) {
            System.out.println(ship);
            if(ship != null) {
                res.add(ship);
            }
        }
        return res;
    }

    private static Spaceship randomShip(List<Spaceship> list) {
        if(list.isEmpty()) {
            return null;
        }
        return list.get(rnd.nextInt(list.size()));
    }
}
