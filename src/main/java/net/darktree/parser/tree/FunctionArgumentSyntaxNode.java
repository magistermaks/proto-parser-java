package net.darktree.parser.tree;

import net.darktree.matcher.context.MatcherContext;
import net.darktree.parser.ParseResult;
import net.darktree.tokenizer.Token;

import java.util.List;

public class FunctionArgumentSyntaxNode extends AbstractSyntaxNode {

	public final String type;
	public final int pointer;
	public final String name;

	public FunctionArgumentSyntaxNode(String type, int pointer, String name) {
		this.type = type;
		this.pointer = pointer;
		this.name = name;
	}

	public static ParseResult parse(List<Token> tokens, int start, int end, MatcherContext context) {
		String type = tokens.get(start).raw;
		String name = tokens.get(end - 1).raw;

		return ParseResult.range(new FunctionArgumentSyntaxNode(type, context.match(0).size(), name), start, end);
	}


}
