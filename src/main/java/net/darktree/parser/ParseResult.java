package net.darktree.parser;

public class ParseResult {

	public final Object result;
	public final int tokens;

	public ParseResult(Object result, int tokens) {
		this.result = result;
		this.tokens = tokens;
	}

	public static ParseResult range(Object result, int start, int end) {
		return new ParseResult(result, end - start);
	}

}
