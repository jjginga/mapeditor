package org.academiadecodigo.paparascii.grid;

import org.academiadecodigo.paparascii.graphics.Rectangle;
import org.academiadecodigo.paparascii.grid.Grid;
import org.academiadecodigo.paparascii.grid.Position;

public class Factory {

    private Grid grid;

    public Factory(Grid grid){
        this.grid=grid;
    }

    public Rectangle getSquare(Position position){
        return new Rectangle(grid.columnToX(position.getColumn()), grid.rowToY(position.getRow()), grid.getCellSize(), grid.getCellSize());

    }

    public Position getPosition(Position pos) {
        return new Position(pos);
    }

}
