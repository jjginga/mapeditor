package org.academiadecodigo.paparascii;

import org.academiadecodigo.paparascii.cursor.Cursor;
import org.academiadecodigo.paparascii.grid.Grid;
import org.academiadecodigo.paparascii.keyboard.Keyboard;
import org.academiadecodigo.paparascii.keyboard.KeyboardEvent;
import org.academiadecodigo.paparascii.keyboard.KeyboardEventType;

public class Editor {

    private Grid grid;
    private Cursor cursor;
    private Drawings drawings;
    private SquareFactory squareFactory;
    private int delay = 200;

    public Editor(){
        grid = new Grid(400, 400);
        cursor = new Cursor(grid.numberOfColumns()/2,grid.numberOfRows()/2,grid);
        drawings = new Drawings();
        squareFactory = new SquareFactory(grid);
    }


    public void start() throws InterruptedException {
        grid.init();
        keyboardControls();
        while(true) {
            Thread.sleep(delay);
            if (cursor.isPainting()) {
                paint();
            }
            if (cursor.isClearing()) {
                clear();
            }
        }

    }



    public void keyboardControls(){

        Keyboard k = new Keyboard(cursor);

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
    }

    public void paint(){


        drawings.add(cursor.getPos(), squareFactory.getSquare(cursor.getPos()));
        System.out.println("Position "+cursor.getPos().getRow()+" "+cursor.getPos().getColumn());
        cursor.setPainting();
        drawings.getSquare(cursor.getPos()).fill();


    }

    public void clear(){

        System.out.println(drawings.size());
        for (Position pos : drawings) {
            System.out.println(pos.getColumn()+" "+pos.getRow());
            drawings.getSquare(pos).delete();
        }
        drawings.wipe();
        cursor.setClear();


    }



    public Grid getGrid() {
        return grid;
    }


}
