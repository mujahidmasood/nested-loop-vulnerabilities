package com.ptaa.code;

import com.google.javascript.jscomp.*;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.rhino.Node;

import java.util.*;

public class StaticAnalyzer {

    private static Map<String, String> assignedVars;
    private static Node function = null;
    private static List<String> params;
    private static Map<Integer, String> vulnerabilitiesMap;

    /**
     * Constructor initializes data structure to be used.
     */
    public StaticAnalyzer() {
        assignedVars = new HashMap<>();
        params = new ArrayList<>();
        vulnerabilitiesMap = new TreeMap<>();
    }


    /**
     * Reads the given script files
     *  and iterates over the children
     *  Finally writes the output by iterating vulnerabilitiesMap
     * @param fileName
     * @throws Exception
     */
    public static void readScriptFile(String fileName) throws Exception {

        SourceFile sourceFile = SourceFile.fromFile(fileName);
        Compiler compiler = ClosureEngine.init();

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

            OutputWriter.writeFormattedOutpu(fileName, vulnerabilitiesMap);
            vulnerabilitiesMap.clear();
            assignedVars.clear();
            params.clear();
            function = null;
        }
    }

    /**
     * iterates the child
     * if child is parameter add it in parameters list
     * if child is function save the reference in function variable
     * in other case check for different cases.
     *
     * @param child
     * @throws Exception
     */
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


    /**
     * Checks if the input node is present in parameters
     *  if yes node is written in vulnerabilities map
     *
     * @param child
     * @param varName
     * @throws Exception
     */
    private static void decideVulnerable(Node child, String varName) throws Exception {

        String functionName = "()";
        //Get the function name
        if (function != null && function.getSecondChild() != null) {
            functionName = function.getFirstChild().getQualifiedName();
        }

        //Get the line no of child node
        int lineNo = child.getLineno();
        String func = functionName;

        //check if the variable is present in parameters list
        String mappedVarValue = assignedVars.get(varName);
        if (params.contains(mappedVarValue) || params.contains(varName)) {
            String formatted_output = String.format("%-50s %s", func, lineNo + "\n");
            vulnerabilitiesMap.putIfAbsent(lineNo, formatted_output);
        }
    }

    /**
     * Check if the parent of node is loop
     *
     * @param node
     * @return
     */
    private static boolean checkLoop(Node node) {
        Node child = node.getParent();
        return child.isForIn() || child.isForOf() || child.isWhile() || child.isVanillaFor();
    }
}
