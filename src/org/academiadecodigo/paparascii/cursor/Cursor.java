package org.academiadecodigo.paparascii.cursor;

import org.academiadecodigo.paparascii.grid.Position;
import org.academiadecodigo.paparascii.graphics.Color;
import org.academiadecodigo.paparascii.graphics.Rectangle;
import org.academiadecodigo.paparascii.grid.VisualGrid;
import org.academiadecodigo.paparascii.keyboard.KeyboardEvent;
import org.academiadecodigo.paparascii.keyboard.KeyboardHandler;

import static org.academiadecodigo.paparascii.cursor.Cursor.Direction.STILL;


public class  Cursor implements KeyboardHandler {

    private Position pos;
    private Rectangle square;
    private VisualGrid visualGrid;
    private boolean isPainting;
    private boolean isWiping;
    private boolean isDeleting;
    private boolean isSaving;
    private boolean isLoading;

    public Cursor(int column, int row, VisualGrid visualGrid){

        //get position from factory

        pos = new Position(column,row);
        this.visualGrid = visualGrid;

        //get square from factory
        square= new Rectangle(visualGrid.columnToX(visualGrid.numberOfColumns()/2), visualGrid.rowToY(visualGrid.numberOfRows()/2), visualGrid.getCellSize(), visualGrid.getCellSize());
        square.setColor(Color.GREEN);
        square.fill();

    }

    private void draw(int dColumn, int dRow){
        square.translate(visualGrid.getCellSize()*dColumn, visualGrid.getCellSize()*dRow);
        square.fill();
    }

    private void hide(){
        square.delete();
    }

    private void move(Direction direction){
       hide();

       direction=checkForEdges(direction);
       pos.moveColumn(direction.dColumn);
       pos.moveRow(direction.dRow);

       draw(direction.dColumn, direction.dRow);
    }

    private Direction checkForEdges(Direction direction){
        switch (direction) {
            case RIGHT:
                if (pos.getColumn() == visualGrid.numberOfColumns()-1) {
                    direction = STILL;
                }
                break;
            case LEFT:
                if (pos.getColumn() == 0) {
                    direction = STILL;
                }
                break;
            case DOWN:
                if (pos.getRow() == visualGrid.numberOfRows()-1) {
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

    /**Change the value of the respective boolean on Keyboard press**/
    private void paint(){
        isPainting=true;
    }

    private void clear(){
        isWiping=true;
    }

    private void delete(){
        isDeleting=true;
    }

    private void save(){
        isSaving=true;
    }

    private void load(){
        isLoading=true;
    }


    /**Getters for the boolean**/
    public boolean isPainting() {
        return isPainting;

    }

    public boolean isWiping() {
        return isWiping;
    }

    public boolean isDeleting() {
        return isDeleting;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean isSaving() {
        return isSaving;
    }


    /**Getter for the position**/
    public Position getPos() {
        return pos;
    }


    /**Reset the value of the boolean after the action is done**/
    public void resetPainting() {
            isPainting=false;
    }

    public void resetWiping() {
            isWiping=false;
    }

    public void resetDeleting() {
            isDeleting=false;
    }

    public void resetSaving() {
            isSaving=false;
    }

    public void resetLoading() {
            isLoading=false;
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
            case KeyboardEvent.KEY_D:
                delete();
                break;
            case KeyboardEvent.KEY_S:
                save();
                break;
            case KeyboardEvent.KEY_L:
                load();
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

    }


}
