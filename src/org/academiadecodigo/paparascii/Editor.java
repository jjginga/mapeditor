package org.academiadecodigo.paparascii;

import org.academiadecodigo.paparascii.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.paparascii.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.paparascii.simplegraphics.keyboard.KeyboardEventType;


public class Editor {

    private VisualGrid visualGrid;
    private Cursor cursor;

    public Editor(){
        visualGrid = new VisualGrid(700, 400);
        cursor = new Cursor(visualGrid.numberOfColumns()/2, visualGrid.numberOfRows()/2, visualGrid);

    }

    public void start() {

        visualGrid.init();
        keyboardControls();


    }

    private void keyboardControls(){

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


        KeyboardEvent eventSave = new KeyboardEvent();
        eventSave.setKey(KeyboardEvent.KEY_S);
        eventSave.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        k.addEventListener(eventSave);

        KeyboardEvent eventLoad = new KeyboardEvent();
        eventLoad.setKey(KeyboardEvent.KEY_L);
        eventLoad.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        k.addEventListener(eventLoad);
    }

}
