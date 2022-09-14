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

	/**
	 * Create an empty successful match
	 */
	public static Match optional() {
		return new Match(0, true);
	}

	/**
	 * Compose a simple single-token match
	 */
	public static Match singular(boolean matched, boolean optional) {
		return new Match(matched ? 1 : 0, matched || optional);
	}

	/**
	 * Create a ranged successful match
	 */
	public static Match ranged(int start, int end) {
		return new Match(end - start, true);
	}

	/**
	 * Create a ranged failed match
	 */
	public static Match failed(int start, int end) {
		return new Match(end - start, false);
	}

}
