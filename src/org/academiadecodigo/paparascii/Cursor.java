package org.academiadecodigo.paparascii;

import org.academiadecodigo.paparascii.simplegraphics.graphics.Color;
import org.academiadecodigo.paparascii.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.paparascii.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.paparascii.simplegraphics.keyboard.KeyboardHandler;

import java.io.*;

import static org.academiadecodigo.paparascii.Cursor.Direction.STILL;


public class  Cursor implements KeyboardHandler {

    private Position pos;
    private Rectangle square;
    private VisualGrid visualGrid;
    private Drawings drawings;
    private Factory factory;
    private String file_path="resources/memory1024";

    public Cursor(int column, int row, VisualGrid visualGrid){


        this.visualGrid = visualGrid;
        drawings = new Drawings();
        factory = new Factory(visualGrid);

        pos = factory.getPosition(column,row);
        square= factory.getSquare(pos);
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

    private void paint(){

        Position key = factory.getPosition(pos);
        drawings.add(key, factory.getSquare(pos));
        drawings.getSquare(key).fill();
    }

    private void wipe(){

        for (Position key : drawings) {
            drawings.getSquare(key).delete();
        }
        drawings.wipe();
    }

    private void delete(){

        for (Position key : drawings) {
            if (key.equals(pos)){
                drawings.getSquare(key).delete();
                drawings.delete(key);
            }
        }
    }

    private void save(){
        try {

            BufferedWriter bWriter = new BufferedWriter(new FileWriter(file_path, false));
            for (Position position : drawings) {
                bWriter.write(position.toString()+"\n");
            }

            bWriter.flush();
            bWriter.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void load(){
        wipe();

        try{

            BufferedReader bReader = new BufferedReader(new FileReader(file_path));

            String line;
            String[] values;
            Position pos;
            Rectangle square;

            while((line = bReader.readLine()) != null) {
                values=line.split(" ");
                pos=new Position(Integer.parseInt(values[0]),Integer.parseInt(values[1]));
                square = factory.getSquare(pos);
                drawings.add(pos,square);
            }
            bReader.close();

            for (Position key : drawings) {
                drawings.getSquare(key).fill();
            }



        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }





    /**Getter for the position**/
    public Position getPos() {
        return pos;
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
                wipe();
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
