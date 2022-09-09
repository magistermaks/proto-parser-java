package net.darktree.matcher.context;

import net.darktree.matcher.token.match.Match;

import java.util.ArrayList;
import java.util.List;

public class MatcherContext {

	private final List<TokenRange> groups = new ArrayList<>();

	public Match addMatch(int start, int end) {
		groups.add(new TokenRange(start, end));
		return Match.ranged(start, end);
	}

	public TokenRange get(int index) {
		return groups.get(index);
	}

	public int count() {
		return groups.size();
	}

}
