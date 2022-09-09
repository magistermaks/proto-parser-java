package net.darktree.matcher.token;

import net.darktree.matcher.context.MatcherContext;
import net.darktree.matcher.token.match.Match;
import net.darktree.matcher.token.match.MatchStage;
import net.darktree.matcher.token.predicate.TokenPredicate;
import net.darktree.tokenizer.Token;

import java.util.List;

public class RangedTokenMatcher extends TokenMatcher {

	private final TokenPredicate predicate;

	public RangedTokenMatcher(TokenPredicate predicate) {
		this.predicate = predicate;
	}

	@Override
	public Match match(List<Token> tokens, int index, int end, MatcherContext context) {
		return Match.optional();
	}

	@Override
	public Match commit(List<Token> tokens, int index, int end, MatcherContext context, Match match) {
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

		return Match.failed(count);
	}

	@Override
	public String getExpected(MatchStage stage) {
		return predicate.expected();
	}

}
