package net.darktree.matcher.pipeline;

import net.darktree.matcher.context.TokenRange;
import net.darktree.matcher.node.Node;
import net.darktree.parser.ParseResult;
import net.darktree.parser.tree.AbstractSyntaxNode;
import net.darktree.tokenizer.Token;

import java.util.List;
import java.util.function.Consumer;

public class RangedTokenPipeline implements TokenPipeline {

	protected final TokenRange range;

	public RangedTokenPipeline(TokenRange range) {
		this.range = range;
	}

	@Override
	public void parse(List<Token> tokens, Node node, Consumer<AbstractSyntaxNode> consumer) {
		int index = range.start;

		do {
			ParseResult parse = node.parse(tokens, index, range.end);
			consumer.accept(parse.result);
			index += parse.tokens == 0 ? 1 : parse.tokens;
		} while (index < range.end);
	}

}
