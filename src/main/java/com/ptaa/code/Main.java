package com.ptaa.code;

import com.google.javascript.jscomp.*;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.rhino.Node;
import java.nio.file.*;
import java.util.*;

public class Main {

    private static Compiler compiler;

    private static Map<String, String> assignedVars;
    private static Node function = null;
    private static List<String> params;
    private static Map<Integer, String> vulnerabilitesMap;


    public static Compiler init() {
        compiler = new Compiler();
        CompilerOptions options = new CompilerOptions();
        options.setLanguage(CompilerOptions.LanguageMode.ECMASCRIPT_2015);
        options.setCodingConvention(new GoogleCodingConvention());
        compiler.initOptions(options);

        assignedVars = new HashMap<>();
        params = new ArrayList<>();
        vulnerabilitesMap = new TreeMap<>();

        return compiler;
    }

    public static void readScriptFile(String fileName) throws Exception {
        SourceFile sourceFile = SourceFile.fromFile(fileName);
        if (compiler == null) {
            compiler = init();
        }

        try {
            Node node = compiler.parse(sourceFile);
            Iterable iterable = node.children();
            Iterator iterator = iterable.iterator();
            while (iterator.hasNext()) {
                Node child = (Node) iterator.next();
                if (child != null) {
                    iterateScript(child);
                }
            }
        } finally {
            String file_out = fileName + "\n";
            file_out += "------------------------------------------------\n";
            writeOutput(fileName, file_out);
            for (String output : vulnerabilitesMap.values()) {
                writeOutput(fileName, output);
            }
            vulnerabilitesMap.clear();
            assignedVars.clear();
            params.clear();
            compiler = null;
            function = null;
        }
    }

    public static void iterateScript(Node child) throws Exception {
        if (child.isParamList()) {
            for (Node param : child.children()) {
                if (param != null) {
                    params.add(param.getQualifiedName());
                }
            }
        }
        if (child.isFunction()) {
            function = child;
        }

        if (child.getToken() != null) {
            switch (child.getToken()) {
                case VAR:
                    for (Node var : child.children()) {
                        if (var != null) {
                            iterateScript(var);
                        }

                        if (child.getFirstChild() != null) {
                            String mappedVar = "";
                            String assignedVar = "";
                            if (child.getFirstChild().getQualifiedName() != null) {
                                assignedVar = child.getFirstChild().getQualifiedName();
                            }

                            if (child.getFirstChild().getFirstChild() != null) {
                                if (child.getFirstChild().getFirstChild().isGetProp()) {
                                    mappedVar = child.getFirstChild().getFirstChild().getFirstChild().getQualifiedName();
                                } else {
                                    if (child.getFirstChild().getFirstChild().getQualifiedName() != null) {
                                        mappedVar = child.getFirstChild().getFirstChild().getQualifiedName();
                                    }
                                }
                            }

                            if (assignedVar != null && mappedVar != null) {
                                assignedVars.put(assignedVar, mappedVar);
                            }

                            if (checkLoop(child)) {
                                decideVulnerable(var, mappedVar);
                                decideVulnerable(var, assignedVar);
                            }

                        }

                    }

                    break;
                case IF:
                case OR:
                case BITXOR:
                case AND:
                case EQ:
                case GT:
                case MOD:
                case LT:
                case GE:
                case LE:
                case NE:
                case DIV:
                case MUL:
                case ADD:
                case SWITCH:
                case CONTINUE:
                case COLON:
                case COMMA:
                case SUB:
                    if (child != null && child.children() != null) {
                        for (Node geChild : child.children()) {
                            if (geChild != null) {
                                String leftOp = "";
                                String rightOp = "";

                                if (child.getFirstChild() != null) {
                                    leftOp = child.getFirstChild().getQualifiedName();
                                }

                                if (child.getSecondChild() != null) {
                                    rightOp = child.getSecondChild().getQualifiedName();
                                }

                                assignedVars.put(leftOp, rightOp);
                                if (checkLoop(child)) {
                                    decideVulnerable(geChild, leftOp);
                                    decideVulnerable(geChild, rightOp);
                                }
                            }
                            iterateScript(geChild);
                        }

                    }

                    break;
                case DEC:
                case INC:
                case NOT:
                    for (Node incChild : child.children()) {
                        String unaryOp = incChild.getQualifiedName();
                        assignedVars.put(unaryOp, unaryOp);
                        if (checkLoop(incChild)) {
                            decideVulnerable(incChild, unaryOp);
                        }
                    }
                    break;
                case NAME:
                    if (!child.getParent().isParamList()) {
                        if (child != null && child.getQualifiedName() != null) {
                            decideVulnerable(child, child.getQualifiedName());
                        }
                    }
                    break;
                default:
                    for (Node node : child.children()) {
                        if (node != null) {
                            iterateScript(node);
                        }
                    }
                    break;

            }
        }

    }


    private static void decideVulnerable(Node child, String varName) throws Exception {

        String functionName = "()";
        if (function != null && function.getSecondChild() != null) {
            functionName = function.getFirstChild().getQualifiedName();
        }

        int lineNo = child.getLineno();
        String func = functionName;

        String mappedVarValue = assignedVars.get(varName);
        if (params.contains(mappedVarValue) || params.contains(varName)) {
            String formatted_output = String.format("%-50s %s", func, lineNo + "\n");
            vulnerabilitesMap.putIfAbsent(lineNo, formatted_output);
        }
    }

    private static boolean checkLoop(Node node) {
        Node child = node.getParent();
        return child.isForIn() || child.isForOf() || child.isWhile() || child.isVanillaFor();
    }


    public static void writeOutput(String inputFile, String output) throws Exception {
        String file = inputFile + "_output.txt";
        Path path = Paths.get(file);
        Files.createDirectories(path.getParent());
        if (!Files.exists(path)) {
            Files.createFile(path);
        }


        Files.write(path, output.getBytes(), StandardOpenOption.APPEND);

    }

    public static void main(String[] args) {

        String modules_path = "/home/mujahidmasood/Masters/DSS/Semester5/PTAA/nested-loop-vulnerabilities/node_modules";
        try {
            Files.walk(Paths.get(modules_path))
                    .filter(Files::isRegularFile)
                    .filter(path -> !Files.isDirectory(path))
                    .filter(file -> file.toString().endsWith(".js"))
                    .forEach(path -> {
                        try {
                            System.out.println(path.toString());
                            readScriptFile(path.toString());
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
