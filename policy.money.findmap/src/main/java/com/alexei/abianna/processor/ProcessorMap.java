package com.alexei.abianna.processor;

import com.alexei.abianna.domain.Node;
import com.alexei.abianna.domain.PathBlocksEnum;
import com.alexei.abianna.domain.PathGraph;
import com.alexei.abianna.domain.PathMap;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.logging.Logger;

public class ProcessorMap {

    private PathGraph pathGraph;
    private PathMap pathMap;

    public ProcessorMap(final PathMap pathMap) {
        this.pathMap = pathMap;
    }

    public void loadingMap(final List<String[]> fileLines) {
        fileLines.remove(0);
        Logger.getAnonymousLogger().info("Size File: " + fileLines.size());
        this.pathGraph = new PathGraph(fileLines);
    }

    public void process() {
        pathGraph.getGrid();
        isFirstBlock(getPathGraph());
        var block = getFirstBlock(getPathGraph());
        var nextBlock = getRightBlock(block);
        //TODO insert method that process a search for bills

        Logger.getAnonymousLogger().info("Amount bills result: " + pathMap.getAmountBills());
    }

    private int checkIsBillOnAPath(final char element) {
        String value = String.valueOf(element);
        return StringUtils.isNumeric(value) ? Integer.parseInt(value) : -1;
    }

    private boolean isPathBlock(final char element) {
        String value = String.valueOf(element);
        if (PathBlocksEnum.HORIZONTAL_WAY.getValue().equals(value)) {
            return true;
        }
        if (PathBlocksEnum.TURN_LEFT_WAY.getValue().equals(value)) {
            return true;
        }
        if (PathBlocksEnum.TURN_RIGHT_WAY.getValue().equals(value)) {
            return true;
        }
        if (PathBlocksEnum.VERTICAL_WAY.getValue().equals(value)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isFirstBlock(final PathGraph pathGraph) {
        for(Node[] node : pathGraph.getGrid()) {
            if(PathBlocksEnum.HORIZONTAL_WAY.getValue().equals(String.valueOf(node[0].getValue()))) {
                char firstBlock = node[0].getValue();
                return true;
            }
        }
        return false;
    }
    private Node getFirstBlock(final PathGraph pathGraph) {
        for(Node[] node : pathGraph.getGrid()) {
            if(PathBlocksEnum.HORIZONTAL_WAY.getValue().equals(String.valueOf(node[0].getValue()))) {
                return node[0];
            }
        }
        return null;
    }

    private Node getUpBlock(final Node block) {
        return block.getNeighbors().get(0);
    }
    private Node getDownBlock(final Node block) {
        return block.getNeighbors().get(1);
    }
    private Node getLeftBlock(final Node block) {
        return block.getNeighbors().get(2);
    }
    private Node getRightBlock(final Node block) {
        return block.getNeighbors().get(3);
    }

    public PathGraph getPathGraph() {
        return this.pathGraph;
    }

}
