package net.darktree;

import net.darktree.error.MessageSink;
import net.darktree.matcher.MatcherBuilderFactory;
import net.darktree.matcher.node.Node;
import net.darktree.matcher.token.predicate.TokenPair;
import net.darktree.parser.ParseResult;
import net.darktree.parser.TokenParser;
import net.darktree.tokenizer.Token;
import net.darktree.tokenizer.TokenType;
import net.darktree.tokenizer.Tokenizer;

import java.util.List;

public class Main {
	public static TokenParser DUMMY_PRINTER = (list, start, end, ctx) -> {
		for (int i = start; i < end; i ++) {
			System.out.println(list.get(i));
		}

		return ParseResult.range(null, start, end);
	};

	public static void main(String[] args) {
		List<Token> tokens = Tokenizer.from("include \"stdio\";\n \n public int main(int a, int b) return var}").getTokens();

		System.out.println("All Tokens:");
		tokens.forEach(System.out::println);

		MatcherBuilderFactory factory = new MatcherBuilderFactory(MessageSink::new);

		System.out.println("Matched Tokens:");

		Node SCOPE = factory.begin().split()
				.match("include").match(TokenType.STRING).match(";").parser(DUMMY_PRINTER)
				.optional(TokenType.ACCESS).match(TokenType.IDENTIFIER).match(TokenType.IDENTIFIER).match(TokenPair.ROUND).match(TokenPair.CURLY).parser(DUMMY_PRINTER)
				.build();

		int index = 0;

		do {
			int matched = SCOPE.parse(tokens, index).tokens;
			index += matched == 0 ? 1 : matched;
		} while (index < tokens.size());

	}
}