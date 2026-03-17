package io.github.nataliabpw.maze_solver.model;

import java.util.ArrayList;
import java.util.HashMap;

public class AdjacencyMatrix {
    private final int BOTTOM_PATH = 0;
    private final int TOP_PATH = 2;
    private final int LEFT_PATH = 1;
    private final int RIGHT_PATH = 3;
    
    
    private final HashMap<Integer, boolean[]> matrix = new HashMap<>();
    
    public void addPath(int from, Passage path){
        int indexToChange;
        switch(path){
            case Passage.BOTTOM -> {
                 indexToChange= BOTTOM_PATH;
            }
            case Passage.LEFT -> {
                indexToChange= LEFT_PATH;
            }
            case Passage.TOP -> {
                indexToChange= TOP_PATH;
            }
            case Passage.RIGHT -> {
                indexToChange= RIGHT_PATH;
            }
            default-> {
                indexToChange = 100000;
            }
        }
        changePathToTrue(indexToChange, from);
    }
    
    private void changePathToTrue(int indexToChange, int from){
        final boolean defaultPaths[] = {false, false, false, false}; 
        boolean newPaths[] = matrix.getOrDefault(from, defaultPaths);
        newPaths[indexToChange] = true;
        matrix.remove(from);
        matrix.put(from, newPaths);
    }
   
    public int[] getNodesConnectedWitchCurrent(int current, int columns){
        final boolean defaultPaths[] = {false, false, false, false}; 
        boolean[] actualPaths =matrix.getOrDefault(current, defaultPaths);
        ArrayList<Integer> nodes = new ArrayList<>();
        if(actualPaths[BOTTOM_PATH]){
            nodes.add(current + columns);
        }
        if(actualPaths[LEFT_PATH]){
            if(current != 0)
                nodes.add(current - 1);
        } 
        if(actualPaths[TOP_PATH]){
            nodes.add(current - columns);
        } 
        if(actualPaths[RIGHT_PATH]){
            nodes.add(current + 1);
        }
        if(!nodes.isEmpty())
            return nodes.stream().mapToInt(i -> i).toArray();
        else
        {
            int[] emptyArray = {};
            return emptyArray;
        }
            
    }
}
