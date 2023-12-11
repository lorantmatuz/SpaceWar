package hu.elte.inf.szofttech2023.team3.spacewar.display;

import java.awt.Color;
import java.util.*;

public interface DisplayEngine {
    public void applyWinner(String name, Color color);
    public void applyBoard(Displayable[][] boardContent);
    public void applyObjectInfo(String title , List<Map.Entry<String,Integer>> content );
    public void applyObjectItemsInfo(boolean erase, String title, List<String> header, List< Map.Entry<String, List<Integer> > > content );
    public void applyItemSelector(boolean erase, String title, Object[] header, Object[][] content);
    public void applyObjectActionPalette(String title, List<Map.Entry<String, Runnable >> content );
    public BoardDisplay getBoardDisplay();
    public void setInfoLabel(String info);
    public void setTurnLabel(int turnNumber , String player, double actionPoint);
    public int getSelectedRow();
}
