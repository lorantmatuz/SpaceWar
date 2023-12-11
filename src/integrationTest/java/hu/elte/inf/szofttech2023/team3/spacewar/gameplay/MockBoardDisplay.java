package hu.elte.inf.szofttech2023.team3.spacewar.gameplay;

import java.awt.Color;
import java.util.function.BiConsumer;

import hu.elte.inf.szofttech2023.team3.spacewar.display.BoardDisplay;
import hu.elte.inf.szofttech2023.team3.spacewar.view.BoardEventType;
import hu.elte.inf.szofttech2023.team3.spacewar.view.FieldPosition;

public class MockBoardDisplay implements BoardDisplay {

    private final int rowCount;
    
    private final int columnCount;
    
    public MockBoardDisplay(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public void setWinner(String name, Color color) {}

    @Override
    public void setBoardListener(BiConsumer<BoardEventType, FieldPosition> boardListener) {}

}
