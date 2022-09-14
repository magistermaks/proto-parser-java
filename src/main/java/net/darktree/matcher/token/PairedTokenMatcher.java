package net.darktree.matcher.token;

import net.darktree.matcher.context.MatcherContext;
import net.darktree.matcher.token.match.Match;
import net.darktree.matcher.token.match.MatchStage;
import net.darktree.matcher.token.predicate.TokenPredicate;
import net.darktree.tokenizer.Token;

import java.util.List;

public class PairedTokenMatcher extends TokenMatcher {

	private final TokenPredicate open;
	private final TokenPredicate close;
	private final boolean recursive;

	public PairedTokenMatcher(TokenPredicate open, TokenPredicate close, boolean recursive) {
		this.open = open;
		this.close = close;
		this.recursive = recursive;
	}

	@Override
	public Match match(List<Token> tokens, int index, int end, MatcherContext context) {
		return Match.singular(index < end && open.test(tokens.get(index)), false);
	}

	@Override
	public Match commit(List<Token> tokens, int index, int end, MatcherContext context, Match match) {
		int start = index;
		int depth = 0;

		while (index < end) {
			if (open.test(tokens.get(index))) {
				if (!recursive && depth > 0) {
					return Match.failed(start, index);
				}

				depth ++;
			} else if (close.test(tokens.get(index))) {
				depth --;
			}

			index ++;

			if (depth == 0) {
				return Match.ranged(start, index);
			}
		}

		return Match.failed(start, index);
	}

	@Override
	public String getExpected(MatchStage stage) {
		return stage.select(open, close).expected();
	}
}
