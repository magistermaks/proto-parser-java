package net.darktree.matcher.token.predicate;

import net.darktree.tokenizer.Token;
import net.darktree.tokenizer.TokenType;

import java.util.Arrays;
import java.util.function.Predicate;

public class TokenPredicateFactory {

	public static class LambdaTokenPredicate extends TokenPredicate {

		private final Predicate<Token> matcher;
		private final String string;

		private LambdaTokenPredicate(Predicate<Token> matcher, String string) {
			this.matcher = matcher;
			this.string = string;
		}

		public LambdaTokenPredicate negate() {
			return new LambdaTokenPredicate(matcher.negate(), "not " + string);
		}

		@Override
		public boolean match(Token token) {
			return matcher.test(token);
		}

		@Override
		public String expected() {
			return string;
		}

	}

	public static LambdaTokenPredicate literal(String raw) {
		return new LambdaTokenPredicate(token -> token.raw.equals(raw), "'" + raw + "'");
	}

	public static LambdaTokenPredicate typed(TokenType type) {
		return new LambdaTokenPredicate(token -> token.type.equals(type), type.toString());
	}

	public static LambdaTokenPredicate always() {
		return new LambdaTokenPredicate(token -> true, "anything");
	}

	public static LambdaTokenPredicate tag(String... array) {
		return new LambdaTokenPredicate(token -> Arrays.asList(array).contains(token.raw), String.join(", ", array));
	}

}
