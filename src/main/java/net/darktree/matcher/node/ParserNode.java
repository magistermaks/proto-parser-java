package net.darktree.matcher.node;

import net.darktree.matcher.context.MatcherContext;
import net.darktree.matcher.token.Match;
import net.darktree.parser.ParseResult;
import net.darktree.parser.TokenParser;
import net.darktree.tokenizer.Token;

import java.util.List;

public class ParserNode implements Node {

	private final TokenParser parser;

	public ParserNode(TokenParser parser) {
		this.parser = parser;
	}

	@Override
	public Match match(List<Token> tokens, int start, int index, int end, MatcherContext context) {
		return Match.optional();
	}

	@Override
	public ParseResult<Object> apply(List<Token> tokens, int start, int index, int end, Match match, Node parent, MatcherContext context) {
		return parser.call(tokens, start, index, context);
	}

	@Override
	public void add(Node node) {
		throw new UnsupportedOperationException();
	}

}
