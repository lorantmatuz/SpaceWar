package hu.elte.inf.szofttech2023.team3.spacewar;

import hu.elte.inf.szofttech2023.team3.spacewar.controller.GameController;
import hu.elte.inf.szofttech2023.team3.spacewar.controller.generator.SpaceGenerator;
import hu.elte.inf.szofttech2023.team3.spacewar.controller.generator.cases.MinimalCaseSpaceGenerator;
import hu.elte.inf.szofttech2023.team3.spacewar.controller.generator.random.ObjectStatSpecs;
import hu.elte.inf.szofttech2023.team3.spacewar.controller.generator.random.RandomSpaceGenerator;
import hu.elte.inf.szofttech2023.team3.spacewar.display.SwingDisplayEngine;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.Player;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static final int BOARD_COLUMNS = 20;
    private static final int BOARD_ROWS = 20;

    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        players.add(new Player(1, "Blue"));
        players.add(new Player(2, "Red"));

        SpaceGenerator spaceGenerator = createSpaceGenerator(players);
        
        // FIXME
        Space tmpSpace = spaceGenerator.generate();
        SwingDisplayEngine display = new SwingDisplayEngine(tmpSpace.width, tmpSpace.height);
        
        GameController controller = new GameController(display, spaceGenerator, players);
        
        JButton shuffleButton = display.getShuffleButton();
        shuffleButton.addActionListener( ev -> controller.endTurn());
    }
    
    private static SpaceGenerator createSpaceGenerator(List<Player> players) {
        //return new RandomSpaceGenerator(BOARD_COLUMNS, BOARD_ROWS, Random::new, players, new ObjectStatSpecs(4, 10, 5, 5));
        return new MinimalCaseSpaceGenerator(players);
    }

}
