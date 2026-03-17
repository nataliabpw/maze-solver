package io.github.nataliabpw.maze_solver.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.github.nataliabpw.maze_solver.response.MazeResponse;
import io.github.nataliabpw.maze_solver.service.MazeService;

@RestController
@RequestMapping("/api/maze")
public class MazeController {

    private final MazeService mazeService;

    public MazeController(MazeService mazeService){
        this.mazeService = mazeService;
    }

    @PostMapping("/upload")
    public MazeResponse uploadMaze (@RequestParam("file") MultipartFile file) {
        validateFile(file);
        return mazeService.uploadMaze(file);
    }

    @PostMapping("/solve")
    public MazeResponse solveMaze (
        @RequestParam("file") MultipartFile file,
        @RequestParam("startX") int startX,
        @RequestParam("startY") int startY,
        @RequestParam("endX") int endX,
        @RequestParam("endY") int endY
    ) {
        validateFile(file);
        return mazeService.solveMaze(file, startX, startY, endX, endY);
    }
    
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }
    }
}
