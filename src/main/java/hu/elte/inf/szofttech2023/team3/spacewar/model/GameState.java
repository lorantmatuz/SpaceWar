package hu.elte.inf.szofttech2023.team3.spacewar.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameState {

    private final Map<FieldPosition, Spacecraft> spacecrafts = new HashMap<>();
    
    public Map<FieldPosition, Spacecraft> getSpacecrafts() {
        return Collections.unmodifiableMap(spacecrafts);
    }
    
    public void setSpacecrafts(Map<FieldPosition, Spacecraft> spacecrafts) {
        this.spacecrafts.clear();
        this.spacecrafts.putAll(new HashMap<>(spacecrafts));
    }
    
}
