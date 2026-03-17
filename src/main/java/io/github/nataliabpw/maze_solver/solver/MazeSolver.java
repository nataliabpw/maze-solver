package io.github.nataliabpw.maze_solver.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.nataliabpw.maze_solver.model.MazeData;

public class MazeSolver {
    private final int [] predecessors;
    private final MazeData mazeData;
    private final int start;
    private final int end;
    
    public MazeSolver(MazeData mazeData){
        this.mazeData = mazeData;
        int numberOfNodes = mazeData.getNumberOfNodes();
        predecessors = new int[numberOfNodes];
        Arrays.fill(predecessors, -1);
        start = mazeData.getStart();
        end = mazeData.getEnd();
    }
    
    public List<Integer> generatePath(){
        BFS();
        List<Integer> path = new ArrayList<>();
        int curr = start;
        path.add(curr);
        int next;
        while (curr != end){
            next = predecessors[curr];
            path.add(next);
            curr = next;
        }
        return path;
    }
    
    private void BFS(){
        List<Integer> queue = new ArrayList<>();
        int curr = end;
        queue.add(curr);
        outer: while (!queue.isEmpty()){
            curr = queue.get(0);
            for (int n : neighbours(curr)){
                if (predecessors[n] == -1){
                    predecessors[n] = curr;
                    queue.add(n);
                }
                if (n==start){
                        break outer;
                }
            }
            queue.remove(0);
        }
    }
    
    private int[] neighbours(int current){
        return mazeData.getNeighbours(current);
    }
}
