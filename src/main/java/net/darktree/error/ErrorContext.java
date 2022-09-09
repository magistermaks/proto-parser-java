package net.darktree.error;

import net.darktree.matcher.node.MatcherNode;
import net.darktree.matcher.node.Node;
import net.darktree.matcher.token.match.MatchStage;
import net.darktree.tokenizer.Token;

import java.util.ArrayList;
import java.util.List;

public class ErrorContext {

	public final Token found;
	public final MatchStage stage;
	public final List<MatcherNode> expected = new ArrayList<>();

	public ErrorContext(List<Token> tokens, int index, int end, MatchStage stage) {
		this(index < end ? tokens.get(index) : null, stage);
	}

	public ErrorContext(Token token, MatchStage stage) {
		this.found = token;
		this.stage = stage;
	}

	public void addNodes(List<Node> nodes) {
		nodes.forEach(node -> {
			if (node instanceof MatcherNode matcher) {
				expected.add(matcher);
			}
		});
	}

}
