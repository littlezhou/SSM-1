package org.apache.hadoop.ssm.antlr;// Generated from /home/hadoop/github/antlr/src/main/java/Demo.g4 by ANTLR 4.6
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DemoParser}.
 */
public interface DemoListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DemoParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(DemoParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link DemoParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(DemoParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link DemoParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(DemoParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link DemoParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(DemoParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by {@link DemoParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(DemoParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link DemoParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(DemoParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link DemoParser#multExpr}.
	 * @param ctx the parse tree
	 */
	void enterMultExpr(DemoParser.MultExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link DemoParser#multExpr}.
	 * @param ctx the parse tree
	 */
	void exitMultExpr(DemoParser.MultExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link DemoParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(DemoParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link DemoParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(DemoParser.AtomContext ctx);
}