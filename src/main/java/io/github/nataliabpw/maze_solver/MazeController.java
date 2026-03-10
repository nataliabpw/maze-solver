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

        MazeSolver mazeSolver = new MazeSolver(mazeData);
        Path path = new Path(mazeSolver.generatePath(), mazeData);
        path.changePathCellsType(Cell.PATH);

        List<List<Cell>> mazeCells = mazeData.getMazeCells();
        MazeResponseBuilder mazeResponseBuilder = new MazeResponseBuilder();
        List<String> mazeLines = mazeResponseBuilder.buildMazeResponse(mazeCells);
        
        Map<String, Object> response = new HashMap<>();
        response.put("maze", mazeLines);
        return response;
    }
}
