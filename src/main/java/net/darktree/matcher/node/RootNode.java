package net.darktree.matcher.node;

import net.darktree.matcher.context.MatcherContext;
import net.darktree.matcher.token.match.Match;
import net.darktree.tokenizer.Token;

import java.util.List;

public class RootNode extends ParentalNode {

	@Override
	public Match match(List<Token> tokens, int start, int index, int end, MatcherContext context) {
		return Match.optional();
	}

	public void onSectionMatched(int start, int end, MatcherContext context) {
		// ignore matching for root node
	}

}
