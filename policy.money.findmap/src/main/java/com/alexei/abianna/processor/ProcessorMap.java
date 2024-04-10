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
        boolean sameCol = currentCol == nextCol;
        boolean sameRow = currentRow == nextRow;


        //Store that last corner
        if (isCorner(current)) {
            saveCorner(current);
        }
        if (isCorner(next)) {
            saveCorner(next);
        }

        if (sameCol || sameRow) {
            final int size = pathMap.getCornerList().size();
            final Node corner = size > 0 ? pathMap.getCornerList().get(size - 1) : null;


            if (isHorizontal(getCharNodeValue(current)) && isRight(getCharNodeValue(next)) && sameRow) {
                return next.getDownBlock();
            }
            if (isVertical(getCharNodeValue(current)) && isRight(getCharNodeValue(next))) {
                return next.getRightBlock();
            }
            if (isHorizontal(getCharNodeValue(current)) && isLeft(getCharNodeValue(next))) {
                if (isVertical(getCharNodeValue(next.getLeftBlock())) || isHorizontal(getCharNodeValue(next.getLeftBlock()))) {
                    return next.getLeftBlock();
                }
                return next.getUpBlock();
            }
            if (isVertical(getCharNodeValue(current)) && isLeft(getCharNodeValue(next)) && sameRow) {
                return next.getUpBlock();
            }
            if (isVertical(getCharNodeValue(current)) && isLeft(getCharNodeValue(next)) && sameCol) {
                return next.getLeftBlock();
            }

            if(Objects.nonNull(corner)) {

                //Down
                if (isRight(getCharNodeValue(corner)) && isVertical(getCharNodeValue(corner.getDownBlock()))) {
                    return next.getDownBlock();
                }
                //Right
                if (isRight(getCharNodeValue(corner)) && !isVertical(getCharNodeValue(corner.getDownBlock()))) {
                    return next.getRightBlock();
                }
                //Up
                if (isLeft(getCharNodeValue(corner)) && isVertical(getCharNodeValue(corner.getUpBlock()))) {
                    return next.getUpBlock();
                }
                //Left
                if (isLeft(getCharNodeValue(corner)) && !isVertical(getCharNodeValue(corner.getUpBlock()))) {
                    return next.getLeftBlock();
                }

            }

        }
        return null;
    }

    private boolean isCorner(Node node) {
        return isLeft(getCharNodeValue(node)) || isRight(getCharNodeValue(node));
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

    private static boolean isHorizontal(final String value) {
        return PathBlocksEnum.HORIZONTAL_WAY.getValue().equals(value);
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

    private static boolean isEnd(final String value) {
        return PathBlocksEnum.FINAL_PATH.getValue().equals(value);
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
