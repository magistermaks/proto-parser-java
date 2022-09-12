package net.darktree.parser.tree;

import java.util.ArrayList;
import java.util.List;

public class RootSyntaxNode extends AbstractSyntaxNode {

	public final List<AbstractSyntaxNode> nodes = new ArrayList<>();

	public void addNode(AbstractSyntaxNode node) {
		nodes.add(node);
	}

}
