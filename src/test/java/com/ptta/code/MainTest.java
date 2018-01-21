package com.ptta.code;


import com.ptaa.code.Main;
import org.junit.Test;

public class MainTest {

    String file = "src/test/resources/index.js";

    @Test
    public void hasForLoop(){
        boolean hasForLoop = Main.hasForLoop(file);
        assert (hasForLoop == true);
    }
}
