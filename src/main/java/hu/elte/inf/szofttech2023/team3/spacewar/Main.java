package hu.elte.inf.szofttech2023.team3.spacewar;

import hu.elte.inf.szofttech2023.team3.spacewar.controller.GameController;
import hu.elte.inf.szofttech2023.team3.spacewar.display.SwingDisplayEngine;
import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.Player;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;
import hu.elte.inf.szofttech2023.team3.spacewar.view.GameStateRenderer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int BOARD_COLUMNS = 20;
    private static final int BOARD_ROWS = 20;

    public static void main(String[] args) {
        Space space = new Space(BOARD_COLUMNS, BOARD_ROWS);

        List<Player> players = new ArrayList<>();
        players.add(new Player(1, "Blue"));
        players.add(new Player(2, "Red"));
        GameState state = new GameState(space, players);

        //SwingBoardDisplay display = new SwingBoardDisplay(BOARD_ROWS, BOARD_COLUMNS, FIELD_WIDTH, FIELD_HEIGHT);
        SwingDisplayEngine display = new SwingDisplayEngine(BOARD_ROWS, BOARD_COLUMNS);

        GameStateRenderer renderer = new GameStateRenderer(display);
        GameController controller = new GameController(state, renderer);
        ///controller.shuffle();
        JButton shuffleButton = display.getShuffleButton();
        shuffleButton.addActionListener( ev -> controller.endTurn());
    }

}
