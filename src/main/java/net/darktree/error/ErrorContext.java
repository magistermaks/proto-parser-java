package net.darktree.error;

import net.darktree.matcher.node.Node;
import net.darktree.matcher.node.MatcherNode;
import net.darktree.tokenizer.Token;

import java.util.ArrayList;
import java.util.List;

public class ErrorContext {

	public final Token found;
	public final boolean commit;
	public final List<Node> expected = new ArrayList<>();

	public ErrorContext(List<Token> tokens, int index, int end, boolean commit) {
		this(index < end ? tokens.get(index) : null, commit);
	}

	public ErrorContext(Token token, boolean commit) {
		this.found = token;
		this.commit = commit;
	}

	public void addNodes(List<Node> nodes) {
		nodes.stream().filter(node -> node instanceof MatcherNode).forEachOrdered(expected::add);
	}

	public String getExpected() {
		StringBuilder builder = new StringBuilder();

		int size = expected.size() - 1;
		int last = size - 1;

		for (int i = 0; i <= size; i ++) {
			builder.append(expected.get(i).getExpected(commit));

			if (i < size) {
				builder.append(i == last ? ", or " : ", ");
			}
		}

		return builder.toString();
	}

}
