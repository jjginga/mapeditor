package org.academiadecodigo.paparascii.grid;

import org.academiadecodigo.paparascii.graphics.Rectangle;

public class Field {

    public static final int PADDING = 10;
    private int width;
    private int height;
    public Rectangle canvas;

    public Field(int width, int height){
        this.width=width;
        this.height=height;
        canvas = new Rectangle(PADDING, PADDING, width, height);
    }


    public void init(){
        canvas.draw();
    }



    public int getHeight() {
        return height+PADDING;
    }

    public int getWidth() {
        return width+PADDING;
    }


}
