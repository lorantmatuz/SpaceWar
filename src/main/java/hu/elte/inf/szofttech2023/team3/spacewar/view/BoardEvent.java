package hu.elte.inf.szofttech2023.team3.spacewar.view;

public class BoardEvent {
    
    private final BoardEventType type;
    
    private final FieldPosition fieldPosition;

    public BoardEvent(BoardEventType type, FieldPosition fieldPosition) {
        this.type = type;
        this.fieldPosition = fieldPosition;
    }

    public BoardEventType getType() {
        return type;
    }

    public FieldPosition getFieldPosition() {
        return fieldPosition;
    }

}
