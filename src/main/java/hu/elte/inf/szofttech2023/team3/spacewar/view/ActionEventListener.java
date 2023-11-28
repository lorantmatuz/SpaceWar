package hu.elte.inf.szofttech2023.team3.spacewar.view;

import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;

@FunctionalInterface
public interface ActionEventListener {

    public void actionPerformed(ActionEvent actionEvent, GameState state);
    
}
