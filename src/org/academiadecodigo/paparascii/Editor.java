package org.academiadecodigo.paparascii;

import org.academiadecodigo.paparascii.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.paparascii.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.paparascii.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.paparascii.simplegraphics.keyboard.KeyboardEventType;

import java.io.*;

public class Editor {

    private VisualGrid visualGrid;
    private Cursor cursor;
    private Drawings drawings;
    private Factory factory;
    private int delay = 200;
    private String file_path="resources/memory1024";

    public Editor(){
        visualGrid = new VisualGrid(400, 400);
        cursor = new Cursor(visualGrid.numberOfColumns()/2, visualGrid.numberOfRows()/2, visualGrid);
        drawings = new Drawings();
        factory = new Factory(visualGrid);
    }

    public void start() throws InterruptedException {
        visualGrid.init();
        keyboardControls();

        while(true) {

            Thread.sleep(delay);

                paint();

                delete();

                wipe();

                save();

                load();
        }

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

    private void paint(){

        if (!cursor.isPainting()){
            return;
        }

        Position pos = factory.getPosition(cursor.getPos());
        drawings.add(pos, factory.getSquare(pos));
        drawings.getSquare(pos).fill();

        cursor.resetPainting();
        System.out.println(pos);

    }

    private void wipe(){

        if(!cursor.isWiping()){
            return;
        }

        for (Position pos : drawings) {
            drawings.getSquare(pos).delete();
        }
        drawings.wipe();
        cursor.resetWiping();


    }

    private void delete(){

        if (!cursor.isDeleting()){
            return;
        }

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

    private void save() {

        if(!cursor.isSaving()){
            return;
        }

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

    private void load() {

        if(!cursor.isLoading()){
            return;
        }

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
        cursor.resetLoading();

        for (Position key : drawings) {
            drawings.getSquare(key).fill();
        }



        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}
