package hu.elte.inf.szofttech2023.team3.spacewar.display;

import java.awt.Color;
import java.util.function.BiConsumer;

import hu.elte.inf.szofttech2023.team3.spacewar.view.BoardEventType;
import hu.elte.inf.szofttech2023.team3.spacewar.view.FieldPosition;

public interface BoardDisplay {
    public int getRowCount();
    public int getColumnCount();
    public void setWinner(String name, Color color);
    public void setBoardListener(BiConsumer<BoardEventType, FieldPosition> boardListener);
}
