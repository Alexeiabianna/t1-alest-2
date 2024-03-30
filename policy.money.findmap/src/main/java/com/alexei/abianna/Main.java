package com.alexei.abianna;

import com.alexei.abianna.domain.PathMap;
import com.alexei.abianna.processor.ProcessorMap;
import com.alexei.abianna.reader.ReaderMapUtils;

public class Main {
    public static void main(String[] args) {
        var text = ReaderMapUtils.getList("src/main/resources/casosdeteste/casoC50.txt");

        final PathMap pathMap = new PathMap();
        final ProcessorMap processor = new ProcessorMap();
        processor.loadingMap(text);

        System.out.println(processor.getPathGraph());
    }
}