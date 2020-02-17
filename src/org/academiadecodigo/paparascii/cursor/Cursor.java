package org.academiadecodigo.paparascii.cursor;

import org.academiadecodigo.paparascii.Position;
import org.academiadecodigo.paparascii.graphics.Color;
import org.academiadecodigo.paparascii.graphics.Rectangle;
import org.academiadecodigo.paparascii.grid.Grid;
import org.academiadecodigo.paparascii.keyboard.KeyboardEvent;
import org.academiadecodigo.paparascii.keyboard.KeyboardHandler;

import static org.academiadecodigo.paparascii.cursor.Cursor.Direction.STILL;


public class  Cursor implements KeyboardHandler {

    private Position pos;
    private Rectangle square;
    private Grid grid;
    private boolean isPainting;
    private boolean clear;

    public Cursor(int column, int row, Grid grid){
        pos = new Position(column,row);
        this.grid=grid;
        square= new Rectangle(grid.columnToX(grid.numberOfColumns()/2), grid.rowToY(grid.numberOfRows()/2), grid.getCellSize(), grid.getCellSize());
        square.setColor(Color.GREEN);
        square.fill();

    }

    public void draw(int dColumn, int dRow){
        square.translate(grid.getCellSize()*dColumn, grid.getCellSize()*dRow);
        square.fill();
    }

    public void hide(){
        square.delete();
    }

    public void move(Direction direction){
       hide();

       direction=checkForEdges(direction);
       pos.moveColumn(direction.dColumn);
       pos.moveRow(direction.dRow);

       draw(direction.dColumn, direction.dRow);
    }

    public Direction checkForEdges(Direction direction){
        switch (direction) {
            case RIGHT:
                if (pos.getColumn() == grid.numberOfColumns()-1) {
                    direction = STILL;
                }
                break;
            case LEFT:
                if (pos.getColumn() == 0) {
                    direction = STILL;
                }
                break;
            case DOWN:
                if (pos.getRow() == grid.numberOfRows()-1) {
                    direction = STILL;
                }
                break;
            case UP:
                if (pos.getRow() == 0) {
                    direction = STILL;
                }
                break;
        }

        return direction;

        }

    public void paint(){
        isPainting=true;
    }

    public void clear(){
        clear=true;
        System.out.println(clear);
    }

    public boolean isPainting() {
        return isPainting;

    }

    public boolean isClearing() {
        return clear;
    }

    public Position getPos() {
        return pos;
    }

    public void setPainting() {
        if (isPainting){
            isPainting=false;
        }
    }

    public void setClear() {
        if(clear){
            clear=false;
        }
    }

    @Override
    public void keyPressed(KeyboardEvent e) {

        switch (e.getKey()) {
            case KeyboardEvent.KEY_RIGHT:
                move(Direction.RIGHT);
                break;
            case KeyboardEvent.KEY_LEFT:
                move(Direction.LEFT);
                break;
            case KeyboardEvent.KEY_UP:
                move(Direction.UP);
                break;
            case KeyboardEvent.KEY_DOWN:
                move(Direction.DOWN);
                break;
            case KeyboardEvent.KEY_SPACE:
                paint();
                break;
            case KeyboardEvent.KEY_W:
                clear();
                break;

        }

    }

    @Override
    public void keyReleased(KeyboardEvent e) {

    }

    public enum Direction{
        UP(0,-1),
        DOWN(0,1),
        LEFT(-1,0),
        RIGHT(1,0),
        STILL(0,0);

        private int dColumn;
        private int dRow;

        Direction(int dColumn, int dRow){
            this.dColumn = dColumn;
            this.dRow = dRow;
        }

        public int getdColumn() {
            return dColumn;
        }

        public int getdRow() {
            return dRow;
        }
    }


}
