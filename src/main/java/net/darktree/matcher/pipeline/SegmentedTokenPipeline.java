package net.darktree.matcher.pipeline;

import net.darktree.error.ErrorContext;
import net.darktree.error.MessageSink;
import net.darktree.matcher.context.TokenRange;
import net.darktree.matcher.node.Node;
import net.darktree.matcher.token.match.MatchStage;
import net.darktree.matcher.token.predicate.TokenPredicate;
import net.darktree.parser.ParseResult;
import net.darktree.parser.tree.AbstractSyntaxNode;
import net.darktree.tokenizer.Token;

import java.util.List;
import java.util.function.Consumer;

/**
 * A token pipeline that feeds token subsections separated
 * by a predefined separator into the given matcher
 *
 * @see TokenPipeline
 */
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
				interrupt.report();

				// try to recover the state
				// this CAN and WILL cause issues but is the best we can do here
				while(index < range.end && !separator.test(tokens.get(index))) {
					index ++;
				}
			}

			if (index < range.end) {
				Token token = tokens.get(index);

				if (separator.test(token)) {
					index ++;
				} else {
					ErrorContext context = new ErrorContext(token, MatchStage.COMMIT);
					context.addNode(separator);
					MessageSink.getSink().report(context);
				}
			} else break;
		}
	}

}
