package net.darktree.error;

import net.darktree.matcher.token.match.MatchStage;

public interface ErrorReportable {

	/**
	 * Get the string representing the expected token sequence for the given match stage.
	 * IDEA: maybe we should return an array here (See tags)?
	 */
	String getExpected(MatchStage stage);

}
