package net.darktree.matcher.context;

import net.darktree.matcher.pipeline.RangedTokenPipeline;
import net.darktree.matcher.pipeline.SegmentedTokenPipeline;
import net.darktree.matcher.pipeline.TokenPipeline;
import net.darktree.matcher.token.predicate.TokenPredicateFactory;
import net.darktree.tokenizer.Token;

import java.util.List;

public class TokenRange {

	public final int start;
	public final int end;

	public TokenRange(int start, int end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * Got the number of tokens in this range
	 */
	public int size() {
		return Math.max(0, end - start);
	}

	/**
	 * Get a sub range of this range by dropping first 'left' and last 'right' tokens
	 */
	public TokenRange subset(int left, int right) {
		return new TokenRange(start + left, end - right);
	}

	/**
	 * Create a {@link RangedTokenPipeline} for this section
	 */
	public TokenPipeline pipeline() {
		return new RangedTokenPipeline(this);
	}

	/**
	 * Create a {@link SegmentedTokenPipeline} for this section, given a separator
	 */
	public TokenPipeline pipeline(String separator) {
		return new SegmentedTokenPipeline(this, TokenPredicateFactory.literal(separator));
	}

	/**
	 * Check if this range contains no tokens
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Get the first token in this range, or null if the range is empty
	 */
	public Token getFirst(List<Token> tokens) {
		return isEmpty() ? null : tokens.get(start);
	}

}
