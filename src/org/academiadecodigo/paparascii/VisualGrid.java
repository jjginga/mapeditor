package org.academiadecodigo.paparascii;

import org.academiadecodigo.paparascii.simplegraphics.graphics.Line;
import org.academiadecodigo.paparascii.simplegraphics.graphics.Rectangle;

public class VisualGrid {

    public static final int PADDING = 10;
    private int cellSize=10;
    private int width;
    private int height;
    private Rectangle canvas;
    private Line[] verticalLines;
    private Line[] horizontalLines;

    public VisualGrid(int width, int height){
        this.width=width;
        this.height=height;
        canvas = new Rectangle(PADDING, PADDING, width, height);

    }

    public void init() {
        canvas.draw();
        verticalLines();
        horizontalLines();
        drawLines();
    }

    public int getCellSize() {
        return cellSize;
    }

    public int numberOfColumns(){
        return (getWidth()/cellSize);
    }

    public int numberOfRows(){
        return (getHeight()/cellSize);
    }

    private void verticalLines(){
        int x = PADDING;
        verticalLines = new Line[numberOfColumns()-1];

        for (int i = 0; i<numberOfColumns()-1;i++){
            verticalLines[i] = new Line(x+=cellSize , PADDING, x ,getHeight());
        }
    }

    private void horizontalLines(){
        int y = PADDING;
        horizontalLines = new Line[numberOfRows()-1];

        for (int i = 0; i<numberOfRows()-1;i++){
            horizontalLines[i] = new Line(PADDING,y+=cellSize ,getWidth(), y);
        }
    }

    private void drawLines(){
        for (Line horizontalLine : horizontalLines) {
            horizontalLine.draw();
        }

        for (Line verticalLine : verticalLines) {
            verticalLine.draw();
        }
    }


    public int getHeight() {
        return height+PADDING;
    }

    public int getWidth() {
        return width+PADDING;
    }

    public int columnToX(int column){
        return (PADDING+column*cellSize);
    }

    public int rowToY(int row){
        return (PADDING+row*cellSize);
    }
}

