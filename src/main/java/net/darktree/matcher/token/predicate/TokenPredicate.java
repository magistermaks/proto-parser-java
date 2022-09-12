package net.darktree.matcher.token.predicate;

import net.darktree.tokenizer.Token;

public abstract class TokenPredicate {

	abstract public boolean match(Token token);
	abstract public String expected(); // maybe we should return an array here (See tags)?

}
