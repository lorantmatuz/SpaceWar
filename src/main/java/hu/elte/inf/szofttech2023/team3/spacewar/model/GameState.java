package hu.elte.inf.szofttech2023.team3.spacewar.model;

import hu.elte.inf.szofttech2023.team3.spacewar.model.game.Player;
import hu.elte.inf.szofttech2023.team3.spacewar.model.game.TurnManager;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ShortestPath;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.SpaceObject;

import java.util.List;

public class GameState {

    private final Space space;
    private final List<Player> players;
    private final TurnManager turnManager;
    private SpaceObject selectedObject;
    public final ShortestPath shortestPath;


    public GameState(Space space, List<Player> players) {
        this.space = space;
        this.shortestPath = new ShortestPath(space);
        this.players = players;
        this.turnManager = new TurnManager(players);
        this.selectedObject = null;
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }

    public Space getSpace() {
        return space;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public SpaceObject getSelectedObject(){ return this.selectedObject; }

    public void setSelectedObject(SpaceObject selectedObject) {
        this.selectedObject = selectedObject;
    }

    public ShortestPath getShortestPath() {
        return shortestPath;
    }
}
