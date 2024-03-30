package com.alexei.abianna.processor;

import com.alexei.abianna.domain.PathGraph;
import com.alexei.abianna.domain.PathMap;

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
        //TODO insert method that process a search for bills

        Logger.getAnonymousLogger().info("Amount bills result: " + pathMap.getAmountBills());
    }

    public PathGraph getPathGraph() {
        return this.pathGraph;
    }

}
