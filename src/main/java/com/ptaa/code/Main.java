package com.ptaa.code;

import com.google.javascript.jscomp.*;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.rhino.Node;

import java.nio.file.*;
import java.util.*;

//TODO test on node module(npm userAgent, httpHeaderParsing npm module).
//TODO Identify anonymous functions
//TODO reduce number of wrongly identified vulnerabilities
//TODO array.length or string.length used in loop
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

        Node node = compiler.parse(sourceFile);
        Iterable iterable = node.children();
        Iterator iterator = iterable.iterator();
        while (iterator.hasNext()) {
            function = (Node) iterator.next();

            Node paramList = function.getSecondChild();
            params = new ArrayList<>();

            if (paramList != null && paramList.children() != null) {
                for (Node param : paramList.children()) {
                    params.add(param.getQualifiedName());
                }
            }

            for (Node node1 : function.children()) {
                Iterator iter = node1.children().iterator();
                while (iter.hasNext()) {
                    Node child = (Node) iter.next();
                    if (child != null) {
                        iterateScript(child);
                    }
                }
            }
        }
    }

    public static void iterateScript(Node child) throws Exception {
        System.out.println(child);
        if (child.getToken() != null) {
            switch (child.getToken()) {
                case BLOCK:
                    if (child.children() != null) {
                        for (Node block : child.children()) {
                            if (block != null) {
                                iterateScript(block);
                                if (block.getFirstChild() != null && block.getFirstChild().getQualifiedName() != null) {
                                    decideVulnerable(block, block.getFirstChild().getQualifiedName());
                                }
                            }
                        }
                    }
                    break;
                case VAR:
                    for (Node var : child.children()) {
                        if (var.getFirstChild() != null) {
                            iterateScript(var.getFirstChild());
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

                            decideVulnerable(var, mappedVar);
                            decideVulnerable(var, assignedVar);


                        }

                    }

                    break;

                case NAME:
                    if (!child.getParent().isParamList()) {
                        decideVulnerable(child, child.getQualifiedName());
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
                case ASSIGN:
                    if (child != null && child.children() != null) {
                        for (Node geChild : child.children()) {
                            if (geChild != null) {
                                iterateScript(geChild);
                                String leftOp = "";
                                String rightOp = "";

                                if (child.getFirstChild() != null) {
                                    leftOp = child.getFirstChild().getQualifiedName();
                                }

                                if (child.getSecondChild() != null) {
                                    rightOp = child.getSecondChild().getQualifiedName();
                                }

                                assignedVars.put(leftOp, rightOp);
                                decideVulnerable(geChild, leftOp);
                                decideVulnerable(geChild, rightOp);
                            }
                        }
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
                case CALL:
                case WHILE:
                case FOR_OF:
                case FOR:
                    if (child.children() != null) {
                        for (Node node : child.children()) {
                            if (node != null) {
                                iterateScript(node);
                                String name1 = node.getQualifiedName();
                                decideVulnerable(node, name1);

                            }
                        }
                    }
                    break;

                case FOR_IN:
                    for (Node for_in : child.children()) {
                        if (for_in != null) {
                            iterateScript(for_in);
                        }
                        if (child.getSecondChild() != null && child.getSecondChild().getQualifiedName() != null) {
                            String param = child.getSecondChild().getQualifiedName();
                            assignedVars.put(param, param);
                            decideVulnerable(for_in, param);
                        }
                    }
                    break;
                case PARAM_LIST:
                case GETPROP:
                case FUNCTION:
                case RETURN:
                case EXPORT:
                case NUMBER:
                case STRING:
                case CONST:
                case NEW:
                case TRY:
                case ENUM:
                case NULL:
                case PIPE:
                case EXPR_RESULT:
                case THIS:
                case TRUE:
                case FALSE:
                case INSTANCEOF:
                case OBJECTLIT:
                case OBJECT_PATTERN:
                case SCRIPT:
                case UNDEFINED_TYPE:
                case FUNCTION_TYPE:
                case DO:
                case IN:
                case ANY_TYPE:
                case CLASS_MEMBERS:
                case MODULE_BODY:
                case SHEQ:
                case DELPROP:
                case HOOK:
                case SHNE:
                case ARRAYLIT:
                case GETELEM:
                case STRING_KEY:
                case STRING_TYPE:
                case TYPEOF:
                case REGEXP:
                case ANNOTATION:
                case BANG:
                case ROOT:
                case BREAK:
                case DEBUGGER:
                case ELLIPSIS:
                case EOC:
                case EQUALS:
                case LB:
                case LC:
                case STAR:
                case TEMPLATELIT:
                case YIELD:
                case EMPTY:
                case IMPORT_STAR:
                case LABEL_NAME:
                case MEMBER_VARIABLE_DEF:
                case BITNOT:
                case CALL_SIGNATURE:
                case CAST:
                case GETTER_DEF:
                case INDEX_SIGNATURE:
                case MEMBER_FUNCTION_DEF:
                case NAMED_TYPE:
                case NEG:
                case POS:
                case REST:
                case SETTER_DEF:
                case SPREAD:
                case TEMPLATELIT_SUB:
                case THROW:
                case TYPE_ALIAS:
                case VOID:
                case ASSIGN_ADD:
                case ASSIGN_BITAND:
                case ASSIGN_BITOR:
                case ASSIGN_BITXOR:
                case ASSIGN_DIV:
                case ASSIGN_LSH:
                case ASSIGN_MOD:
                case ASSIGN_MUL:
                case ASSIGN_EXPONENT:
                case ASSIGN_RSH:
                case ASSIGN_SUB:
                case ASSIGN_URSH:
                case BITAND:
                case BITOR:
                case CASE:
                case CATCH:
                case COMPUTED_PROP:
                case DEFAULT_VALUE:
                case EXPONENT:
                case LABEL:
                case LSH:
                case NAMESPACE:
                case RSH:
                case TAGGED_TEMPLATELIT:
                case URSH:
                case WITH:
                case CLASS:
                case IMPORT:
                case INTERFACE:
                    for (Node node : child.children()) {
                        if (node != null) {
                            iterateScript(node);
                        }
                        decideVulnerable(node, node.getQualifiedName());
                    }
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


    public static void writeOutput(String output) throws Exception {
        Path path = Paths.get("src/test/resources/output.txt");
        Files.write(path, output.getBytes(), StandardOpenOption.APPEND);
    }

    public static void main(String[] args) {

        String file = "src/test/resources/agent.js";

        //identifies but many are wrong
        //String file = "src/test/resources/parseheader.js";

        //identifes 2 case of array.length
        //String file = "src/test/resources/clientsessions.js";

        //String file = "src/test/resources/glmatrix.js";

        //String file = "src/test/resources/httpheaders.js";


        //String file = "src/test/resources/react-metrics-graphics.js";

        //String file = "src/test/resources/dash.js";

        //418 and 419 correct
        //String file = "src/test/resources/agent.js";

        try{
            readScriptFile(file);
        }catch (Exception e){
            System.out.println(e.getCause());
            e.printStackTrace();
        }


    }
}
