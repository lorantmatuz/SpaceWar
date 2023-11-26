package hu.elte.inf.szofttech2023.team3.spacewar.display;

import java.awt.event.MouseEvent;

public interface BoardDisplay {

    public int getRowCount();
    public int getColumnCount();
    public void handleBoardClick(MouseEvent e);
}
