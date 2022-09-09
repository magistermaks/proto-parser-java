package net.darktree.matcher.token.predicate;

public enum TokenPair {

	CURLY("{", "}"),
	SQUARE("[", "]"),
	ANGLE("<", ">"),
	ROUND("(", ")");

	public final TokenPredicate open;
	public final TokenPredicate close;

	TokenPair(String open, String close) {
		this.open = TokenPredicateFactory.literal(open);
		this.close = TokenPredicateFactory.literal(close);
	}

}
