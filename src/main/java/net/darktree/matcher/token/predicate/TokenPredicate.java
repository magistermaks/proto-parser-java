package net.darktree.matcher.token.predicate;

import net.darktree.tokenizer.Token;

public interface TokenPredicate {

	boolean match(Token token);
	String toString();

}
