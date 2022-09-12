package net.darktree.matcher;

import net.darktree.error.MessageConsumer;
import net.darktree.matcher.node.Node;
import net.darktree.matcher.node.ParserNode;
import net.darktree.matcher.node.RootNode;
import net.darktree.parser.TokenParser;

public class MatcherBuilder extends AbstractMatcherBuilder {

	private MatcherBuilder junction;
	private final MatcherBuilder parent;
	private final Node root;
	private Node self;

	private MatcherBuilder(MatcherBuilder parent, Node root, Node self, MessageConsumer sink) {
		super(sink);
		this.parent = parent;
		this.junction = null;
		this.root = root;
		this.self = self;
	}

	private MatcherBuilder withJunction(MatcherBuilder junction) {
		this.junction = junction;
		return this;
	}

	public static MatcherBuilder begin(MessageConsumer sink) {
		RootNode root = new RootNode(sink);
		MatcherBuilder builder = new MatcherBuilder(null, root, root, sink);
		return new MatcherBuilder(builder, root, null, sink).withJunction(builder.junction);
	}

	@Override
	protected MatcherBuilder push(Node node) {
		if (parent != null) {
			parent.self.addChild(node);
		}

		self = node;

		return new MatcherBuilder(this, root, null, sink).withJunction(junction);
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
