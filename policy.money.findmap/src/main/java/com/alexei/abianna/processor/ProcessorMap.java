package com.alexei.abianna.processor;

import com.alexei.abianna.domain.Node;
import com.alexei.abianna.domain.PathBlocksEnum;
import com.alexei.abianna.domain.PathGraph;
import com.alexei.abianna.domain.PathMap;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
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
        //TODO insert method that process a search for bills

        Node startBlock = getFirstBlock(getPathGraph());
        Node currentBlock = startBlock;
        if (Objects.nonNull(startBlock)) {
            Node nextBlock = startBlock.getRightBlock();
            pathMap.addBlockToMap(startBlock.getValue());

            while (isPathBlock(nextBlock.getValue())) {
                pathMap.addBlockToMap(nextBlock.getValue());
                var temp = nextBlock;
                nextBlock = getNextWay(currentBlock, nextBlock);
                currentBlock = temp;
                if (Objects.isNull(nextBlock)) {
                    break;
                }
            }
        }

        Logger.getAnonymousLogger().info("Amount bills result: " + pathMap.getAmountBills());
    }

    private Node getNextWay(final Node current, final Node nextBlock) {
        final String currentValue = String.valueOf(current.getValue());
        final String nextValue = String.valueOf(nextBlock.getValue());
        final String verifyNextBottom = String.valueOf(nextBlock.getDownBlock().getValue());
        final String verifyNextUpper = String.valueOf(nextBlock.getUpBlock().getValue());
        if (PathBlocksEnum.HORIZONTAL_WAY.getValue().equals(currentValue) && PathBlocksEnum.TURN_LEFT_WAY.getValue().equals(nextValue)) {
            return nextBlock.getUpBlock();
        }
        if (PathBlocksEnum.HORIZONTAL_WAY.getValue().equals(currentValue) && PathBlocksEnum.TURN_RIGHT_WAY.getValue().equals(nextValue)) {
            return nextBlock.getDownBlock();
        }
        if (PathBlocksEnum.TURN_RIGHT_WAY.getValue().equals(currentValue)) {
            if (PathBlocksEnum.VERTICAL_WAY.getValue().equals(nextValue) && !PathBlocksEnum.VERTICAL_WAY.getValue().equals(verifyNextBottom)) {
                return nextBlock.getRightBlock().getRightBlock();
            } else if (PathBlocksEnum.HORIZONTAL_WAY.getValue().equals(nextValue)) {
                return nextBlock.getRightBlock();
            }
        }
        if (PathBlocksEnum.VERTICAL_WAY.getValue().equals(currentValue) && PathBlocksEnum.TURN_LEFT_WAY.getValue().equals(nextValue)) {
            return nextBlock.getLeftBlock();
        }
        if (PathBlocksEnum.VERTICAL_WAY.getValue().equals(currentValue) && PathBlocksEnum.TURN_RIGHT_WAY.getValue().equals(nextValue)) {
            return nextBlock.getRightBlock();
        }

        return null;
    }

    private int checkIsBillOnAPath(final char element) {
        String value = String.valueOf(element);
        return StringUtils.isNumeric(value) ? Integer.parseInt(value) : -1;
    }

    private boolean isPathBlock(final char element) {
        final String value = String.valueOf(element);
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
            if (checkIsBillOnAPath(element) != -1) {
                pathMap.addBills(Integer.parseInt(String.valueOf(element)));
                return true;
            }
            return false;
        }
    }

    private Node getFirstBlock(final PathGraph pathGraph) {
        for(Node[] node : pathGraph.getGrid()) {
            if(PathBlocksEnum.HORIZONTAL_WAY.getValue().equals(String.valueOf(node[0].getValue()))) {
                char firstBlock = node[0].getValue();
                Logger.getAnonymousLogger().info("First block of map founded: " + firstBlock + " at row: " + node[0].getRow());
                return node[0];
            }
        }
        return null;
    }

    public PathGraph getPathGraph() {
        return this.pathGraph;
    }

}
