package net.darktree.matcher.token;

import net.darktree.error.ErrorReportable;
import net.darktree.matcher.context.MatcherContext;
import net.darktree.matcher.token.match.Match;
import net.darktree.tokenizer.Token;

import java.util.List;

public abstract class TokenMatcher implements ErrorReportable {

	/**
	 * The first stage of the matching process.
	 * If this method returns a failed state the parent matcher will try to call another child
	 * (if one exists), otherwise error will be generated.
	 */
	abstract public Match match(List<Token> tokens, int start, int end, MatcherContext context);

	/**
	 * Called for a matcher after ${@link TokenMatcher#match} returned a successful state.
	 * When this method is called the parent is already "committed" to this child,
	 * returning a failed state here will force an error to be generated regardless of existence of other paths.
	 */
	public Match commit(List<Token> tokens, int index, int end, MatcherContext context, Match match) {
		return match;
	}

}
