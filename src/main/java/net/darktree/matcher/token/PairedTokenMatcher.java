package net.darktree.matcher.token;

import net.darktree.matcher.context.MatcherContext;
import net.darktree.matcher.token.predicate.TokenPredicate;
import net.darktree.tokenizer.Token;

import java.util.List;

public class PairedTokenMatcher implements TokenMatcher {

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
		return Match.singular(index < end && open.match(tokens.get(index)), false);
	}

	@Override
	public Match commit(List<Token> tokens, int index, int end, MatcherContext context, Match match) {
		int count = 0;
		int start = index;
		int depth = 0;

		while (index < end) {
			if (open.match(tokens.get(index))) {
				if (!recursive && depth > 0) {
					return Match.failed(count);
				}

				depth ++;
			} else if (close.match(tokens.get(index))) {
				depth --;
			}

			count ++;
			index ++;

			if (depth == 0) {
				context.range(start, index);
				return Match.ranged(count);
			}
		}

		return Match.failed(count);
	}

	@Override
	public String toString() {
		return open.toString();
	}

	@Override
	public String getCommitString() {
		return close.toString();
	}
}
