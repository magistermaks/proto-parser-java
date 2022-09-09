package net.darktree.matcher.token.predicate;

import net.darktree.tokenizer.Token;

public abstract class TokenPredicate {

	abstract public boolean match(Token token);
	abstract public String expected();

	@Deprecated
	public String toString() {
		return expected();
	}

}
