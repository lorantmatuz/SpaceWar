package hu.elte.inf.szofttech2023.team3.spacewar.display;

public interface BoardDisplay {

    public int getRowCount();
    
    public int getColumnCount();
    
    public void apply(Displayable[][] content, Runnable shuffleAction);
    
}
