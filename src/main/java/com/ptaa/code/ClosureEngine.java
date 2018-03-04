package com.ptaa.code;

import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.GoogleCodingConvention;

/**
 * This class is intended for Google Closure compiler.
 */
public class ClosureEngine {

    /**
     * Initialized the Google closure engine
     * Focus is on ECMASCRIPT_2015
     *
     * @return Compiler object to parse the js files.
     */
    public static Compiler init() {

        Compiler compiler = new Compiler();
        CompilerOptions options = new CompilerOptions();
        options.setLanguage(CompilerOptions.LanguageMode.ECMASCRIPT_2015);
        options.setCodingConvention(new GoogleCodingConvention());
        compiler.initOptions(options);

        return compiler;
    }
}
