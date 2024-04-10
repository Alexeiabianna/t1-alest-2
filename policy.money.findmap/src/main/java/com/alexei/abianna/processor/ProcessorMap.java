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

            while (isPathBlock(nextBlock)) {
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

        while(sameRow) {
            if (!isRight(next) || !isLeft(next)) {
                return next.getRightBlock();
            }
            if (isRight(next)) {
                return next.getDownBlock();
            }
            if (isLeft(next)) {
                return next.getUpBlock();
            }
        }

        if (sameCol || sameRow) {
            final int size = pathMap.getCornerList().size();
            final Node corner = size > 0 ? pathMap.getCornerList().get(size - 1) : null;

            //Change direction
            if (isHorizontal(current) && isRight(next) && sameRow) {
                return next.getDownBlock();
            }
            if (isVertical(current) && isRight(next) && sameCol) {
                return next.getRightBlock();
            }
            if (isVertical(current) && isVertical(next) && isRight(next.getLeftBlock())) {
                return next.getLeftBlock();
            }

            if (isHorizontal(current) && isLeft(next)) {
                if (isVertical(next.getLeftBlock()) || isHorizontal(next.getLeftBlock())) {
                    return next.getLeftBlock();
                }
                return next.getUpBlock();
            }
            if (isVertical(current) && isLeft(next) && sameRow) {
                return next.getUpBlock();
            }
            if (isVertical(current) && isLeft(next) && sameCol) {
                return next.getLeftBlock();
            }

            if(Objects.nonNull(corner)) {

                //Down
                if (isRight(corner) && isVertical(corner.getDownBlock())) {
                    return next.getDownBlock();
                }
                //Right
                if (isRight(corner) && !isVertical(corner.getDownBlock()) && !isHorizontal(corner.getDownBlock())) {
                    return next.getRightBlock();
                }
                //Up
                if (isLeft(corner) && isVertical(corner.getUpBlock())) {
                    return next.getUpBlock();
                }
                //Left
                if (isLeft(corner) && !isVertical(corner.getUpBlock())) {
                    return next.getLeftBlock();
                }
                //Up Left
                if (isRight(corner) && !isVertical(corner.getUpBlock())
                        && isVertical(corner.getDownBlock()) || isHorizontal(corner.getDownBlock()) || isNumeric(corner.getDownBlock())) {
                    if (corner.getRow() == next.getRow()) {
                        return next.getLeftBlock();
                    }
                }
                //Left to Up
                if (isRight(next) && isVertical(corner.getDownBlock())) {
                    return next.getUpBlock();
                }
            }

        }
        return null;
    }

    private boolean isCorner(Node node) {
        return isLeft(node) || isRight(node);
    }

    private static String getCharNodeValue(Node current) {
        return String.valueOf(current.getValue());
    }

    private static boolean isNumeric(final Node currentValue) {
        return StringUtils.isNumeric(getCharNodeValue(currentValue));
    }

    private void saveCorner(final Node corner) {
        pathMap.addCorner(corner);
    }

    private static boolean isHorizontal(final Node value) {
        return PathBlocksEnum.HORIZONTAL_WAY.getValue().equals(getCharNodeValue(value));
    }

    private static boolean isVertical(final Node value) {
        return PathBlocksEnum.VERTICAL_WAY.getValue().equals(getCharNodeValue(value));
    }

    private static boolean isRight(final Node value) {
        return PathBlocksEnum.TURN_RIGHT_WAY.getValue().equals(getCharNodeValue(value));
    }

    private static boolean isLeft(final Node value) {
        return PathBlocksEnum.TURN_LEFT_WAY.getValue().equals(getCharNodeValue(value));
    }

    private static boolean isEnd(final Node value) {
        return PathBlocksEnum.FINAL_PATH.getValue().equals(getCharNodeValue(value));
    }

    private int checkIsBillOnAPath(final Node element) {
        String value = getCharNodeValue(element);
        return isNumeric(element) ? Integer.parseInt(value) : -1;
    }

    private boolean isPathBlock(final Node element) {
        if (isHorizontal(element)) {
            return true;
        }
        if (isLeft(element)) {
            return true;
        }
        if (isRight(element)) {
            return true;
        }
        if (isVertical(element)) {
            return true;
        } else {
            if (checkIsBillOnAPath(element) != -1) {
                pathMap.addBills(Integer.parseInt(getCharNodeValue(element)));
                return true;
            }
            return false;
        }
    }

    private Node getFirstBlock(final PathGraph pathGraph) {
        for(Node[] node : pathGraph.getGrid()) {
            if(isHorizontal(node[0])) {
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
