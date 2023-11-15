package hu.elte.inf.szofttech2023.team3.spacewar.model.game;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.SpaceObject;

import java.util.List;
import java.awt.Point;


public class TurnManager {
    private final List<Player> players;
    public final double maxActionPoint = 100;

    private TurnState state = null;
    private int playerIndex = 0;
    private double actionPoint;
    private SpaceObject selectedObject;
    private Point targetPoint;
    private Player winner = null;


    public TurnManager(List<Player> players) {
        this.players = players;
    }

    public Player nextPlayer() {
        final var player = players.get(playerIndex);
        harvestFromPlanets(player);
        playerIndex = (playerIndex + 1) % players.size();
        state = TurnState.STARTED;
        actionPoint = maxActionPoint;
        selectedObject = null;
        targetPoint = null;
        return player;
    }

    private void harvestFromPlanets(Player player) {
        for(final var planet : player.getPlanets()) {
            planet.importEnergy();
            planet.importMaterial();
        }
    }

    public double decreaseActionPointBy(double points) {
        actionPoint -= points;
        return getActionPoint();
    }

    public void setState(TurnState state) {
        this.state = state;
    }

    public void setSelectedObject(SpaceObject selectedObject) {
        this.selectedObject = selectedObject;
    }

    public void setTargetPoint(Point targetPoint) {
        this.targetPoint = targetPoint;
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

}
