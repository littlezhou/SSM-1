package org.apache.hadoop.ssm;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.hadoop.ssm.antlr.*;
import org.junit.Test;

/**
 * Created by hadoop on 17-3-7.
 */

public class AntlrTest {

  public static void run(String expr) throws Exception {

    ANTLRInputStream in = new ANTLRInputStream(expr);
    DemoLexer lexer = new DemoLexer(in);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    DemoParser parser = new DemoParser(tokens);
    parser.prog();

  }

  @Test
  public void test() throws Exception {

    String[] testStr = {
            "2",
            "a+b+3",
            "(a-b)+3",
            "a+(b*3"
    };

    for (String s : testStr) {
      System.out.println("Input expr:" + s);
      run(s);
    }
  }
}