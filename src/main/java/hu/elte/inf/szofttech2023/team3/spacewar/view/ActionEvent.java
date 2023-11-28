package hu.elte.inf.szofttech2023.team3.spacewar.view;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ActionEvent {

    private final Object type;
    
    private final Map<String, Object> data;

    public ActionEvent(Object type) {
        this(type, Collections.emptyMap());
    }

    public ActionEvent(Object type, Map<String, Object> data) {
        this.type = type;
        this.data = new HashMap<>(data);
    }

    public Object getType() {
        return type;
    }

    public Map<String, Object> getData() {
        return Collections.unmodifiableMap(data);
    }

}
