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
        
        List<String> mazeLines = new ArrayList<>();
        for (List<Cell> subList: mazeCells){
            StringBuilder line = new StringBuilder();

            for (Cell cell : subList) {
                char c;
                switch(cell){
                    case WALL -> c = 'X';
                    case START -> c = 'P';
                    case END -> c = 'K';
                    case SPACE -> c = ' ';
                    case PATH -> c = 'O';
                    default -> c = ' ';
                }
                line.append(c);
            }

            mazeLines.add(line.toString());
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("maze", mazeLines);
        return response;
    }
}
