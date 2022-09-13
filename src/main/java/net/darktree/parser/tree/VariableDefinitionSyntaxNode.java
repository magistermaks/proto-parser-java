package net.darktree.parser.tree;

import net.darktree.matcher.context.MatcherContext;
import net.darktree.parser.ParseResult;
import net.darktree.tokenizer.Token;

import java.util.List;

public class VariableDefinitionSyntaxNode extends AbstractSyntaxNode {

	public final Token access;
	public final String type;
	public final int pointer;
	public final String name;

	public VariableDefinitionSyntaxNode(Token access, String type, int pointer, String name) {
		this.access = access;
		this.type = type;
		this.pointer = pointer;
		this.name = name;
	}

	public static ParseResult parse(List<Token> tokens, int start, int end, MatcherContext context) {
		final Token access = context.match(0).getFirst(tokens);
		final String type = context.match(1).getFirst(tokens).raw;
		final int pointer = context.match(2).size();
		final String name = context.match(3).getFirst(tokens).raw;

		return ParseResult.range(new VariableDefinitionSyntaxNode(access, type, pointer, name), start, end);
	}

}
