package hu.elte.inf.szofttech2023.team3.spacewar.view;

import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;

@FunctionalInterface
public interface GameActionListener {

    public void actionPerformed(Object target, GameState state);
    
}
