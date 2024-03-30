package com.alexei.abianna.domain;

import java.util.List;

public class PathGraph {
    private Node[][] grid;

    public PathGraph(final List<String[]> fileLines) {
        int numRows = fileLines.size();
        int numCols = fileLines.get(0).length;
        grid = new Node[numRows][numCols];

        // Initialize nodes
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                char value = fileLines.get(i)[j].charAt(0);
                grid[i][j] = new Node(value, i, j);
            }
        }
        // Establish connections between neighboring cells
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (i > 0) {
                    grid[i][j].neighbors.add(grid[i - 1][j]); // Up neighbor
                }
                if (i < numRows - 1) {
                    grid[i][j].neighbors.add(grid[i + 1][j]); // Down neighbor
                }
                if (j > 0) {
                    grid[i][j].neighbors.add(grid[i][j - 1]); // Left neighbor
                }
                if (j < numCols - 1) {
                    grid[i][j].neighbors.add(grid[i][j + 1]); // Right neighbor
                }
            }
        }
    }

    public Node[][] getGrid() {
        return grid;
    }

}
