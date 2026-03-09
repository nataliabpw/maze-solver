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
        Map<String, Object> response = new HashMap<>();
        return response;
    }
}
