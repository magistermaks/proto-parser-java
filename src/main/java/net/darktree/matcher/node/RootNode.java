package net.darktree.matcher.node;

import net.darktree.error.MessageConsumer;
import net.darktree.matcher.context.MatcherContext;
import net.darktree.matcher.token.Match;
import net.darktree.tokenizer.Token;

import java.util.List;

public class RootNode extends ParentalNode {

	public RootNode(MessageConsumer sink) {
		super(sink);
	}

	@Override
	public Match match(List<Token> tokens, int start, int index, int end, MatcherContext context) {
		return Match.optional();
	}

}