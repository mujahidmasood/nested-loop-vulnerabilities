package com.ptaa.code;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Client for the application
 */
public class AnalyzerClient {
    public static void main(String[] args) {
        String modules_path = "src/test/resources";
        //String modules_path = "/home/mujahidmasood/Masters/DSS/Semester5/PTAA/nested-loop-vulnerabilities/node_modules";
        try {
            //Walking through given directory
            //identifying non hidden files
            //filter files with js or ts extension
            //read every file to find out vulnerabilities
            Files.walk(Paths.get(modules_path))
                    .filter(Files::isRegularFile)
                    .filter(path -> !Files.isDirectory(path))
                    .filter(file ->
                        file.toString().endsWith(".js") || file.toString().endsWith(".ts")
                    )
                    .forEach(path -> {
                        try {
                            System.out.println(path.toString());
                            StaticAnalyzer analyzer = new StaticAnalyzer();
                            analyzer.readScriptFile(path.toString());
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }
                    });

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
