package hu.elte.inf.szofttech2023.team3.spacewar.display;

import hu.elte.inf.szofttech2023.team3.spacewar.view.GameActionListener;

import java.util.*;
import hu.elte.inf.szofttech2023.team3.spacewar.model.GameState;

public interface Applicable {
    public void apply(Displayable[][] boardContent);
    public void apply(String title , List<Map.Entry<String,Integer>> content );
    public void apply(Boolean erase, String title, List<String> header, List< Map.Entry<String, List<Integer> > > content );
    public void apply(List<Map.Entry<String, Runnable >> content );
}
