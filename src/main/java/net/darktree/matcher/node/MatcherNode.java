package net.darktree.matcher.node;

import net.darktree.error.MessageConsumer;
import net.darktree.matcher.context.MatcherContext;
import net.darktree.matcher.token.Match;
import net.darktree.matcher.token.TokenMatcher;
import net.darktree.tokenizer.Token;

import java.util.List;

public class MatcherNode extends ParentalNode {

	private final TokenMatcher matcher;

	public MatcherNode(TokenMatcher matcher, MessageConsumer sink) {
		super(sink);
		this.matcher = matcher;
	}

	@Override
	public Match match(List<Token> tokens, int start, int index, int end, MatcherContext context) {
		return matcher.match(tokens, index, end, context);
	}

	@Override
	public Match commit(List<Token> tokens, int index, int end, MatcherContext context, Match match) {
		return matcher.commit(tokens, index, end, context, match);
	}

	@Override
	public String getExpected(boolean commit) {
		return commit ? matcher.toString() : matcher.getCommitString();
	}

}
