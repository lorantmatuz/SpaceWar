package hu.elte.inf.szofttech2023.team3.spacewar.model.display;

import java.awt.Image;

public interface Displayable {

    public Image getImage(int width, int height);
    
    public Runnable getAction();
    
}
