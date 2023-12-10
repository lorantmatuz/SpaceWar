package hu.elte.inf.szofttech2023.team3.spacewar.model.game;

import hu.elte.inf.szofttech2023.team3.spacewar.view.FieldPosition;

import java.util.List;
import java.awt.Point;

public class TurnManager {
    private final List<Player> players;
    public final double maxActionPoint = 100;

    private Player currentPlayer;
    private int turnCounter = 1;
    private TurnState state = null;
    private int playerIndex = 0;
    private double actionPoint;
    private Point targetPoint;
    private Player winner = null;
    private FieldPosition selectedPosition = null;
    private List<FieldPosition> plannedPath = null;

    public TurnManager(List<Player> players) {
        this.players = players;
        this.currentPlayer = nextPlayer();
    }

    public Player nextPlayer() {
        currentPlayer = players.get(playerIndex++);

        if (playerIndex >= players.size()) {
            playerIndex = 0;
            ++turnCounter;
        }

        currentPlayer.checkConstructions();
        state = TurnState.STARTED;
        actionPoint = maxActionPoint;
        targetPoint = null;
        return getCurrentPlayer();
    }


    public double decreaseActionPointBy(double points) {
        actionPoint -= points;
        return getActionPoint();
    }

    public void setTargetPoint(Point targetPoint) {
        this.targetPoint = targetPoint;
    }

    public void setSelectedPosition(FieldPosition selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public void setPlannedPath(List<FieldPosition> plannedPath) {
        this.plannedPath = plannedPath;
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

    public FieldPosition getSelectedPosition() {
        return selectedPosition;
    }

    public List<FieldPosition> getPlannedPath() {
        return plannedPath;
    }

    public Player getWinner() {
        return winner;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public void decreaseActionPointBy(int points) {
        this.actionPoint -= points;
        if (this.actionPoint < 0) {
            this.actionPoint = 0;
        }
    }
}
