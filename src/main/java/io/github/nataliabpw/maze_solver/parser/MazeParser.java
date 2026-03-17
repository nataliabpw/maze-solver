package io.github.nataliabpw.maze_solver.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.github.nataliabpw.maze_solver.model.Cell;
import io.github.nataliabpw.maze_solver.model.MazeData;
import io.github.nataliabpw.maze_solver.model.Passage;

public class MazeParser {

    public void parseFile(MazeData mazeData, MultipartFile file) throws IOException{
        List<List<Cell>> cells = new ArrayList<>();
        mazeData.initAdjacencyMatrix();
        int columns = 0;

        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(file.getInputStream()))){

            String line;

            while ((line = reader.readLine()) != null){
                boolean nextIsStart = false;
                List<Cell> subList = new ArrayList<>();
                for (Character i: line.toCharArray()){
                    switch(i){
                        case ' ' -> {
                            if(nextIsStart){
                                subList.add(Cell.START);
                                nextIsStart = false;
                            }
                            else
                                subList.add(Cell.SPACE);
                            if(cells.size() % 2 == 1 && subList.size() % 2 == 1){
                                int node = countNode(columns, cells, subList);
                                mazeData.addPathToMatrix(node+1, Passage.LEFT);
                                mazeData.addPathToMatrix(node, Passage.RIGHT);
                            } 
                            if (cells.size() % 2 == 0 && subList.size() % 2 == 0 && !cells.isEmpty() && !subList.isEmpty()){
                                mazeData.addPathToMatrix(countNode(columns, cells, subList), Passage.BOTTOM);
                                mazeData.addPathToMatrix(countNode(columns, cells, subList)+columns, Passage.TOP);
                            }
                            
                        }
                        case 'X' -> subList.add(Cell.WALL);
                        case 'P' -> subList.add(Cell.WALL);
                        case 'K' -> subList.add(Cell.WALL);

                    }
                }
                if (cells.isEmpty()){
                    columns = subList.size()/2;
                }
                cells.add(subList);
            }
            mazeData.setMazeCells(cells);
            mazeData.setColumns((cells.get(0).size() - 1) / 2);
            mazeData.setRows((cells.size() - 1) / 2);
        }
    }    

    private int countNode(int columns, List<List<Cell>> cells, List<Cell> subList) {
        int node = columns * ((cells.size()+1)/2 - 1)  + subList.size()/2;
        return node-1;
    }
}
