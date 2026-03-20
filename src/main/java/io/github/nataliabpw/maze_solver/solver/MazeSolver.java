package io.github.nataliabpw.maze_solver.solver;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import org.springframework.stereotype.Component;

import io.github.nataliabpw.maze_solver.exception.MazePathNotFoundException;
import io.github.nataliabpw.maze_solver.model.MazeData;

@Component
public class MazeSolver {
    
    public List<Integer> generatePath(MazeData mazeData){
        int numberOfNodes = mazeData.getNumberOfNodes();
        int [] predecessors = new int[numberOfNodes];
        Arrays.fill(predecessors, -1);

        int start = mazeData.getStart();
        int end = mazeData.getEnd();

        bfs(mazeData, predecessors, start, end);

        if (predecessors[start]==-1){
            throw new MazePathNotFoundException("No path found between given points");
        }

        List<Integer> path = buildPath(predecessors, start, end);

        return path;
    }
    
    private void bfs(MazeData mazeData, int[] predecessors, int start, int end){
        Queue<Integer> queue = new ArrayDeque<>();
        int curr = end;
        queue.add(curr);
        while (!queue.isEmpty()){
            curr = queue.poll();
            int[] neighbours = mazeData.getNeighbours(curr);
            for (int n : neighbours){
                if (predecessors[n] == -1){
                    predecessors[n] = curr;
                    queue.add(n);
                }
                if (n==start){
                    return;
                }
            }
        }
    }
    
    private List<Integer> buildPath(int[] predecessors, int start, int end){
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
}
