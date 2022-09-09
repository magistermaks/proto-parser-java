package net.darktree.parser;

import net.darktree.matcher.context.MatcherContext;
import net.darktree.tokenizer.Token;

import java.util.List;

@FunctionalInterface
public interface TokenParser {
	ParseResult<Object> call(List<Token> tokens, int start, int end, MatcherContext context);
}
