package org.academiadecodigo.paparascii;

import java.util.Objects;

public class Position{

    private int column;
    private int row;

    public Position(int column, int row){
        this.column=column;
        this.row=row;
    }

    public Position(Position pos){
        this.column=pos.getColumn();
        this.row=pos.getRow();
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void moveColumn(int dColumn) {
        column += dColumn;
    }

    public void moveRow(int dRow) {
        row += dRow;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return getColumn() == position.getColumn() &&
                getRow() == position.getRow();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getColumn(), getRow());
    }

    @Override
    public String toString() {
        return column+" "+row;
    }
}
