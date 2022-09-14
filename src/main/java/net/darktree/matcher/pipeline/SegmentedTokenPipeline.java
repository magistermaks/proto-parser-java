package net.darktree.matcher.pipeline;

import net.darktree.error.ErrorContext;
import net.darktree.error.MessageSink;
import net.darktree.matcher.context.TokenRange;
import net.darktree.matcher.node.MatcherNode;
import net.darktree.matcher.node.Node;
import net.darktree.matcher.token.LiteralTokenMatcher;
import net.darktree.matcher.token.match.MatchStage;
import net.darktree.matcher.token.predicate.TokenPredicate;
import net.darktree.parser.ParseResult;
import net.darktree.parser.tree.AbstractSyntaxNode;
import net.darktree.tokenizer.Token;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class SegmentedTokenPipeline extends RangedTokenPipeline {

	private final TokenPredicate separator;

	public SegmentedTokenPipeline(TokenRange range, TokenPredicate separator) {
		super(range);
		this.separator = separator;
	}

	@Override
	public void parse(List<Token> tokens, Node node, Consumer<AbstractSyntaxNode> consumer) {
		int index = range.start;

		while(true) {
			try {
				ParseResult parse = node.parse(tokens, index, range.end);

				if (parse.result != null) {
					consumer.accept(parse.result);
				}

				index += parse.tokens == 0 ? 1 : parse.tokens;
			} catch (PipelineInterruptException interrupt) {
				while(index < range.end && !separator.match(tokens.get(index))) {
					index ++;
				}
			}

			if (index < range.end) {
				if (separator.match(tokens.get(index))) {
					index ++;
				} else {
					// TODO this is concern
					ErrorContext context = new ErrorContext(tokens, index, range.end, MatchStage.COMMIT);
					context.addNodes(Collections.singletonList(new MatcherNode(new LiteralTokenMatcher(separator, false))));
					MessageSink.getSink().report(context);
				}
			} else break;
		}
	}

}
