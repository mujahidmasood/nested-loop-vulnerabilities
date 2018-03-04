package com.ptaa.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

public class OutputWriter {

    /**
     * Create output file to write vulnerabilities
     * Output file name is with of format inputFile_output.txt
     * The output directory is specified in inputFile
     * Writes the output to file
     *
     * @param inputFile
     * @param output
     * @throws Exception
     */
    public static void createOuputFile(String inputFile, String output) throws Exception {
        String file =  inputFile +"_output.txt";
        Path path = Paths.get(file);
        Files.createDirectories(path.getParent());
        if(!Files.exists(path)) {
            Files.createFile(path);
        }


        Files.write(path, output.getBytes(), StandardOpenOption.APPEND);

    }

    /**
     * Writes formatted output
     * First line is name of file for which output is written
     *
     * Output is written with function name and line number.
     *
     * @param fileName
     * @param vulnerabilitesMap
     * @throws Exception
     */
    public static void writeFormattedOutpu(String fileName, Map<Integer,String> vulnerabilitesMap) throws Exception {
        String file_out = fileName + "\n";
        file_out += "------------------------------------------------\n";
        createOuputFile(fileName, file_out);
        for (String output : vulnerabilitesMap.values()) {
            createOuputFile(fileName, output);
        }
    }
}
