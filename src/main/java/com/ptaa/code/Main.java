package com.ptaa.code;

import com.google.javascript.jscomp.*;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.rhino.Node;
import com.google.javascript.rhino.Token;

import java.nio.file.*;
import java.util.*;

//TODO test on node module(npm userAgent, httpHeaderParsing npm module).
//TODO identify function, figure out how to run
//TODO function call
//TODO Array argument
//TODO Array for each


public class Main {

    private static Compiler compiler;
    private static CompilerOptions options;
    private static SourceFile sourceFile;
    private static Map<String, String> assignedVars;
    private static Node function = null;
    private static List<String> params = null;


    public static Compiler init() {
        compiler = new Compiler();
        options = new CompilerOptions();
        options.setLanguage(CompilerOptions.LanguageMode.ECMASCRIPT_2015);
        options.setCodingConvention(new GoogleCodingConvention());
        compiler.initOptions(options);
        assignedVars = new HashMap<>();
        return compiler;
    }

    public static void readScriptFile(String fileName) throws Exception {
        sourceFile = SourceFile.fromFile(fileName);
        if (compiler == null) {
            compiler = init();
        }

        Node main = compiler.parse(sourceFile).removeFirstChild();
        Node script = new Node(Token.SCRIPT, main);
        if (script.getFirstChild().isFunction()) {
            Node node = compiler.parse(sourceFile);
            Iterable iterable = node.children();
            Iterator iterator = iterable.iterator();
            while (iterator.hasNext()) {
                function = (Node) iterator.next();
                Node paramList = function.getSecondChild();
                params = new ArrayList<>();
                for (Node param : paramList.children()) {
                    params.add(param.getQualifiedName());
                }

                for (Node node1 : function.children()) {
                    Iterator iter = node1.children().iterator();
                    while (iter.hasNext()) {
                        Node child = (Node) iter.next();
                        System.out.println(child);
                        iterateScript(child);
                    }
                }
            }
        }
    }

    public static void iterateScript(Node child) throws Exception {

        if (child.getToken() != null) {
            switch (child.getToken()) {
                case BLOCK:
                    for (Node block : child.children()) {
                        iterateScript(block);
                        if (block.getFirstChild().getQualifiedName() != null) {
                            decideVulnerable(block, block.getFirstChild().getQualifiedName());
                        }
                    }
                    break;
                case VAR:
                    for (Node var : child.children()) {
                        if(var.getFirstChild() != null){
                            iterateScript(var.getFirstChild());
                        }

                        if(child.getFirstChild() != null){
                            String mappedVar = "";
                            String assignedVar = "";
                            if(getQualifiedName(child.getFirstChild() )!= null){
                                assignedVar = getQualifiedName(child.getFirstChild());
                            }

                            if(child.getFirstChild().getFirstChild() != null){
                                if(getQualifiedName(child.getFirstChild().getFirstChild()) != null){
                                    mappedVar = getQualifiedName(child.getFirstChild().getFirstChild());
                                }
                            }

                            if (assignedVar != null && mappedVar != null) {
                                assignedVars.put(assignedVar, mappedVar);
                            }

                            decideVulnerable(var, assignedVar);
                        }

                    }

                    break;

                case NAME:
                    break;
                case NUMBER:
                    break;
                case IF:
                    break;
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
                case SUB:
                case ASSIGN:
                    for (Node geChild : child.children()) {
                        iterateScript(geChild);
                        String leftOp = "";
                        String rightOp = "";

                        if (child.getFirstChild() != null) {
                            leftOp = getQualifiedName(child.getFirstChild());
                        }
                        if (child.getSecondChild() != null) {
                            rightOp = getQualifiedName(child.getSecondChild());
                        }

                        decideVulnerable(geChild, leftOp);
                        decideVulnerable(geChild, rightOp);

                    }
                    break;
                case DEC:
                case INC:
                case NOT:
                    for (Node incChild : child.children()) {
                        String unaryOp = incChild.getQualifiedName();
                        decideVulnerable(incChild, unaryOp);
                    }
                    break;
                case EXPR_RESULT:
                case CALL:
                case WHILE:
                case FOR:
                    if (child != null) {
                        for (Node node : child.children()) {
                            iterateScript(node);
                        }
                    }
                    break;

                case FOR_IN:
                    for (Node for_in : child.children()) {
                        if(for_in != null){
                            iterateScript(for_in);
                        }
                        if(child.getSecondChild()!= null && child.getSecondChild().getQualifiedName() != null){
                            String param = child.getSecondChild().getQualifiedName();
                            assignedVars.put(param,param);
                            decideVulnerable(for_in, param);
                        }
                    }
                    break;
                default:
                    break;

            }
        }

    }


    private static void decideVulnerable(Node child, String varName) throws Exception {


        String functionName = function.getFirstChild().getQualifiedName();

        String file = function.getSourceFileName();
        String description = "at file: ";
        String lineNo = " lineNo: " + child.getLineno();
        String func = " function: " + functionName;
        String output = "";

        String mappedVarValue = assignedVars.get(varName);

        if (params.contains(mappedVarValue) || params.contains(varName)) {
            output = description + file + func + lineNo + "\n";
            System.out.println(output);
            writeOutput(output);
        }
    }

    public static String getQualifiedName(Node child) {
        return child.getQualifiedName();
    }

    public static void writeOutput(String output) throws Exception {
        Path path = Paths.get("src/test/resources/output.txt");
        Files.write(path, output.getBytes(), StandardOpenOption.APPEND);
    }

    public static void main(String[] args) throws Exception {
        readScriptFile("src/test/resources/index.js");
    }
}
