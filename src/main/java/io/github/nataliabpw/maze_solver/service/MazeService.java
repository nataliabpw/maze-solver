package io.github.nataliabpw.maze_solver.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.github.nataliabpw.maze_solver.model.Cell;
import io.github.nataliabpw.maze_solver.model.MazeData;
import io.github.nataliabpw.maze_solver.model.MazePath;
import io.github.nataliabpw.maze_solver.parser.MazeParser;
import io.github.nataliabpw.maze_solver.response.MazeResponseBuilder;
import io.github.nataliabpw.maze_solver.solver.MazeSolver;

@Service
public class MazeService {

    private final MazeParser mazeParser;
    private final MazeResponseBuilder mazeResponseBuilder;

    public MazeService(MazeParser mazeParser, MazeResponseBuilder mazeResponseBuilder){
        this.mazeParser = mazeParser;
        this.mazeResponseBuilder = mazeResponseBuilder;
    }

    public Map<String, Object> uploadMaze(MultipartFile file) throws IOException{
        MazeData mazeData = new MazeData();
        mazeParser.parseFile(mazeData, file);

        List<List<Cell>> mazeCells = mazeData.getMazeCells();
        List<String> mazeLines = mazeResponseBuilder.buildMazeResponse(mazeCells);
        
        Map<String, Object> response = new HashMap<>();
        response.put("maze", mazeLines);
        return response;
    }

    public Map<String, Object> solveMaze (
        MultipartFile file,
        int startX,
        int startY,
        int endX,
        int endY
    ) throws IOException{
        MazeData mazeData = new MazeData();
        mazeParser.parseFile(mazeData, file);

        int columns = mazeData.getColumns();
        int startNode = getNodeNumber(startX, startY, columns);
        int endNode = getNodeNumber(endX, endY, columns);

        mazeData.setStart(startNode);
        mazeData.setEnd(endNode);

        MazeSolver mazeSolver = new MazeSolver(mazeData);
        List<Integer> nodesInPath = mazeSolver.generatePath();

        nodesInPath = adjustPathForNonNodePoint(nodesInPath, startX, startY, startNode, columns);
        nodesInPath = adjustPathForNonNodePoint(nodesInPath, endX, endY, endNode, columns);
        
        MazePath path = new MazePath(nodesInPath, mazeData);
        path.changePathCellsType(Cell.PATH);

        List<List<Cell>> mazeCells = mazeData.getMazeCells();
        List<String> mazeLines = mazeResponseBuilder.buildMazeResponse(mazeCells);
        
        Map<String, Object> response = new HashMap<>();
        response.put("maze", mazeLines);
        return response;
    }

    private int getNodeNumber(int x, int y, int columns){
        int nodeX = (x-1)/2;
        int nodeY = (y-1)/2;
        return nodeX + columns * nodeY;
    }

    private List<Integer> adjustPathForNonNodePoint (List<Integer> nodesInPath, int x, int y, int node, int columns){
        if (x % 2 == 0){
            if (nodesInPath.contains(Integer.valueOf(node+1))){
                nodesInPath.remove(Integer.valueOf(node));
            }
        }
        if (y % 2 == 0){
            if (nodesInPath.contains(Integer.valueOf(node+columns))){
                nodesInPath.remove(Integer.valueOf(node));
            }
        }
        return nodesInPath;
    }

}
