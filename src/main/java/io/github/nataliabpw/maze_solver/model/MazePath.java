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
            currX = getCellNumber(path.get(i) % columns);
            currY = getCellNumber(path.get(i) / columns);
            nextX = getCellNumber(path.get(i+1) % columns);
            nextY = getCellNumber(path.get(i+1) / columns);
            markLine(currX, nextX, currY, nextY, type);
        }
    }
    
    private int getCellNumber(int node){
        return node * 2 + 1;
    }

    private void markLine(int x1, int x2, int y1, int y2, Cell type){
        if ( x1 == x2){
            int min = Math.min(y1, y2);
            int max = Math.max(y1, y2);
            for (int y = min; y <= max; y++){
                setIfNotStartOrEnd(x1, y, type);
            }
        } else{
            int min = Math.min(x1, x2);
            int max = Math.max(x1, x2);
            for (int x = min; x <= max; x++){
                setIfNotStartOrEnd(x, y1, type);
            }
        }
    }

    private void setIfNotStartOrEnd(int x, int y, Cell type){
        Cell cell = mazeData.getCellType(x, y);
        if(cell != Cell.START && cell != Cell.END)
                    mazeData.setCellType(type, x, y);
    }
}
