package net.darktree.matcher.token;

import net.darktree.matcher.context.MatcherContext;
import net.darktree.matcher.token.predicate.TokenPredicate;
import net.darktree.tokenizer.Token;

import java.util.List;

public class LiteralTokenMatcher implements TokenMatcher {

	private final TokenPredicate predicate;
	private final boolean optional;

	public LiteralTokenMatcher(TokenPredicate predicate, boolean optional) {
		this.predicate = predicate;
		this.optional = optional;
	}

	@Override
	public Match match(List<Token> tokens, int index, int end, MatcherContext context) {
		return Match.singular(predicate.match(tokens.get(index)), optional);
	}

	@Override
	public String toString() {
		return predicate.toString();
	}

}
