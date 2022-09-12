package net.darktree.matcher.token.predicate;

public class TokenTag {

	public static final TokenTag ARITHMETIC = new TokenTag("*", "/", "+", "-", "%");

	private final TokenPredicate predicate;

	public TokenTag(String... array) {
		this.predicate = TokenPredicateFactory.tag(array);
	}

	public TokenPredicate getPredicate() {
		return predicate;
	}

}
