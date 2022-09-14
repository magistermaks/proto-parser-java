package net.darktree;

import net.darktree.error.MessageSink;
import net.darktree.error.SimpleMessageSink;
import net.darktree.matcher.MatcherBuilder;
import net.darktree.matcher.context.TokenRange;
import net.darktree.matcher.node.Node;
import net.darktree.matcher.pipeline.GlobalTokenPipeline;
import net.darktree.matcher.token.RangedTokenMatcher;
import net.darktree.matcher.token.TokenMatcher;
import net.darktree.matcher.token.predicate.TokenPair;
import net.darktree.matcher.token.predicate.TokenPredicateFactory;
import net.darktree.parser.ParseResult;
import net.darktree.parser.TokenParser;
import net.darktree.parser.tree.*;
import net.darktree.tokenizer.Token;
import net.darktree.tokenizer.TokenType;
import net.darktree.tokenizer.Tokenizer;

import java.util.List;

public class Main {
	public static TokenMatcher POINTER = new RangedTokenMatcher(TokenPredicateFactory.literal("*").negate(), false);

	public static TokenParser DUMMY_PRINTER = (list, start, end, ctx) -> {
		for (int i = start; i < end; i ++) {
			System.out.println(list.get(i));
		}

		for (int g = 0; g < ctx.groupCount(); g ++) {
			System.out.println("\nGroup " + g + ":");
			TokenRange range = ctx.match(g).subset(1, 1);

			for (int i = range.start; i < range.end; i ++) {
				System.out.println(list.get(i));
			}
		}

		return ParseResult.range(null, start, end);
	};

	public static void main(String[] args) { // private int test; private int* var = 0;
		MessageSink.setSink(new SimpleMessageSink("Error: ", System.out::println));

		List<Token> tokens = Tokenizer.from("include \"stdio\";\n private int var = 1; public int abc;\n public int main(int a int* b) {return var}").getTokens();

		Node SCOPE = MatcherBuilder.begin().split()
				.match(";").parser(TokenParser::dummy)
				.match("include").match(TokenType.STRING).match(";").parser(IncludeSyntaxNode::parse)
				.optional(TokenType.ACCESS).match(TokenType.IDENTIFIER).matcher(POINTER).match(TokenType.IDENTIFIER).split()
						.match(";").parser(VariableDefinitionSyntaxNode::parse)
						.match("=").until(";").parser(VariableInitializationSyntaxNode::parse)
						.pair(TokenPair.ROUND).pair(TokenPair.CURLY).parser(FunctionSyntaxNode::parse)
				.build();

		System.out.println("Matched:");
		RootSyntaxNode root = new RootSyntaxNode();
		GlobalTokenPipeline.getInstance().parse(tokens, SCOPE, root::addNode);

		System.out.println(root);
	}
}