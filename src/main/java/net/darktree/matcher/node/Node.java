package net.darktree.matcher.node;

import net.darktree.matcher.context.MatcherContext;
import net.darktree.matcher.token.Match;
import net.darktree.parser.ParseResult;
import net.darktree.tokenizer.Token;

import java.util.List;

public interface Node {

	Match match(List<Token> tokens, int start, int index, int end, MatcherContext context);

	ParseResult<Object> apply(List<Token> tokens, int start, int index, int end, Match match, Node node, MatcherContext context);

	default Match commit(List<Token> tokens, int index, int end, MatcherContext context, Match match) {
		return match;
	}

	void add(Node node);

	default ParseResult<Object> parse(List<Token> tokens, int start) {
		int end = tokens.size();

		MatcherContext context = new MatcherContext();
		Match match = match(tokens, start, start, end, context);

		if (match.matched) {
			return apply(tokens, start, start, end, match, null, context);
		}

		return ParseResult.range(null, start, start);
	}

	default String getExpected(boolean commit) {
		return toString();
	}

}
