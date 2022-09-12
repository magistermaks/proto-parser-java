package net.darktree.parser;

import net.darktree.parser.tree.AbstractSyntaxNode;

public class ParseResult {

	public final AbstractSyntaxNode result;
	public final int tokens;

	public ParseResult(AbstractSyntaxNode result, int tokens) {
		this.result = result;
		this.tokens = tokens;
	}

	public static ParseResult range(AbstractSyntaxNode result, int start, int end) {
		return new ParseResult(result, end - start);
	}

}
