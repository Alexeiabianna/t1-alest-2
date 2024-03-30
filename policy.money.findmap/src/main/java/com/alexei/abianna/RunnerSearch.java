package com.alexei.abianna;

import com.alexei.abianna.domain.PathMap;
import com.alexei.abianna.processor.ProcessorMap;
import com.alexei.abianna.reader.ReaderMapUtils;

public class RunnerSearch {

    private static final String CASE_50 = "src/main/resources/casosdeteste/casoC50.txt";
    private static final String CASE_100 = "src/main/resources/casosdeteste/casoC100.txt";
    private static final String CASE_200 = "src/main/resources/casosdeteste/casoC200.txt";
    private static final String CASE_500 = "src/main/resources/casosdeteste/casoC500.txt";
    private static final String CASE_750 = "src/main/resources/casosdeteste/casoC750.txt";
    private static final String CASE_1000 = "src/main/resources/casosdeteste/casoC1000.txt";
    private static final String CASE_1500 = "src/main/resources/casosdeteste/casoC1500.txt";
    private static final String CASE_2000 = "src/main/resources/casosdeteste/casoC2000.txt";

    public static void main(String[] args) {
        run(CASE_50);
//        run(CASE_100);
//        run(CASE_200);
//        run(CASE_500);
//        run(CASE_750);
//        run(CASE_1000);
//        run(CASE_1500);
//        run(CASE_2000);
    }

    private static void run(final String filePath) {
        var text = ReaderMapUtils.getList(filePath);
        final ProcessorMap processor = new ProcessorMap(new PathMap());
        processor.loadingMap(text);
        processor.process();
    }
}