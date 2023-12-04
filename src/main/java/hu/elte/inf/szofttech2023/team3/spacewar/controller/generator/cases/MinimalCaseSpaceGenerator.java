package hu.elte.inf.szofttech2023.team3.spacewar.controller.generator.cases;

import java.util.ArrayList;
import java.util.List;

import hu.elte.inf.szofttech2023.team3.spacewar.controller.generator.SpaceGenerator;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.Player;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.view.FieldPosition;

public class MinimalCaseSpaceGenerator implements SpaceGenerator {
    
    private final List<Player> players;
    
    public MinimalCaseSpaceGenerator(List<Player> players) {
        this.players = new ArrayList<>(players);
    }

    @Override
    public Space generate() {
        Space space = new Space(5, 5);
        space.setSpaceObject(new Planet(FieldPosition.of(1, 2), players.get(0)));
        return space;
    }

}
