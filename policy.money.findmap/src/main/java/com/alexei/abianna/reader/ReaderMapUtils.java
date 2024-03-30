package com.alexei.abianna.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ReaderMapUtils {

    public static List<String[]> getList(final String filePath) {
        final List<String[]> fileLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                var array = line.split("");
                fileLines.add(array);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileLines;
    }

    public static String[] readText(final String filePath) {
        final List<String> fileLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileLines.toArray(String[]::new);
    }

}
