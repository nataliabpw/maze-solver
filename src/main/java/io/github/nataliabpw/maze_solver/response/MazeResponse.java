package io.github.nataliabpw.maze_solver.response;

import java.util.List;

public class MazeResponse {
    private final List<String> maze;

    public MazeResponse(List<String> maze){
        this.maze = maze;
    }

    public List<String> getMaze(){
        return maze;
    }
}
