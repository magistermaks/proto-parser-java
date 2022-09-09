package net.darktree.matcher.token.match;

public class Match {

	public final int count;
	public final boolean matched;

	private Match(int count, boolean matched) {
		this.count = count;
		this.matched = matched;
	}

	public boolean wasSkipped() {
		return matched && count == 0;
	}

	public static Match optional() {
		return new Match(0, true);
	}

	public static Match singular(boolean matched, boolean optional) {
		return new Match(matched ? 1 : 0, matched || optional);
	}

	public static Match ranged(int start, int end) {
		return new Match(end - start, true);
	}

	public static Match failed(int start, int end) {
		return new Match(end - start, false);
	}

}
