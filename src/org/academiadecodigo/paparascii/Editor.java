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


    }


}
