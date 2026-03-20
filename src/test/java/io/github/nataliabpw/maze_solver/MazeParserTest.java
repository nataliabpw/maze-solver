package io.github.nataliabpw.maze_solver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import io.github.nataliabpw.maze_solver.model.MazeData;
import io.github.nataliabpw.maze_solver.parser.MazeParser;

public class MazeParserTest {
    private MazeParser mazeParser = new MazeParser();
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
    void shouldParseMaze() throws Exception{
        BufferedReader reader = new BufferedReader(new StringReader(maze));
        MazeData mazeData = mazeParser.parse(reader);
        
        assertEquals(6, mazeData.getRows());
        assertEquals(6, mazeData.getColumns());
    }

}
