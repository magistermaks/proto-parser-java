package net.darktree.matcher.token.predicate;

public class TokenPair {

	public static final TokenPair CURLY = new TokenPair("{", "}");
	public static final TokenPair SQUARE = new TokenPair("[", "]");
	public static final TokenPair ANGLE = new TokenPair("<", ">");
	public static final TokenPair ROUND = new TokenPair("(", ")");

	private final TokenPredicate open;
	private final TokenPredicate close;

	protected TokenPair(String open, String close) {
		this.open = TokenPredicateFactory.literal(open);
		this.close = TokenPredicateFactory.literal(close);
	}

	public final TokenPredicate getOpen() {
		return open;
	}

	public final TokenPredicate getClose() {
		return close;
	}

}
