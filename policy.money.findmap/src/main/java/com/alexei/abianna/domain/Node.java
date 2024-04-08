package com.alexei.abianna.domain;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
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

    public Node getUpBlock() {
        return !this.getNeighbors().isEmpty() ? this.getNeighbors().get(UP) : null;
    }
    public Node getDownBlock() {
        return !this.getNeighbors().isEmpty() ? this.getNeighbors().get(DOWN) : null;
    }
    public Node getLeftBlock() {
        return !this.getNeighbors().isEmpty() ? this.getNeighbors().get(LEFT) : null;
    }
    public Node getRightBlock() {
        return isRightNeighborsExists() ? this.getNeighbors().get(RIGHT) : getLeftBlock();
    }

    public boolean isRightNeighborsExists() {
        return this.getNeighbors().size() > RIGHT;
    }
}
