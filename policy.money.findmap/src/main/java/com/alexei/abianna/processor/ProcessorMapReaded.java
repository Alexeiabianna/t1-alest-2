package com.alexei.abianna.processor;

import com.alexei.abianna.domain.PathBlocks;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class ProcessorMapReaded {

    private Map<Integer, List<String>> orderedLines;

    public int getSizeFile(final List<String> lines) {
        if (Objects.nonNull(lines)) {
            String[] size = lines.get(0).split(" ");
            return Integer.parseInt(size[0]);
        }
        return 0;
    }

    public void orderFileLines(final List<String> lines, int sizeFile) {
        lines.remove(0);
        this.orderedLines = new HashMap<>(sizeFile);
        List<String> elements = new ArrayList<>(sizeFile);
        int i = 0;

        for(String line : lines) {
            String sequenceCleaned = StringUtils.deleteWhitespace(line);
            String[] s = StringUtils.splitByCharacterType(sequenceCleaned);
            if(s[i].contains(PathBlocks.HORIZONTAL_WAY.name())) {
                elements.add(s[i]);
            }
            i++;
        }

        elements.size();

        //TODO create a method that receive a list of strings and sort them
    }
}
