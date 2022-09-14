package net.darktree.parser;

import net.darktree.matcher.context.MatcherContext;
import net.darktree.tokenizer.Token;

import java.util.List;

@FunctionalInterface
public interface TokenParser {

	/**
	 * Dummy parser for when a token sequence should be
	 * completely ignored by the matcher.
	 */
	static ParseResult dummy(List<Token> tokens, int start, int end, MatcherContext context) {
		return ParseResult.range(null, start, end);
	}

	ParseResult call(List<Token> tokens, int start, int end, MatcherContext context);

}
