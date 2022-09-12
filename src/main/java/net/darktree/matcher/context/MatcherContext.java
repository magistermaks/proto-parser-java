package net.darktree.matcher.context;

import net.darktree.matcher.token.match.Match;

import java.util.ArrayList;
import java.util.List;

public class MatcherContext {

	private final List<TokenRange> groups = new ArrayList<>();
	private final List<Boolean> optionals = new ArrayList<>();

	public Match addGroup(int start, int end) {
		groups.add(new TokenRange(start, end));
		return Match.ranged(start, end);
	}

	public void addOptional(boolean matched) {
		optionals.add(matched);
	}

	public TokenRange group(int index) {
		return groups.get(index);
	}

	public boolean optional(int index) {
		return optionals.get(index);
	}

	@Deprecated
	public int groupCount() {
		return groups.size();
	}

}
