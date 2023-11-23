package hu.elte.inf.szofttech2023.team3.spacewar.model;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;

public class GameState {
    private boolean playerIsActive = false;
    private final Space space;
    
    public GameState(Space space) {
        this.space = space;
    }
    
    public Space getSpace() {
        return space;
    }
    public boolean getActionState(){
        return playerIsActive;
    }

    public void setActionState(boolean active) {
        this.playerIsActive = active;

    }
}
