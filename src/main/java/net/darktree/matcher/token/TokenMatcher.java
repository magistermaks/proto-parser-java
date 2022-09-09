package net.darktree.matcher.token;

import net.darktree.matcher.context.MatcherContext;
import net.darktree.matcher.token.match.Match;
import net.darktree.matcher.token.match.MatchStage;
import net.darktree.tokenizer.Token;

import java.util.List;

public abstract class TokenMatcher {

	abstract public Match match(List<Token> tokens, int start, int end, MatcherContext context);
	abstract public String getExpected(MatchStage stage);

	public Match commit(List<Token> tokens, int index, int end, MatcherContext context, Match match) {
		return match;
	}

}
