package org.academiadecodigo.paparascii;


import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import java.io.*;

import static java.lang.Enum.valueOf;
import static org.academiadecodigo.paparascii.Cursor.Direction.STILL;


public class  Cursor implements KeyboardHandler {

    //PROPERTIES
    private Position pos;
    private Rectangle square;
    private VisualGrid visualGrid;
    private Drawings drawings;
    private Factory factory;
    private String file_path="resources/memory1024";
    private MyKeyboard myKeyboard;
    private MyColors color = MyColors.MYBLACK;


    //CONSTRUCTOR
    public Cursor(int column, int row, VisualGrid visualGrid){



        this.visualGrid = visualGrid;
        drawings = new Drawings();
        factory = new Factory(visualGrid);

        pos = factory.getPosition(column,row);
        square= factory.getSquare(pos);
        square.setColor(color.getColor());
        square.fill();

        myKeyboard = new MyKeyboard(this);

    }



    //METHODS TO MAKE THE CURSOR MOVE
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

    //METHODS FOR PICTURE EDITING
    private void paint(){

        if(drawings.getSquare(pos)!=null){
            if (drawings.hasKey(pos)) {
                return;
            }
        }

        Position key = factory.getPosition(pos);
        drawings.add(key, factory.getSquare(pos));
        drawings.getSquare(key).setColor(color.getColor());
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
                return;
            }
        }
    }

    private void changeColor(){

        if(color.ordinal()==MyColors.values().length-1){
            color=MyColors.values()[0];
            square.setColor(color.getColor());
            return;
        }

        color=MyColors.values()[color.ordinal()+1];
        square.setColor(color.getColor());
    }

    //SAVING AND LOADING
    private void save(){
        try {


            BufferedWriter bWriter = new BufferedWriter(new FileWriter(file_path, false));

            for (Position key : drawings) {
                bWriter.write(key.toString()+" "+color.getValue(drawings.getSquare(key).getColor())+"\n");
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
            Position posLoad;
            Rectangle square;

            while((line = bReader.readLine()) != null) {
                values=line.split(" ");
                posLoad=factory.getPosition(Integer.valueOf(values[0]),Integer.valueOf(values[1]));
                square = factory.getSquare(posLoad);
                square.setColor(MyColors.values()[Integer.valueOf(values[2])].getColor());
                square.fill();
                drawings.add(posLoad,square);
            }

            bReader.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
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
            case KeyboardEvent.KEY_C:
                changeColor();
                break;
        }

    }

    @Override
    public void keyReleased(KeyboardEvent e) {

    }


    //Enum to update the position
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

    //Keyboard controls
    public class MyKeyboard{


        public MyKeyboard(KeyboardHandler handler){
        Keyboard k = new Keyboard(handler);

        //MOVEMENT
        KeyboardEvent eventRight = new KeyboardEvent();
        eventRight.setKey(KeyboardEvent.KEY_RIGHT);
        eventRight.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        k.addEventListener(eventRight);

        KeyboardEvent eventLeft = new KeyboardEvent();
        eventLeft.setKey(KeyboardEvent.KEY_LEFT);
        eventLeft.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        k.addEventListener(eventLeft);

        KeyboardEvent eventUp = new KeyboardEvent();
        eventUp.setKey(KeyboardEvent.KEY_UP);
        eventUp.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        k.addEventListener(eventUp);

        KeyboardEvent eventDown = new KeyboardEvent();
        eventDown.setKey(KeyboardEvent.KEY_DOWN);
        eventDown.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        k.addEventListener(eventDown);

        //CONTROLS
        KeyboardEvent eventFill = new KeyboardEvent();
        eventFill.setKey(KeyboardEvent.KEY_SPACE);
        eventFill.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        k.addEventListener(eventFill);

        KeyboardEvent eventDelete = new KeyboardEvent();
        eventDelete.setKey(KeyboardEvent.KEY_D);
        eventDelete.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        k.addEventListener(eventDelete);

        KeyboardEvent eventWipe = new KeyboardEvent();
        eventWipe.setKey(KeyboardEvent.KEY_W);
        eventWipe.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        k.addEventListener(eventWipe);


        KeyboardEvent eventSave = new KeyboardEvent();
        eventSave.setKey(KeyboardEvent.KEY_S);
        eventSave.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        k.addEventListener(eventSave);

        KeyboardEvent eventLoad = new KeyboardEvent();
        eventLoad.setKey(KeyboardEvent.KEY_L);
        eventLoad.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        k.addEventListener(eventLoad);

        KeyboardEvent eventChangeColor = new KeyboardEvent();
        eventChangeColor.setKey(KeyboardEvent.KEY_C);
        eventChangeColor.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        k.addEventListener(eventChangeColor);
        }
    }


    //Colors
    public enum MyColors {

        MYBLACK(Color.BLACK),
        MYRED(Color.RED),
        MYGREEN(Color.GREEN),
        MYBLUE(Color.BLUE),
        MYYELLOW(Color.YELLOW);

        private Color color;

        MyColors(Color color){
            this.color=color;
        }

        public int getValue(Color color){
            for (MyColors myColors : MyColors.values()) {
                if(myColors.getColor()==color){
                    return myColors.ordinal();
                }
            }

            return -1;
        }

        public Color getColor() {
            return color;
        }

    }
}
