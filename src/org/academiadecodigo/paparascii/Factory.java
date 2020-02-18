package org.academiadecodigo.paparascii;


import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Factory {

    private VisualGrid visualGrid;

    public Factory(VisualGrid visualGrid){
        this.visualGrid = visualGrid;
    }

    public Rectangle getSquare(Position position){
        return new Rectangle(visualGrid.columnToX(position.getColumn()), visualGrid.rowToY(position.getRow()), visualGrid.getCellSize(), visualGrid.getCellSize());

    }

    public Position getPosition(Position pos) {
        return new Position(pos);
    }

    public Position getPosition(int column, int row) {
        return new Position(column, row);
    }



}
