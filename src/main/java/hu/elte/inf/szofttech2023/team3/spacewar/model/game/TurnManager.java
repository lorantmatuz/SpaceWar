package hu.elte.inf.szofttech2023.team3.spacewar.model.game;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.SpaceObject;

import java.util.List;
import java.awt.Point;

public class TurnManager {
    private final List<Player> players;
    public final double maxActionPoint = 100;

    private Player currentPlayer;
    private int turnCounter = 0;
    private TurnState state = null;
    private int playerIndex = 0;
    private double actionPoint;
    private SpaceObject selectedObject;
    private Point targetPoint;
    private Player winner = null;

    public TurnManager(List<Player> players) {
        this.players = players;
        this.currentPlayer = nextPlayer();
    }

    public Player nextPlayer() {
        currentPlayer = players.get(playerIndex);
        if(++playerIndex >= players.size()) {
            playerIndex = 0;
            ++turnCounter;
        }
        state = TurnState.STARTED;
        actionPoint = maxActionPoint;
        selectedObject = null;
        targetPoint = null;
        return getCurrentPlayer();
    }

    public double decreaseActionPointBy(double points) {
        actionPoint -= points;
        return getActionPoint();
    }

    public void setSelectedObject(SpaceObject selectedObject) {
        this.selectedObject = selectedObject;
    }

    public void setTargetPoint(Point targetPoint) {
        this.targetPoint = targetPoint;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public double getActionPoint() {
        return actionPoint;
    }

    public TurnState getState() {
        return state;
    }

    public Point getTargetPoint() {
        return targetPoint;
    }

    public Player getWinner() {
        return winner;
    }

    public int getTurnCounter() {
        return turnCounter;
    }
}
