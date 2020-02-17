package org.academiadecodigo.paparascii;

import org.academiadecodigo.paparascii.graphics.Rectangle;
import org.academiadecodigo.paparascii.grid.Grid;

public class SquareFactory {

    private Grid grid;

    public SquareFactory(Grid grid){
        this.grid=grid;
    }

    public Rectangle getSquare(Position position){
        return new Rectangle(grid.columnToX(position.getColumn()), grid.rowToY(position.getRow()), grid.getCellSize(), grid.getCellSize());

    }

}
