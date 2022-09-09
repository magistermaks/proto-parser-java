package net.darktree.matcher.token.predicate;

import net.darktree.tokenizer.Token;
import net.darktree.tokenizer.TokenType;

import java.util.function.Function;

public class TokenPredicateFactory {

	private static class LambdaTokenPredicate implements TokenPredicate {

		private final Function<Token, Boolean> matcher;
		private final String string;

		public LambdaTokenPredicate(Function<Token, Boolean> matcher, String string) {
			this.matcher = matcher;
			this.string = string;
		}

		@Override
		public boolean match(Token token) {
			return matcher.apply(token);
		}

		@Override
		public String toString() {
			return string;
		}

	}

	public static TokenPredicate literal(String raw) {
		return new LambdaTokenPredicate(token -> token.raw.equals(raw), "'" + raw + "'");
	}

	public static TokenPredicate typed(TokenType type) {
		return new LambdaTokenPredicate(token -> token.type.equals(type), type.toString());
	}

}
