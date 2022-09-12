package net.darktree.parser.tree;

import net.darktree.matcher.context.MatcherContext;
import net.darktree.parser.ParseResult;
import net.darktree.tokenizer.Token;

import java.util.List;

public class IncludeSyntaxNode extends AbstractSyntaxNode {

	public final String target;

	public IncludeSyntaxNode(String target) {
		this.target = target;
	}

	public static ParseResult parse(List<Token> list, int start, int end, MatcherContext context) {
		final String raw = list.get(start + 1).raw;
		return ParseResult.range(new IncludeSyntaxNode(raw.substring(1, raw.length() - 1)), start, end);
	}

}
