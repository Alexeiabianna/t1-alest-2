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
//                nextBlock = getNextWay(currentBlock, nextBlock);
                nextBlock = getNext(currentBlock, nextBlock);
                currentBlock = temp;
                if (Objects.isNull(nextBlock)) {
                    break;
                }
            }
        }

        Logger.getAnonymousLogger().info("Amount bills result: " + pathMap.getAmountBills());
    }

    private Node getNext(final Node current, final Node next) {
        int currentCol = current.getCol();
        int currentRow = current.getRow();

        int nextCol = next.getCol();
        int nextRow = next.getRow();

        if (isCorner(current)) {
            saveCorner(current);
        }

        if (currentCol == nextCol || currentRow == nextRow) {
            int size = pathMap.getCornerList().size();
            Node corner = size > 0 ? pathMap.getCornerList().get(size - 1) : null;

            // Change directions
            if (isHorizontal(getCharNodeValue(current)) && isRight(getCharNodeValue(next))) {
                return next.getDownBlock();
            }
            if (isHorizontal(getCharNodeValue(current)) && isLeft(getCharNodeValue(next))) {
                return next.getUpBlock();
            }
            if (isVertical(getCharNodeValue(current)) && isRight(getCharNodeValue(next))) {
                return next.getRightBlock();
            }
            if (isVertical(getCharNodeValue(current)) && isLeft(getCharNodeValue(next))) {
                return next.getLeftBlock();
            }

            // Go ahead
            if (Objects.nonNull(corner)) {
                if (isHorizontal(getCharNodeValue(next))) {
                    if (isLeft(getCharNodeValue(corner))) {
                        return next.getLeftBlock();
                    }
                    if (isRight(getCharNodeValue(corner))) {
                        return next.getRightBlock();
                    }
                    if (isLeft(getCharNodeValue(corner))) {
                        if (isPipe(getCharNodeValue(next))) {
                            return next.getRightBlock();
                        }
                    }
                    if (isRight(getCharNodeValue(corner))) {
                        if (isPipe(getCharNodeValue(next))) {
                            return next.getRightBlock();
                        }
                    }
                }
                if (isNumeric(getCharNodeValue(next))) {
                    if (isLeft(getCharNodeValue(corner))) {
                        return next.getUpBlock();
                    }
                    if (isRight(getCharNodeValue(corner))) {
                        return next.getDownBlock();
                    }
                }
                if (isVertical(getCharNodeValue(next))) {
                    if (isLeft(getCharNodeValue(corner))) {
                        return next.getUpBlock();
                    }
                    if (isRight(getCharNodeValue(corner))) {
                        return next.getDownBlock();
                    }
                }
            }
        }
        return null;
    }

    private boolean isPipe(final String value) {
        return PathBlocksEnum.VERTICAL_WAY.getValue().equals(value);
    }

    private boolean isCorner(Node node) {
        return isLeft(getCharNodeValue(node)) || isRight(getCharNodeValue(node));
    }

    private Node getNextWay(final Node current, final Node nextBlock) {
        final String currentValue = getCharNodeValue(current);
        getCharNodeValue(current.getUpBlock());
        getCharNodeValue(current.getDownBlock());
        getCharNodeValue(current.getLeftBlock());
        getCharNodeValue(current.getRightBlock());

        final String nextValue = getCharNodeValue(nextBlock);
        getCharNodeValue(nextBlock.getUpBlock());
        final String nextDownValue = getCharNodeValue(nextBlock.getDownBlock());
        final String nextLeftValue = getCharNodeValue(nextBlock.getLeftBlock());
        final String nextRightValue = getCharNodeValue(nextBlock.getRightBlock());

        if (isHorizontal(currentValue) && isLeft(nextValue)) {
            saveCorner(nextBlock);
            return nextBlock.getUpBlock();
        }
        if (isHorizontal(currentValue) && isRight(nextValue)) {
            saveCorner(nextBlock);
            return nextBlock.getDownBlock();
        }
        if (isNumeric(currentValue) || isVertical(currentValue) && isLeft(nextValue)) {
            saveCorner(nextBlock);
            return nextBlock.getLeftBlock();
        }
        if (isNumeric(currentValue) || isVertical(currentValue) && isRight(nextValue)) {
            saveCorner(nextBlock);
            return nextBlock.getRightBlock();
        }
        if (isContinueDown(nextValue) && !isRight(nextRightValue) && !isLeft(nextLeftValue)) {
            return nextBlock.getDownBlock();
        }

        if (isRight(currentValue)) {
            if (isVertical(nextValue) && !isVertical(nextDownValue)) {
                return nextBlock.getRightBlock().getRightBlock();
            } else if (isHorizontal(nextValue)) {
                return nextBlock.getRightBlock();
            }
        }
        return null;
    }

    private static String getCharNodeValue(Node current) {
        return String.valueOf(current.getValue());
    }

    private static boolean isNumeric(String currentValue) {
        return StringUtils.isNumeric(currentValue);
    }

    private void saveCorner(final Node corner) {
        pathMap.addCorner(corner);
    }

    private boolean isContinueDown(final String nextValue) {
        return isNumeric(nextValue) || isVertical(nextValue);
    }

    private static boolean isVertical(final String value) {
        return PathBlocksEnum.VERTICAL_WAY.getValue().equals(value);
    }

    private static boolean isRight(final String value) {
        return PathBlocksEnum.TURN_RIGHT_WAY.getValue().equals(value);
    }

    private static boolean isLeft(final String value) {
        return PathBlocksEnum.TURN_LEFT_WAY.getValue().equals(value);
    }

    private static boolean isHorizontal(final String value) {
        return PathBlocksEnum.HORIZONTAL_WAY.getValue().equals(value);
    }

    private int checkIsBillOnAPath(final char element) {
        String value = String.valueOf(element);
        return isNumeric(value) ? Integer.parseInt(value) : -1;
    }

    private boolean isPathBlock(final char element) {
        final String value = String.valueOf(element);
        if (isHorizontal(value)) {
            return true;
        }
        if (isLeft(value)) {
            return true;
        }
        if (isRight(value)) {
            return true;
        }
        if (isVertical(value)) {
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
            if(isHorizontal(String.valueOf(node[0].getValue()))) {
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
