package org.academiadecodigo.paparascii;

import org.academiadecodigo.paparascii.graphics.Rectangle;

import java.io.*;
import java.util.*;

public class Drawings implements Iterable<Position>{

        private Map<Position, Rectangle> positions;

        public Drawings(){
            positions = new HashMap<>();
        }

        public void add(Position position, Rectangle square){
            positions.put(position, square);
        }

        public void delete(Position position){
            if(positions.containsKey(position)){
                positions.remove(position);
            }
        }

        public void wipe(){
            positions.clear();
        }

        public Set<Position> getSquares(){
            return positions.keySet();
        }

        public Iterator<Position> iterator(){
            return positions.keySet().iterator();
        }

        public int size(){
            return positions.size();
        }



        public Rectangle getSquare(Position position){
            return positions.get(position);
        }




}

