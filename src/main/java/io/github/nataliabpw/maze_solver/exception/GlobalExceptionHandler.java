package io.github.nataliabpw.maze_solver.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.nataliabpw.maze_solver.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex){
        return ResponseEntity.badRequest()
        .body(new ErrorResponse(ex.getMessage()));
    }
    
    @ExceptionHandler(MazePathNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMazePathNotFound(MazePathNotFoundException ex){
        return ResponseEntity.status(404)
        .body(new ErrorResponse(ex.getMessage()));
    }

}
