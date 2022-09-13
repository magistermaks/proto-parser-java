package net.darktree.matcher.pipeline;

import net.darktree.matcher.node.Node;
import net.darktree.parser.ParseResult;
import net.darktree.parser.tree.AbstractSyntaxNode;
import net.darktree.tokenizer.Token;

import java.util.List;
import java.util.function.Consumer;

public class GlobalTokenPipeline implements TokenPipeline {

	private final static TokenPipeline INSTANCE = new GlobalTokenPipeline();

	public static TokenPipeline getInstance() {
		return INSTANCE;
	}

	@Override
	public void parse(List<Token> tokens, Node node, Consumer<AbstractSyntaxNode> consumer) {
		int end = tokens.size();
		int index = 0;

		try {
			do {
				ParseResult parse = node.parse(tokens, index, end);

				if (parse.result != null) {
					consumer.accept(parse.result);
				}

				index += parse.tokens == 0 ? 1 : parse.tokens;
			} while (index < end);
		} catch (PipelineInterruptException interrupt) {

		}
	}

}
