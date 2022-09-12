package net.darktree.matcher.node;

import net.darktree.matcher.context.MatcherContext;
import net.darktree.matcher.token.match.Match;
import net.darktree.parser.ParseResult;
import net.darktree.tokenizer.Token;

import java.util.List;

public abstract class Node {

	protected abstract Match match(List<Token> tokens, int start, int index, int end, MatcherContext context);

	protected abstract ParseResult apply(List<Token> tokens, int start, int index, int end, Match match, Node node, MatcherContext context);

	protected Match commit(List<Token> tokens, int index, int end, MatcherContext context, Match match) {
		return match;
	}

	public abstract void addChild(Node node);

	public ParseResult parse(List<Token> tokens, int start, int end) {
		MatcherContext context = new MatcherContext();
		Match match = match(tokens, start, start, end, context);

		if (match.matched) {
			return apply(tokens, start, start, end, match, null, context);
		}

		return ParseResult.range(null, start, start);
	}

}
