package com.ptta.code;


import com.ptaa.code.Main;
import org.junit.Test;

public class MainTest {

    //String file = "src/test/resources/useragent.js";

    //TODO for each in parseheader.js does not work
    //String file = "src/test/resources/parseheader.js";

    //identifes 2 case of array.length
    //String file = "src/test/resources/clientsessions.js";

    //TODO check does not identify anything
    //String file = "src/test/resources/glmatrix.js";

    //TODO does not identify anything
    //String file = "src/test/resources/httpheaders.js";

    //
    String file = "src/test/resources/react-metrics-graphics.js";
    @Test
    public void hasForLoop() throws Exception{
        Main.readScriptFile(file);
    }
}
