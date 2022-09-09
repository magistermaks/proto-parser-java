package net.darktree.matcher.token;

import net.darktree.matcher.context.MatcherContext;
import net.darktree.matcher.token.predicate.TokenPredicate;
import net.darktree.tokenizer.Token;

import java.util.List;

public class RangedTokenMatcher implements TokenMatcher {

	private final TokenPredicate predicate;

	public RangedTokenMatcher(TokenPredicate predicate) {
		this.predicate = predicate;
	}

	@Override
	public Match match(List<Token> tokens, int index, int end, MatcherContext context) {
		int count = 0;
		int start = index;

		while (index < end) {
			boolean matched = predicate.match(tokens.get(index));

			count ++;
			index ++;

			if (matched) {
				context.range(start, index);
				return Match.ranged(count);
			}
		}

		return Match.failed(0); // TODO
	}

	@Override
	public String toString() {
		return predicate.toString();
	}

}
