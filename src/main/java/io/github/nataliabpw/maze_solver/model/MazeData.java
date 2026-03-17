package io.github.nataliabpw.maze_solver.model;

import java.util.List;

public class MazeData {
    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }
    
    public int getNumberOfNodes() {
        return rows*columns;
    }

    public int getStart() {
        return start;
    }
    
    public int getStartX() {
        return start % columns;
    }
    
    public int getStartY() {
        return start / columns;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }
    
    public int getEndX() {
        return end % columns;
    }
    
    public int getEndY() {
        return end / columns;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public List<List<Cell>> getMazeCells() {
        return mazeCells;
    }

    public void setMazeCells(List<List<Cell>> mazeCells) {
        this.mazeCells = mazeCells;
    }
    
    public void setCellType(Cell type, int x, int y){
        mazeCells.get(y).set(x, type);
    }
    
    public Cell getCellType( int x, int y){
        return mazeCells.get(y).get(x);
    }

    public AdjacencyMatrix getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public void setAdjacencyMatrix(AdjacencyMatrix adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }
    
    public void initAdjacencyMatrix(){
        this.adjacencyMatrix = new AdjacencyMatrix();
    }
    
    public void addPathToMatrix(int node, Passage passage){
        adjacencyMatrix.addPath(node, passage);
    }

    public int[] getNeighbours(int current){
        return adjacencyMatrix.getNodesConnectedWitchCurrent(current, columns);
    }
    
    private int rows;
    private int columns;
    private int start;
    private int end;
    
    private List<List<Cell>> mazeCells;
    
    private AdjacencyMatrix adjacencyMatrix;
}
