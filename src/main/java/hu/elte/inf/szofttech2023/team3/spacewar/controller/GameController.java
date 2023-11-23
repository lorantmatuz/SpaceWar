package hu.elte.inf.szofttech2023.team3.spacewar.controller;

import hu.elte.inf.szofttech2023.team3.spacewar.display.SpecialAction;
import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.GenerateSpace;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.SpaceObject;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Spaceship;
import hu.elte.inf.szofttech2023.team3.spacewar.view.FieldPosition;
import hu.elte.inf.szofttech2023.team3.spacewar.view.GameStateRenderer;

public class GameController {

    private final  GameState gameState;
    
    private final GameStateRenderer renderer;
    
    public GameController(GameState gameState, GameStateRenderer renderer) {
        this.gameState = gameState;
        this.renderer = renderer;
    }
    
    public void shuffle() {
        // TODO
        Space space = gameState.getSpace();
        GenerateSpace generator = new GenerateSpace(space);
        generator.run(10, 5, 5);
        renderer.apply(gameState, this::handleAnyAction);
    }

    public void handleAnyAction(Object target, GameState state) {

        // Player already selected a fleet.
        if(state.getActionState()){
            if(target instanceof Spaceship) {
                ////((Spaceship) activeFleet).attack(target);
                // TODO: Ellenséges hajó támadása / saját hajó flottalapítás, ha a saját hajó anyahajó vagy a cél anyahajó
            }else if (target instanceof FieldPosition) {
                FieldPosition position = (FieldPosition) target;
                int row = position.getRow();
                int column = position.getColumn();
                System.out.println("Kattintás az űrben: Sor=" + row + ", Oszlop=" + column);
                System.out.println("Itt vagyok");
                // TODO: További logika az űrben történt kattintásra
            }else if (target instanceof Planet) {
                Planet planet = (Planet) target;
                // TODO: Ha ellenséges, bolygó akkor támadás, ha saját akkor kilépünk.
            }


        }
        else{
            if(target instanceof Spaceship){
                //if(state.getActivePlayer().hasShip(target))
                Spaceship spaceship = (Spaceship) target;
                System.out.println("Spaceship state:");
                System.out.println("Name: " + spaceship.name());
                System.out.println("Everything: " + spaceship.toString());
                state.setActionState(true);
            }else if (target instanceof Planet) {
                Planet planet = (Planet) target;
                System.out.println("Planet state:");
                System.out.println("Energy: " + planet.getEnergy());
                System.out.println("Material: " + planet.getMaterial());
                state.setActionState(true);
            }
        }

        if (target == SpecialAction.SHUFFLE) {
            shuffle();
        }else if (target instanceof SpaceObject) {
            // TODO
            System.out.println(String.format("A(n) %s object was clicked", target.getClass().getSimpleName()));
        }
        else {
            System.err.println(String.format("Unknown object type %s", target.getClass()));

        }

    }
    
}
