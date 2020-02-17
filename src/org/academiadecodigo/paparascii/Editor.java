package org.academiadecodigo.paparascii;

import org.academiadecodigo.paparascii.cursor.Cursor;
import org.academiadecodigo.paparascii.graphics.Rectangle;
import org.academiadecodigo.paparascii.grid.Factory;
import org.academiadecodigo.paparascii.grid.Grid;
import org.academiadecodigo.paparascii.grid.Position;
import org.academiadecodigo.paparascii.keyboard.Keyboard;
import org.academiadecodigo.paparascii.keyboard.KeyboardEvent;
import org.academiadecodigo.paparascii.keyboard.KeyboardEventType;

import java.io.*;

public class Editor {

    private Grid grid;
    private Cursor cursor;
    private Drawings drawings;
    private Factory factory;
    private int delay = 200;
    private String file_path="resources/memory1024";

    public Editor(){
        grid = new Grid(400, 400);
        cursor = new Cursor(grid.numberOfColumns()/2,grid.numberOfRows()/2,grid);
        drawings = new Drawings();
        factory = new Factory(grid);
    }

    public void start() throws InterruptedException {
        grid.init();
        keyboardControls();

        while(true) {

            Thread.sleep(delay);

            if (cursor.isPainting()) {
                paint();
            }

            if (cursor.isDeleting()){
                delete();
            }

            if (cursor.isClearing()) {
                clear();
            }

            if (cursor.isSaving()) {
                save();
            }

            if (cursor.isLoading()){
                load();
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


        KeyboardEvent eventSave = new KeyboardEvent();
        eventSave.setKey(KeyboardEvent.KEY_S);
        eventSave.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        k.addEventListener(eventSave);

        KeyboardEvent eventLoad = new KeyboardEvent();
        eventLoad.setKey(KeyboardEvent.KEY_L);
        eventLoad.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        k.addEventListener(eventLoad);
    }

    public void paint(){

        Position pos = factory.getPosition(cursor.getPos());
        drawings.add(pos, factory.getSquare(pos));
        cursor.resetPainting();
        drawings.getSquare(pos).fill();
        System.out.println(pos);

    }

    public void clear(){

        for (Position pos : drawings) {
            drawings.getSquare(pos).delete();
        }
        drawings.wipe();
        cursor.resetClear();


    }

    public void delete(){
        for (Position key : drawings) {
            if (key.equals(cursor.getPos())){
                drawings.getSquare(key).delete();
                drawings.delete(key);
                cursor.resetDeleting();
                return;
            }
        }

        cursor.resetDeleting();
    }

    public void save() {
        try {

            BufferedWriter bWriter = new BufferedWriter(new FileWriter(file_path, false));
            for (Position position : drawings) {
                bWriter.write(position.toString()+"\n");
            }

            bWriter.flush();
            bWriter.close();
            cursor.resetSaving();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void load() {

        clear();

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
        cursor.resetLoading();

        for (Position key : drawings) {
            drawings.getSquare(key).fill();
        }



        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}
