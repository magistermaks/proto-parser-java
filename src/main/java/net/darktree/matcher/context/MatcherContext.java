package net.darktree.matcher.context;

import java.util.ArrayList;
import java.util.List;

public class MatcherContext {

	private final List<TokenRange> groups = new ArrayList<>();

	public void range(int start, int end) {
		groups.add(new TokenRange(start, end));
	}

	public TokenRange get(int index) {
		return groups.get(index);
	}

	public int count() {
		return groups.size();
	}

}
