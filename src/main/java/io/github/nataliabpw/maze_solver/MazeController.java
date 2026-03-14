package io.github.nataliabpw.maze_solver;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/maze")
public class MazeController {

    @PostMapping("/upload")
    public Map<String, Object> uploadMaze (@RequestParam("file") MultipartFile file) throws IOException {
        MazeData mazeData = new MazeData();
        MazeReader mazeReader = new MazeReader();
        mazeReader.readFromFile(mazeData, file);

        List<List<Cell>> mazeCells = mazeData.getMazeCells();
        MazeResponseBuilder mazeResponseBuilder = new MazeResponseBuilder();
        List<String> mazeLines = mazeResponseBuilder.buildMazeResponse(mazeCells);
        
        Map<String, Object> response = new HashMap<>();
        response.put("maze", mazeLines);
        return response;
    }

    @PostMapping("/solve")
    public Map<String, Object> solveMaze (
        @RequestParam("file") MultipartFile file,
        @RequestParam("startX") int startX,
        @RequestParam("startY") int startY,
        @RequestParam("endX") int endX,
        @RequestParam("endY") int endY
    ) throws IOException {
        MazeData mazeData = new MazeData();
        MazeReader mazeReader = new MazeReader();
        mazeReader.readFromFile(mazeData, file);

        int columns = mazeData.getColumns();
        int startNode = getNodeNumber(startX, startY, columns);
        int endNode = getNodeNumber(endX, endY, columns);

        mazeData.setStart(startNode);
        mazeData.setEnd(endNode);

        MazeSolver mazeSolver = new MazeSolver(mazeData);
        List<Integer> nodesInPath = mazeSolver.generatePath();

        nodesInPath = adjustPathForNonNodePoint(nodesInPath, startX, startY, startNode, columns);
        nodesInPath = adjustPathForNonNodePoint(nodesInPath, endX, endY, endNode, columns);
        
        Path path = new Path(nodesInPath, mazeData);
        path.changePathCellsType(Cell.PATH);

        List<List<Cell>> mazeCells = mazeData.getMazeCells();
        MazeResponseBuilder mazeResponseBuilder = new MazeResponseBuilder();
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
