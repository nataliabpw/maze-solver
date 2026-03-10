package io.github.nataliabpw.maze_solver;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/maze")
public class MazeController {

    @PostMapping("/upload")
    public Map<String, Object> uploadMap (@RequestParam("file") MultipartFile file) throws IOException {
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
}
