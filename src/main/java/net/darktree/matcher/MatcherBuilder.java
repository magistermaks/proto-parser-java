package net.darktree.matcher;

import net.darktree.matcher.node.Node;
import net.darktree.matcher.node.ParserNode;
import net.darktree.matcher.node.RootNode;
import net.darktree.parser.TokenParser;

/**
 * This class is used to construct matching trees
 * The root node of a tree is always a {@link RootNode}
 * and every tree branch finally ends on a {@link ParserNode}
 *
 * @see AbstractMatcherBuilder
 */
public final class MatcherBuilder extends AbstractMatcherBuilder {

	private MatcherBuilder junction;
	private final MatcherBuilder parent;
	private final Node root;
	private Node self;

	private MatcherBuilder(MatcherBuilder parent, Node root, Node self) {
		this.parent = parent;
		this.junction = null;
		this.root = root;
		this.self = self;
	}

	private MatcherBuilder withJunction(MatcherBuilder junction) {
		this.junction = junction;
		return this;
	}

	public static MatcherBuilder begin() {
		RootNode root = new RootNode();
		MatcherBuilder builder = new MatcherBuilder(null, root, root);
		return new MatcherBuilder(builder, root, null).withJunction(builder.junction);
	}

	@Override
	protected MatcherBuilder push(Node node) {
		if (parent != null) {
			parent.self.addChild(node);
		}

		self = node;

		return new MatcherBuilder(this, root, null).withJunction(junction);
	}

	public MatcherBuilder split() {
		return withJunction(this);
	}

	/**
	 * Attach a parser, specifies the end of a branch
	 */
	public MatcherBuilder parser(TokenParser parser) {
		push(new ParserNode(parser));
		return junction != null ? junction : parent;
	}

	public Node build() {
		return root;
	}

}
