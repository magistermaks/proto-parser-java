package net.darktree.matcher.token;

import net.darktree.matcher.context.MatcherContext;
import net.darktree.matcher.token.match.Match;
import net.darktree.matcher.token.match.MatchStage;
import net.darktree.matcher.token.predicate.TokenPredicate;
import net.darktree.tokenizer.Token;

import java.util.List;

public class LiteralTokenMatcher extends TokenMatcher {

	private final TokenPredicate predicate;
	private final boolean optional;

	public LiteralTokenMatcher(TokenPredicate predicate, boolean optional) {
		this.predicate = predicate;
		this.optional = optional;
	}

	@Override
	public Match match(List<Token> tokens, int index, int end, MatcherContext context) {
		final boolean matched = predicate.match(tokens.get(index));

		context.addOptional(matched);
		return Match.singular(matched, optional);
	}

	@Override
	public String getExpected(MatchStage stage) {
		return predicate.expected();
	}
}
