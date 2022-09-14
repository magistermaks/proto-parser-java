package net.darktree.parser.tree;

import net.darktree.Main;
import net.darktree.matcher.MatcherBuilder;
import net.darktree.matcher.context.MatcherContext;
import net.darktree.matcher.node.Node;
import net.darktree.matcher.token.predicate.TokenPair;
import net.darktree.parser.ParseResult;
import net.darktree.parser.TokenParser;
import net.darktree.tokenizer.Token;
import net.darktree.tokenizer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class FunctionSyntaxNode extends AbstractSyntaxNode {

	public final List<AbstractSyntaxNode> args = new ArrayList<>();
	public final List<AbstractSyntaxNode> body = new ArrayList<>();
	public final Token access;

	public FunctionSyntaxNode(Token access) {
		this.access = access;
	}

	public static ParseResult parse(List<Token> tokens, int start, int end, MatcherContext context) {

		// read access token
		Token access = context.match(0).getFirst(tokens);

		FunctionSyntaxNode function = new FunctionSyntaxNode(access);

		Node ARGUMENT = MatcherBuilder.begin()
				.match(TokenType.IDENTIFIER).matcher(Main.POINTER).match(TokenType.IDENTIFIER).parser(FunctionArgumentSyntaxNode::parse)
				.build();

		// function argument group
		context.match(4).subset(1, 1).pipeline(",").parse(tokens, ARGUMENT, function.args::add);

		Node STATEMENT = MatcherBuilder.begin().split()
				.match(";").parser(TokenParser::dummy)
				.match("while").pair(TokenPair.ROUND).pair(TokenPair.CURLY).parser(TokenParser::dummy)
				.match("if").pair(TokenPair.ROUND).pair(TokenPair.CURLY).parser(TokenParser::dummy)
				.match("return").until(";").parser(TokenParser::dummy)
				.until(";").parser(TokenParser::dummy)
				.build();

		context.match(5).subset(1, 1).pipeline().parse(tokens, STATEMENT, function.body::add);

		return ParseResult.range(function, start, end);
	}

}
