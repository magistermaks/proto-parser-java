package net.darktree.parser;

public class ParseResult<T> {
	public final T result;
	public final int tokens;

	public ParseResult(T result, int tokens) {
		this.result = result;
		this.tokens = tokens;
	}

	public static <T> ParseResult<T> range(T result, int start, int end) {
		return new ParseResult<>(result, end - start);
	}

}
