package com.ptta.code;


import com.ptaa.code.Main;
import org.junit.Test;

public class MainTest {

    //418 and 419 correct
    String file = "src/test/resources/index.js";

    //identifies but many are wrong
    //String file = "src/test/resources/parseheader.js";

    //identifes 2 case of array.length
    //String file = "src/test/resources/clientsessions.js";

    //TODO check does not identify anything
    //String file = "src/test/resources/glmatrix.js";

    //TODO Identifies wrong ones
    //String file = "src/test/resources/httpheaders.js";


    //TODO does not identify anything
    //String file = "src/test/resources/react-metrics-graphics.js";
    @Test
    public void testVulnerabilities() throws Exception{
        Main.readScriptFile(file);
    }
}
