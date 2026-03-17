package io.github.nataliabpw.maze_solver.model;

import java.util.List;

public class MazePath {
    private final List<Integer> path;
    private final MazeData mazeData;
    
    public MazePath(List<Integer> path, MazeData mazeData){
        this.path = path;
        this.mazeData = mazeData;
    }

    public void changePathCellsType(Cell type){
        int columns = mazeData.getColumns();
        int currX, currY;
        int nextX, nextY;
        for(int i=0; i < path.size()-1; i++){
            currX = cellNumber(path.get(i) % columns);
            currY = cellNumber(path.get(i) / columns);
            nextX = cellNumber(path.get(i+1) % columns);
            nextY = cellNumber(path.get(i+1) / columns);
            if ( currX == nextX){
                int min = Math.min(currY, nextY);
                int max = Math.max(currY, nextY);
                for (int j = min; j <= max; j++){
                    if(mazeData.getCellType(currX, j) != Cell.START && mazeData.getCellType(currX, j) != Cell.END)
                    mazeData.setCellType(type, currX, j);
                }
            } else{
                int min = Math.min(currX, nextX);
                int max = Math.max(currX, nextX);
                for (int j = min; j <= max; j++){
                    if(mazeData.getCellType(j, currY) != Cell.START && mazeData.getCellType(j, currY) != Cell.END)
                    mazeData.setCellType(type, j, currY);
                }
            }
        }
    }
    
    private int cellNumber(int node){
        return node * 2 + 1;
    }
}
