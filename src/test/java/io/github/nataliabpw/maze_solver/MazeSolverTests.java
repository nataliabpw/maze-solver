package io.github.nataliabpw.maze_solver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.nataliabpw.maze_solver.model.MazeData;
import io.github.nataliabpw.maze_solver.parser.MazeParser;
import io.github.nataliabpw.maze_solver.solver.MazeSolver;

public class MazeSolverTests {
    private MazeParser mazeParser = new MazeParser();
    private MazeSolver mazeSolver = new MazeSolver();
    private String maze = """
        XXXXXXXXXXXXX
        X       X   X
        X XXX X XXX X
        X X X X     X
        X X X XXXXX X
        X X X X   X X
        X X X X X XXX
        X   X   X   X
        XXX XXXXXXX X
        X   X     X X
        X XXX XXXXX X
        X     X     X
        XXXXXXXXXXXXX
        """.stripIndent();

    @Test
    void shouldFindPath() throws Exception{
        BufferedReader reader = new BufferedReader(new StringReader(maze));

        MazeData mazeData = mazeParser.parse(reader);
        mazeData.setStart(0);
        mazeData.setEnd(5);
        
        List<Integer> expectedResult = List.of(0, 1, 2, 3, 9, 10, 11, 5);

        List<Integer> result = mazeSolver.generatePath(mazeData);
        assertEquals(expectedResult, result);
    }
}
