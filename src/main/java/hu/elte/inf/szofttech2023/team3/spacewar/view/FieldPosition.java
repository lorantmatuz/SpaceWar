package hu.elte.inf.szofttech2023.team3.spacewar.view;

import java.util.Objects;

public class FieldPosition {

    private final int row;
    
    private final int column;

    private FieldPosition(int row, int column) {
        if (row < 0) {
            throw new IllegalArgumentException("row is negative");
        }
        if (column < 0) {
            throw new IllegalArgumentException("column is negative");
        }
        
        this.row = row;
        this.column = column;
    }
    
    public static FieldPosition of(int row, int column) {
        return new FieldPosition(row, column);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof FieldPosition)) {
            return false;
        }
        
        FieldPosition otherFieldPosition = (FieldPosition) other;
        return row == otherFieldPosition.row && column == otherFieldPosition.column;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

}
