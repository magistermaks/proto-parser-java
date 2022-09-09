package net.darktree.matcher.node;

import net.darktree.error.ErrorContext;
import net.darktree.error.MessageConsumer;
import net.darktree.matcher.context.MatcherContext;
import net.darktree.matcher.token.Match;
import net.darktree.parser.ParseResult;
import net.darktree.tokenizer.Token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ParentalNode implements Node {

	protected final MessageConsumer sink;
	protected final List<Node> children = new ArrayList<>();

	protected ParentalNode(MessageConsumer sink) {
		this.sink = sink;
	}

	@Override
	public final void add(Node node) {
		children.add(node);
	}

	@Override
	public ParseResult<Object> apply(List<Token> tokens, int start, int index, int end, Match match, Node parent, MatcherContext context) {
		Match commit = commit(tokens, index, end, context, match);
		index += commit.count;

		ErrorContext error = new ErrorContext(tokens, index, end, commit.matched);

		if (commit.matched) {
			for (Node node : children) {
				Match result = node.match(tokens, start, index, end, context);

				if (result.matched) {
					return node.apply(tokens, start, index, end, result, this, context);
				}
			}

			error.addNodes(children);

			if (commit.wasSkipped() && parent instanceof ParentalNode father) {
				error.addNodes(father.children);
			}
		} else {
			error.addNodes(Collections.singletonList(this));
		}

		sink.report(error);
		return ParseResult.range(null, start, index);
	}

}
