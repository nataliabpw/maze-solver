package io.github.nataliabpw.maze_solver.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.github.nataliabpw.maze_solver.response.MazeResponse;
import io.github.nataliabpw.maze_solver.service.MazeService;

import java.io.IOException;

@RestController
@RequestMapping("/api/maze")
public class MazeController {

    private final MazeService mazeService;

    public MazeController(MazeService mazeService){
        this.mazeService = mazeService;
    }

    @PostMapping("/upload")
    public MazeResponse uploadMaze (@RequestParam("file") MultipartFile file) throws IOException {
        validateFile(file);
        MazeResponse mazeResponse = mazeService.uploadMaze(file);
        return mazeResponse;
    }

    @PostMapping("/solve")
    public MazeResponse solveMaze (
        @RequestParam("file") MultipartFile file,
        @RequestParam("startX") int startX,
        @RequestParam("startY") int startY,
        @RequestParam("endX") int endX,
        @RequestParam("endY") int endY
    ) throws IOException {
        validateFile(file);
        MazeResponse mazeResponse = mazeService.solveMaze(file, startX, startY, endX, endY);
        return mazeResponse;
    }
    
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }
    }
}
