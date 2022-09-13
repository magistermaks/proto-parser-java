package net.darktree.matcher.context;

import java.util.ArrayList;
import java.util.List;

public class MatcherContext {

	private final List<TokenRange> sections = new ArrayList<>();

	public void addSection(int start, int end) {
		sections.add(new TokenRange(start, end));
	}

	public TokenRange match(int index) {
		return sections.get(index);
	}

	@Deprecated
	public int groupCount() {
		return sections.size();
	}

}
