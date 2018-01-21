package com.ptaa.code;

import com.google.javascript.jscomp.*;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.rhino.Node;
import com.google.javascript.rhino.Token;
import com.sun.org.apache.bcel.internal.generic.BranchInstruction;

import java.util.Iterator;

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

    public static boolean hasFunction(String fileName) {

        sourceFile = SourceFile.fromFile(fileName);
        if (compiler == null) {
            compiler = init();
        }
        Node node = compiler.parse(sourceFile).removeFirstChild();
        Node script = new Node(Token.SCRIPT, node);
        return script.getFirstChild().isFunction();
    }

    public static boolean hasForLoop(String fileName) {

        if (hasFunction(fileName)) {
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
                            case FOR:
                                String forVar1 = child.getSecondChild().getSecondChild().getQualifiedName();
                                decideVulnerable(function, paramList, functionName, child, forVar1);

                                String forVar2 = child.getFirstChild().getFirstChild().getFirstChild().getQualifiedName();
                                decideVulnerable(function, paramList, functionName, child, forVar2);


                            case WHILE:
                                //case 1 : max used in while loop

                                String whileVar = child.getFirstChild().getQualifiedName();
                                decideVulnerable(function, paramList, functionName, child, whileVar);

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
                                String loopParamName = child.getSecondChild().getQualifiedName();
                                decideVulnerable(function, paramList, functionName, child, loopParamName);
                        }
                    }
                }

            }

        }

        return true;
    }

    private static void decideVulnerable(Node function, Node paramList, String functionName, Node child, String loopParamName) {
        for (Node param : paramList.children()) {
            if (param.getQualifiedName().equals(loopParamName)) {
                System.out.println("possible vulnerable at file: " + function.getSourceFileName() + " function: " + functionName + " lineNo: " + child.getLineno());
            }
        }
    }
}
