package io.github.nataliabpw.maze_solver.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import io.github.nataliabpw.maze_solver.model.Cell;
import io.github.nataliabpw.maze_solver.model.MazeData;
import io.github.nataliabpw.maze_solver.model.Passage;

@Component
public class MazeParser {

    public MazeData parseFile(MultipartFile file){
        MazeData mazeData = new MazeData();
        List<List<Cell>> cells = new ArrayList<>();
        mazeData.initAdjacencyMatrix();
        int columns = 0;

        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(file.getInputStream()))){

            String line;

            while ((line = reader.readLine()) != null){
                List<Cell> subList = new ArrayList<>();
                for (char i: line.toCharArray()){
                    switch(i){
                        case ' ' -> {
                            subList.add(Cell.SPACE);
                            int cellX = subList.size() - 1;
                            int cellY = cells.size();
                            int node = getNodeNumber(columns, cellX, cellY);
                            if(isHorizontalPassage(cellX, cellY)){
                                mazeData.addPathToMatrix(node + 1, Passage.LEFT);
                                mazeData.addPathToMatrix(node, Passage.RIGHT);
                            } 
                            if (isVerticalPassage(cellX, cellY)){
                                mazeData.addPathToMatrix(node, Passage.BOTTOM);
                                mazeData.addPathToMatrix(node+columns, Passage.TOP);
                            }
                            
                        }
                        case 'X' -> subList.add(Cell.WALL);
                        case 'P' -> subList.add(Cell.WALL);
                        case 'K' -> subList.add(Cell.WALL);
                        default -> throw new IllegalArgumentException("Invalid character in maze: " + i);
                    }
                }
                if (cells.isEmpty()){
                    validateFirstRow(subList);
                    columns = subList.size()/2;
                } else {
                    validateRow(subList, columns);
                }
                cells.add(subList);
            }

            validateLastRow(cells.getLast());
            validateRowsNumber(cells);

            mazeData.setMazeCells(cells);
            mazeData.setColumns((cells.get(0).size() - 1) / 2);
            mazeData.setRows((cells.size() - 1) / 2);
        } catch (IOException e){
            throw new IllegalArgumentException("Failed to read maze file");
        }
        return mazeData;
    }    

    private int getNodeNumber(int columns, int cellX, int cellY) {
        int rowIndex = (cellY + 1) / 2 - 1;
        int columnIndex = (cellX + 1) / 2;
        int nodeNumber = columns * rowIndex  + columnIndex - 1;
        return nodeNumber;
    }

    private boolean isHorizontalPassage(int cellX, int cellY){
        return cellY % 2 == 1 && cellX % 2 == 0;
    }

    private boolean isVerticalPassage(int cellX, int cellY){
        return cellY % 2 == 0 && cellX % 2 == 1;
    }

    private void validateFirstRow(List<Cell> firstRow){
        if (firstRow.stream().anyMatch(cell -> cell!=Cell.WALL)){
            throw new IllegalArgumentException("First row of maze must contain only wall-cells!");
        }
        if (firstRow.size()<3){
            throw new IllegalArgumentException("Invalid maze size!");
        }
    }

    private void validateRow(List<Cell> row, int expectedColumns){
        Cell firstCell = row.getFirst();
        Cell lastCell = row.getLast();
        if (firstCell!=Cell.WALL || lastCell!=Cell.WALL){
            throw new IllegalArgumentException("The boundaries of the maze must be wall-cells!");
        }
        if (row.size()/2!=expectedColumns){
            throw new IllegalArgumentException("The rows of the maze must be the same length!");
        }
    }

    private void validateLastRow(List<Cell> lastRow){
        if (lastRow.stream().anyMatch(cell -> cell!=Cell.WALL)){
            throw new IllegalArgumentException("Last row of maze must contain only wall-cells!");
        }
    }

    private void validateRowsNumber(List<List<Cell>> cells){
        if (cells.size() < 3) {
            throw new IllegalArgumentException("Maze must have at least 3 rows");
        }
    }

}
