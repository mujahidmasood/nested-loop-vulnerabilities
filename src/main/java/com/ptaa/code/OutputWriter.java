package com.ptaa.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

public class OutputWriter {

    public static void writeOutput(String inputFile, String output) throws Exception {
        String file =  inputFile +"_output.txt";
        Path path = Paths.get(file);
        Files.createDirectories(path.getParent());
        if(!Files.exists(path)) {
            Files.createFile(path);
        }


        Files.write(path, output.getBytes(), StandardOpenOption.APPEND);

    }

    public static void write(String fileName, Map<Integer,String> vulnerabilitesMap) throws Exception {
        String file_out = fileName + "\n";
        file_out += "------------------------------------------------\n";
        OutputWriter.writeOutput(fileName, file_out);
        for (String output : vulnerabilitesMap.values()) {
            OutputWriter.writeOutput(fileName, output);
        }
    }
}
