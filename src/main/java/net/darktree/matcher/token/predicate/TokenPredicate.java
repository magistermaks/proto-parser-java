package net.darktree.matcher.token.predicate;

import net.darktree.error.ErrorReportable;
import net.darktree.matcher.token.match.MatchStage;
import net.darktree.tokenizer.Token;

public abstract class TokenPredicate implements ErrorReportable {

	abstract public boolean test(Token token);
	abstract public String expected();

	@Override
	public String getExpected(MatchStage stage) {
		return expected();
	}

}
