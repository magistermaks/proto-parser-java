package net.darktree.matcher.node;

import net.darktree.error.ErrorContext;
import net.darktree.error.MessageSink;
import net.darktree.matcher.context.MatcherContext;
import net.darktree.matcher.pipeline.PipelineInterruptException;
import net.darktree.matcher.token.match.Match;
import net.darktree.matcher.token.match.MatchStage;
import net.darktree.parser.ParseResult;
import net.darktree.tokenizer.Token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//TODO cleanup
public abstract class ParentalNode extends Node {

	protected final List<Node> children = new ArrayList<>();

	@Override
	public final void addChild(Node node) {
		children.add(node);
	}

	public abstract void onSectionMatched(int start, int end, MatcherContext context);

	@Override
	public ParseResult apply(List<Token> tokens, int start, int index, int end, Match match, Node parent, MatcherContext context) throws PipelineInterruptException {
		Match commit = commit(tokens, index, end, context, match);
		int section = index + commit.count;

		ErrorContext error = new ErrorContext(tokens, section, end, MatchStage.of(commit.matched));

		if (commit.matched) {
			onSectionMatched(index, section, context);

			for (Node node : children) {
				Match result = node.match(tokens, start, section, end, context);

				if (result.matched) {
					return node.apply(tokens, start, section, end, result, this, context);
				}
			}

			error.addNodes(children);

			if (commit.wasSkipped() && parent instanceof ParentalNode father) {
				error.addNodes(father.children);
			}
		} else {
			error.addNodes(Collections.singletonList(this));
		}

		MessageSink.getSink().report(error);
		throw new PipelineInterruptException("TODO");
	}

}
