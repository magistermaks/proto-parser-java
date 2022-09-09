package net.darktree.matcher.context;

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

}
