package com.alexei.abianna.domain;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private char value;
    private int row;
    private int col;
    List<Node> neighbors;

    public Node(char value, int row, int col) {
        this.value = value;
        this.row = row;
        this.col = col;
        this.neighbors = new ArrayList<>();
    }

    public char getValue() {
        return value;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }
}
