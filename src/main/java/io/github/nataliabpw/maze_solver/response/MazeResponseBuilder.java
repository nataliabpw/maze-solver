package io.github.nataliabpw.maze_solver.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import io.github.nataliabpw.maze_solver.model.Cell;

@Component
public class MazeResponseBuilder {
    
    public List<String> buildMazeResponse(List<List<Cell>> mazeCells){
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

        return mazeLines;
    }
}
