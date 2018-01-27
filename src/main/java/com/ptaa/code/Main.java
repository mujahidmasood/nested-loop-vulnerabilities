package com.ptaa.code;

import com.google.javascript.jscomp.*;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.rhino.Node;
import com.google.javascript.rhino.Token;
import com.sun.org.apache.bcel.internal.generic.BranchInstruction;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {

    private static Compiler compiler;
    private static CompilerOptions options;
    private static SourceFile sourceFile;


    public static Compiler init() {
        compiler = new Compiler();
        options = new CompilerOptions();
        options.setLanguage(CompilerOptions.LanguageMode.ECMASCRIPT_2015);
        options.setCodingConvention(new GoogleCodingConvention());
        compiler.initOptions(options);
        return compiler;
    }

    public static boolean isFunction(String fileName) {

        sourceFile = SourceFile.fromFile(fileName);
        if (compiler == null) {
            compiler = init();
        }
        Node node = compiler.parse(sourceFile).removeFirstChild();
        Node script = new Node(Token.SCRIPT, node);
        return script.getFirstChild().isFunction();
    }

    public static void checkVulnerable(String fileName) throws Exception {

        if (isFunction(fileName)) {
            Node node = compiler.parse(sourceFile);
            Iterable iterable = node.children();
            Iterator iterator = iterable.iterator();
            while (iterator.hasNext()) {
                Node function = (Node) iterator.next();
                Node paramList = function.getSecondChild();
                String functionName = function.getFirstChild().getQualifiedName();

                for (Node node1 : function.children()) {
                    Iterator iter = node1.children().iterator();
                    while (iter.hasNext()) {
                        Node child = (Node) iter.next();

                        switch (child.getToken()) {
                            case VAR:
                                if(child.getFirstChild().getFirstChild() != null){
                                    String varName = child.getFirstChild().getFirstChild().getQualifiedName();
                                    decideVulnerable(function,paramList,functionName,child,varName);
                                }


                            case FOR:
                                if(child != null){
                                    if(child.getSecondChild() != null && child.getSecondChild().getSecondChild() != null){
                                        String forVar1 = child.getSecondChild().getSecondChild().getQualifiedName();
                                        decideVulnerable(function, paramList, functionName, child, forVar1);
                                    }

                                    if(child.getFirstChild() != null && child.getFirstChild().getFirstChild().getFirstChild() != null){
                                        String forVar2 = child.getFirstChild().getFirstChild().getFirstChild().getQualifiedName();
                                        decideVulnerable(function, paramList, functionName, child, forVar2);
                                    }
                                }


                            case WHILE:
                                //case 1 : max used in while loop

                                if( child.getFirstChild() != null){
                                    String whileVar = child.getFirstChild().getQualifiedName();
                                    decideVulnerable(function, paramList, functionName, child, whileVar);
                                }

                                /*switch (child.getFirstChild().getToken()){
                                    case LT:
                                        System.out.println("LT");
                                    case GT:
                                        System.out.println("GT");
                                    case AND:
                                    case OR:
                                        System.out.println("OR");
                                    case EQUALS:
                                    case LE:
                                    case GE:
                                    default:
                                }*/

                                //System.out.println(child.getFirstChild().getFirstChild().getFirstChild().getQualifiedName());
                                //System.out.println(child.getFirstChild().getSecondChild().getFirstChild().getQualifiedName());


                                //case 2 : max used with condition e.g while(max > 10) or while(max < 10)
                                //case 3 : param used in block

                            case FOR_IN:
                                //case 4 : For in loop possible vulnerability
                                if(child.getSecondChild() != null){
                                    String loopParamName = child.getSecondChild().getQualifiedName();
                                    decideVulnerable(function, paramList, functionName, child, loopParamName);
                                }

                        }
                    }
                }


            }

        }
    }


    private static void decideVulnerable(Node function, Node paramList, String functionName, Node child, String loopParamName) throws Exception {


        Path path = Paths.get("src/test/resources/output.txt");

        String file = function.getSourceFileName();
        String description = "at file: ";
        String lineNo = " lineNo: " + child.getLineno();
        String func = " function: " + functionName;
        String output = "";

        for (Node param : paramList.children()) {
            if (param.getQualifiedName().equals(loopParamName)) {
                output = description + file + func + lineNo + "\n";
                System.out.println(output);
                Files.write(path, output.getBytes(), StandardOpenOption.APPEND);
            }
        }

    }


    public static void main(String[] args) throws Exception {
        checkVulnerable("src/test/resources/index.js");
    }
}
