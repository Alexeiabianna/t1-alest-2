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

    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;

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
        Node nextBlock = getRightBlock(startBlock);
        if (Objects.nonNull(startBlock)) {
            pathMap.addBlockToMap(startBlock.getValue());

            while (isPathBlock(nextBlock.getValue())) {
                pathMap.addBlockToMap(nextBlock.getValue());
                nextBlock = getNextBlock(currentBlock, nextBlock);
                currentBlock = nextBlock;
                if (Objects.isNull(nextBlock)) {
                    break;
                }
            }


        }

        Logger.getAnonymousLogger().info("Amount bills result: " + pathMap.getAmountBills());
    }

    private Node getNextBlock(final Node current, final Node nextBlock) {
        final String currentValue = String.valueOf(current.getValue());
        final String nextValue = String.valueOf(nextBlock.getValue());
        if (PathBlocksEnum.HORIZONTAL_WAY.getValue().equals(currentValue) && PathBlocksEnum.TURN_LEFT_WAY.getValue().equals(nextValue)) {
            return getUpBlock(nextBlock);
        }
        if (PathBlocksEnum.VERTICAL_WAY.getValue().equals(currentValue) && PathBlocksEnum.TURN_LEFT_WAY.getValue().equals(nextValue)) {
            return getLeftBlock(nextBlock);
        }
        if (PathBlocksEnum.HORIZONTAL_WAY.getValue().equals(currentValue) && PathBlocksEnum.TURN_RIGHT_WAY.getValue().equals(nextValue)) {
            return getDownBlock(nextBlock);
        }
        if (PathBlocksEnum.TURN_RIGHT_WAY.getValue().equals(currentValue) && PathBlocksEnum.VERTICAL_WAY.getValue().equals(nextValue)) {
            return getRightBlock(nextBlock);
        }
        if (PathBlocksEnum.VERTICAL_WAY.getValue().equals(currentValue) && PathBlocksEnum.TURN_RIGHT_WAY.getValue().equals(nextValue)) {
            return getRightBlock(nextBlock);
        }
        if (StringUtils.isNumeric(currentValue) && PathBlocksEnum.TURN_LEFT_WAY.getValue().equals(nextValue)) {
            return getUpBlock(nextBlock);
        }
        if (StringUtils.isNumeric(currentValue) && PathBlocksEnum.TURN_LEFT_WAY.getValue().equals(nextValue)) {
            return getLeftBlock(nextBlock);
        }
        if (StringUtils.isNumeric(currentValue) && PathBlocksEnum.TURN_RIGHT_WAY.getValue().equals(nextValue)) {
            return getDownBlock(nextBlock);
        }
        if (StringUtils.isNumeric(currentValue) && PathBlocksEnum.TURN_RIGHT_WAY.getValue().equals(nextValue)) {
            return getRightBlock(nextBlock);
        }
        if (isContinueInDown(current.getValue())) {
            return getDownBlock(nextBlock);
        }
        if (isContinueInUp(current.getValue())) {
            return getUpBlock(nextBlock);
        }
        if (isContinueInLeft(current.getValue())) {
            return getLeftBlock(nextBlock);
        }
        if (isContinueInRight(current.getValue())) {
            return getRightBlock(nextBlock);
        }
        return null;
    }

    private boolean isContinueInRight(final char block) {
        final String value = String.valueOf(block);
        return StringUtils.isNumeric(value) || PathBlocksEnum.HORIZONTAL_WAY.getValue().equals(value);
    }

    private boolean isContinueInLeft(final char block) {
        final String value = String.valueOf(block);
        return StringUtils.isNumeric(value) || PathBlocksEnum.HORIZONTAL_WAY.getValue().equals(value);
    }

    private boolean isContinueInUp(final char block) {
        final String value = String.valueOf(block);
        return StringUtils.isNumeric(value) || PathBlocksEnum.VERTICAL_WAY.getValue().equals(value);
    }

    private boolean isContinueInDown(final char block) {
        final String value = String.valueOf(block);
        return StringUtils.isNumeric(value) || PathBlocksEnum.VERTICAL_WAY.getValue().equals(value);
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

    private Node getUpBlock(final Node block) {
        return !block.getNeighbors().isEmpty() ? block.getNeighbors().get(UP) : null;
    }
    private Node getDownBlock(final Node block) {
        return !block.getNeighbors().isEmpty() ? block.getNeighbors().get(DOWN) : null;
    }
    private Node getLeftBlock(final Node block) {
        return !block.getNeighbors().isEmpty() ? block.getNeighbors().get(LEFT) : null;
    }
    private Node getRightBlock(final Node block) {
        return isRightNeighborsExists(block) ? block.getNeighbors().get(RIGHT) : getLeftBlock(block);
    }

    private boolean isRightNeighborsExists(final Node block) {
        return block.getNeighbors().size() > RIGHT;
    }

    public PathGraph getPathGraph() {
        return this.pathGraph;
    }

}
