package org.academiadecodigo.paparascii;

import org.academiadecodigo.paparascii.simplegraphics.graphics.Rectangle;

import java.util.*;

public class Drawings implements Iterable<Position>{

        private Map<Position, Rectangle> positions;

        public Drawings(){
            positions = new HashMap<>();
        }

        public boolean hasKey(Position pos) {
            return positions.containsKey(pos);
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

        public Iterator<Position> iterator(){
            return positions.keySet().iterator();
        }

        public Rectangle getSquare(Position position){
            return positions.get(position);
        }

}

