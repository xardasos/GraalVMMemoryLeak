package com.example;

import com.oracle.truffle.js.scriptengine.GraalJSScriptEngine;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.HostAccess;

public class Main {

    private static final String SCRIPT_BODY =
            "var list = new java.util.ArrayList();\n" +
                    "list.add(\"aaaaa\");";
    //private static final HostAccess HOST_ACCESS = HostAccess.newBuilder().allowPublicAccess(true).build();

    public static void main(String[] args) throws Exception {

        for (int i = 1; i < 1000000; i++) {
            System.out.println(i);

            Engine polyglotEngine = Engine.newBuilder().build();
            GraalJSScriptEngine engine = GraalJSScriptEngine.create(polyglotEngine, getContextBuilder());

            engine.eval(SCRIPT_BODY);

            engine.close();
            polyglotEngine.close();
        }
    }


    private static Context.Builder getContextBuilder() {
        return Context.newBuilder()
                .allowHostAccess(HostAccess.newBuilder().allowPublicAccess(true).build())
                //reusing HostAccess instead fixes the memory leak
                //.allowHostAccess(HOST_ACCESS)
                .allowHostClassLookup(x -> true);
    }

}
