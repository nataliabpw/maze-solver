package io.github.nataliabpw.maze_solver.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import io.github.nataliabpw.maze_solver.service.MazeService;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/maze")
public class MazeController {

    private final MazeService mazeService;

    public MazeController(MazeService mazeService){
        this.mazeService = mazeService;
    }

    @PostMapping("/upload")
    public Map<String, Object> uploadMaze (@RequestParam("file") MultipartFile file) throws IOException {
        return mazeService.uploadMaze(file);
    }

    @PostMapping("/solve")
    public Map<String, Object> solveMaze (
        @RequestParam("file") MultipartFile file,
        @RequestParam("startX") int startX,
        @RequestParam("startY") int startY,
        @RequestParam("endX") int endX,
        @RequestParam("endY") int endY
    ) throws IOException {
        return mazeService.solveMaze(file, startX, startY, endX, endY);
    }
    
}
