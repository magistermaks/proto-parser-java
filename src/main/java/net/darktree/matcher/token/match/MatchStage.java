package net.darktree.matcher.token.match;

public enum MatchStage {

	MATCH,
	COMMIT;

	public <T> T select(T match, T commit) {
		return this == MATCH ? match : commit;
	}

}
