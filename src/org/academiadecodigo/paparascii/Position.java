package org.academiadecodigo.paparascii;

public class Position{

    private int column;
    private int row;

    public Position(int column, int row){
        this.column=column;
        this.row=row;
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
}
