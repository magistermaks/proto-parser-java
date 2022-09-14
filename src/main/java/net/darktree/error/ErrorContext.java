package net.darktree.error;

import net.darktree.matcher.node.Node;
import net.darktree.matcher.token.match.MatchStage;
import net.darktree.tokenizer.Token;

import java.util.ArrayList;
import java.util.List;

public class ErrorContext {

	public final Token found;
	public final MatchStage stage;
	public final List<ErrorReportable> expected = new ArrayList<>();

	public ErrorContext(List<Token> tokens, int index, int end, MatchStage stage) {
		this(index < end ? tokens.get(index) : null, stage);
	}

	public ErrorContext(Token token, MatchStage stage) {
		this.found = token;
		this.stage = stage;
	}

	public ErrorContext addNodes(List<Node> nodes) {
		nodes.forEach(node -> {
			if (node instanceof ErrorReportable reportable) {
				addNode(reportable);
			}
		});

		return this;
	}

	public void addNode(ErrorReportable reportable) {
		expected.add(reportable);
	}

}
