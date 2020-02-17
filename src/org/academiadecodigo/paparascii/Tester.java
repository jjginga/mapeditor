package org.academiadecodigo.paparascii;


public class Tester {

    public static void main(String[] args) {
        Editor editor = new Editor();
        try {
            editor.start();
        } catch (InterruptedException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }


    }
}
