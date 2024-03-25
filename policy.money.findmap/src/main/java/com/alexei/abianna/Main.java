package com.alexei.abianna;

import com.alexei.abianna.domain.MapPath;
import com.alexei.abianna.processor.ProcessorMapReaded;
import com.alexei.abianna.reader.ReaderMapUtils;

public class Main {
    public static void main(String[] args) {
//        var text = ReaderMapUtils.readTextFile("src/main/resources/casosdeteste/casoC01.txt");
        var text50 = ReaderMapUtils.readTextFile("src/main/resources/casosdeteste/casoC50.txt");

        final MapPath mapPath = new MapPath();
        final ProcessorMapReaded processor = new ProcessorMapReaded();
        processor.orderFileLines(text50, processor.getSizeFile(text50));

        System.out.println(text50);
    }
}