package hu.elte.inf.szofttech2023.team3.spacewar.gameplay;

import java.awt.Color;
import java.util.List;
import java.util.Map.Entry;

import hu.elte.inf.szofttech2023.team3.spacewar.display.BoardDisplay;
import hu.elte.inf.szofttech2023.team3.spacewar.display.DisplayEngine;
import hu.elte.inf.szofttech2023.team3.spacewar.display.Displayable;

public class MockDisplayEngine implements DisplayEngine {
    
    private final BoardDisplay boardDisplay;
    
    public MockDisplayEngine(int rowCount, int columnCount) {
        this.boardDisplay = new MockBoardDisplay(rowCount, columnCount);
    }

    @Override
    public void applyWinner(String name, Color color) {}

    @Override
    public void applyBoard(Displayable[][] boardContent) {}

    @Override
    public void applyObjectInfo(String title, List<Entry<String, Integer>> content) {}

    @Override
    public void applyObjectItemsInfo(
            boolean erase, String title, List<String> header, List<Entry<String, List<Integer>>> content) {}

    @Override
    public void applyItemSelector(boolean erase, String title, Object[] header, Object[][] content) {}

    @Override
    public void applyObjectActionPalette(String title, List<Entry<String, Runnable>> content) {}

    @Override
    public BoardDisplay getBoardDisplay() {
        return boardDisplay;
    }

    @Override
    public void setInfoLabel(String info) {}

    @Override
    public void setTurnLabel(int turnNumber, String player, double actionPoint) {}

    @Override
    public int getSelectedRow() {
        return 0;
    }

}
