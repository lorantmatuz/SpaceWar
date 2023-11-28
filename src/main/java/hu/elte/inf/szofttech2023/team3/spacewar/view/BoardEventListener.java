package hu.elte.inf.szofttech2023.team3.spacewar.view;

import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;

@FunctionalInterface
public interface BoardEventListener {

    public void actionPerformed(BoardEvent boardEvent, GameState state);
    
}
