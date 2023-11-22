package hu.elte.inf.szofttech2023.team3.spacewar.view;

import hu.elte.inf.szofttech2023.team3.spacewar.display.*;
import hu.elte.inf.szofttech2023.team3.spacewar.model.building.*;
import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Asteroid;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.BlackHole;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.SpaceObject;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Fleet;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

public class GameStateRenderer {
    
    private final DisplayEngine displayEngine;

    public GameStateRenderer(DisplayEngine displayEngine) {
        this.displayEngine = displayEngine;
    }
    
    public void apply(GameState gameState, GameActionListener listener) {
        BoardDisplay boardDisplay = displayEngine.getBoardDisplay();
        int rowCount = boardDisplay.getRowCount();
        int columnCount = boardDisplay.getColumnCount();
        Displayable[][] displayables = new Displayable[rowCount][columnCount];
        Space space = gameState.getSpace();
        for (int j = 0; j < space.height; j++) {
            for (int i = 0; i < space.width; i++) {
                SpaceObject spaceObject = space.getObjectAt(i, j);
                if (spaceObject != null) {
                    displayables[i][j] = displayableOf(spaceObject, gameState, listener);
                }
            }
        }
        displayEngine.apply( displayables );
    }

    public void apply( Object object , GameState gameState , GameActionListener listener ) {
        String title = new String();
        List<Map.Entry<String, Integer>> attributeContent = new ArrayList<>();
        ArrayList<String> titleList = new ArrayList<>();
        List<Map.Entry<String, List<Integer>>> collectionContent = new ArrayList<>();
        List<Map.Entry<String, Runnable >> actionContent = new ArrayList<>();
        if (object instanceof Planet) {
            Planet p = (Planet) object;
            title = p.getName();
            attributeContent.add(new AbstractMap.SimpleEntry<>("energy", p.getEnergy()           ) );
            attributeContent.add(new AbstractMap.SimpleEntry<>("material", p.getMaterial()       ) );
            attributeContent.add(new AbstractMap.SimpleEntry<>("temperature", p.getTemperature() ) );
            attributeContent.add(new AbstractMap.SimpleEntry<>("space capacity", p.getMaxSize()  ) );
            attributeContent.add(new AbstractMap.SimpleEntry<>("used capacity", p.getSize()      ) );
            titleList.add("Building type");
            titleList.add("Level");
            titleList.add("Size");
            Map<Class<? extends Building>, Building> buildingList = p.getBuildingMap();
            for (Map.Entry<Class<? extends Building>, Building> set : buildingList.entrySet()) {
                List<Integer> listElementAttributes = new ArrayList<Integer>();
                listElementAttributes.add(set.getValue().getLevel());
                listElementAttributes.add(set.getValue().getSize());
                collectionContent.add(
                        new AbstractMap.SimpleEntry<>( set.getKey().getSimpleName(), listElementAttributes )
                );
            }
            actionContent.add(new AbstractMap.SimpleEntry("Build Building", (Runnable) () -> listener.actionPerformed(SpecialAction.BUILD_BUILDING,gameState)));
            actionContent.add(new AbstractMap.SimpleEntry("Build Ship", (Runnable) () -> listener.actionPerformed(SpecialAction.BUILD_SHIP,gameState)));
            actionContent.add(new AbstractMap.SimpleEntry("Transfer Resources", (Runnable) () -> listener.actionPerformed(SpecialAction.TRANSFER,gameState)));
        }
        else if (object instanceof Fleet)
        {
            title = "Fleet";
        }
        else if (object instanceof Asteroid)
        {
            title = "An asteroid";
        }
        else if (object instanceof BlackHole)
        {
            title = "A black hole";
        }
        displayEngine.apply( title , attributeContent );
        displayEngine.apply( titleList , collectionContent );
        displayEngine.apply( actionContent  );
    }


    private Displayable displayableOf(SpaceObject spaceObject, GameState gameState, GameActionListener listener) {
        String imageName = spaceObject.getClass().getSimpleName().toLowerCase();
        Runnable action = () -> listener.actionPerformed(spaceObject, gameState);
        return new BoardItem(imageName, action);
    }

    private void handleShuffleAction(GameState gameState, GameActionListener listener) {
        System.out.println("shuffleAction!");
        listener.actionPerformed(SpecialAction.SHUFFLE, gameState);
    }

}
