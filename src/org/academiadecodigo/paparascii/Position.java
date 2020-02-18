package org.academiadecodigo.paparascii;

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


    public boolean equals(Position position) {
        return (row==position.getRow() && column==position.getColumn());
    }

    @Override
    public String toString() {
        return column+" "+row;
    }
}
