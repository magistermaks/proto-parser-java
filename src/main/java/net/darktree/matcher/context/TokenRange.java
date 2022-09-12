package net.darktree.matcher.context;

import net.darktree.matcher.pipeline.RangedTokenPipeline;
import net.darktree.matcher.pipeline.SegmentedTokenPipeline;
import net.darktree.matcher.pipeline.TokenPipeline;
import net.darktree.matcher.token.predicate.TokenPredicateFactory;

public class TokenRange {

	public final int start;
	public final int end;

	public TokenRange(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public int size() {
		return Math.max(0, end - start);
	}

	public TokenRange subset(int left, int right) {
		return new TokenRange(start + left, end - right);
	}

	public TokenPipeline pipeline() {
		return new RangedTokenPipeline(this);
	}

	public TokenPipeline pipeline(String separator) {
		return new SegmentedTokenPipeline(this, TokenPredicateFactory.literal(separator));
	}

}
