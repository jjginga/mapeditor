package org.academiadecodigo.paparascii;


import org.academiadecodigo.simplegraphics.graphics.Line;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

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

    //Calculates number of Columns
    public int numberOfColumns(){
        return (width/cellSize);
    }


    //Calculates number of Rows
    public int numberOfRows(){
        return (height/cellSize);
    }

    //Creates vertical lines
    private void verticalLines(){
        int x = PADDING;
        verticalLines = new Line[numberOfColumns()-1];

        for (int i = 0; i<numberOfColumns()-1;i++){
            verticalLines[i] = new Line(x+=cellSize , PADDING, x ,height+PADDING);
        }
    }

    //Creates horizontal lines
    private void horizontalLines(){
        int y = PADDING;
        horizontalLines = new Line[numberOfRows()-1];

        for (int i = 0; i<numberOfRows()-1;i++){
            horizontalLines[i] = new Line(PADDING,y+=cellSize ,width+PADDING, y);
        }
    }

    //Draws lines
    private void drawLines(){
        for (Line horizontalLine : horizontalLines) {
            horizontalLine.draw();
        }

        for (Line verticalLine : verticalLines) {
            verticalLine.draw();
        }
    }

    //For auxiliary calculations
    public int columnToX(int column){
        return (PADDING+column*cellSize);
    }

    public int rowToY(int row){
        return (PADDING+row*cellSize);
    }
}

