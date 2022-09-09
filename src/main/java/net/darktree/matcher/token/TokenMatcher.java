package net.darktree.matcher.token;

import net.darktree.matcher.context.MatcherContext;
import net.darktree.tokenizer.Token;

import java.util.List;

public interface TokenMatcher {

	Match match(List<Token> tokens, int start, int end, MatcherContext context);

	default Match commit(List<Token> tokens, int index, int end, MatcherContext context, Match match) {
		return match;
	}

	default String getCommitString() {
		return toString();
	}

}
