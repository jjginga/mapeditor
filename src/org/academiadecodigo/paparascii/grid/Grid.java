package org.academiadecodigo.paparascii.grid;

import org.academiadecodigo.paparascii.graphics.Line;

public class Grid extends Field {

    private int cellSize=10;
    private Line[] verticalLines;
    private Line[] horizontalLines;

    public Grid(int width, int height){
        super(width, height);
        verticalLines();
        horizontalLines();
        drawLines();
    }

    public int getCellSize() {
        return cellSize;
    }

    public int numberOfColumns(){
        return (this.getWidth()/cellSize);
    }

    public int numberOfRows(){
        return (this.getHeight()/cellSize);
    }

    public void verticalLines(){
        int x = PADDING;
        verticalLines = new Line[numberOfColumns()-1];

        for (int i = 0; i<numberOfColumns()-1;i++){
            verticalLines[i] = new Line(x+=cellSize , PADDING, x ,getHeight());
        }
    }

    public void horizontalLines(){
        int y = PADDING;
        horizontalLines = new Line[numberOfRows()-1];

        for (int i = 0; i<numberOfRows()-1;i++){
            horizontalLines[i] = new Line(PADDING,y+=cellSize ,getWidth(), y);
        }
    }

    public void drawLines(){
        for (Line horizontalLine : horizontalLines) {
            horizontalLine.draw();
        }

        for (Line verticalLine : verticalLines) {
            verticalLine.draw();
        }
    }


    public int columnToX(int column){
        return (PADDING+column*cellSize);
    }

    public int rowToY(int row){
        return (PADDING+row*cellSize);
    }
}

