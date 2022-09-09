package net.darktree.matcher;

import net.darktree.error.MessageConsumer;
import net.darktree.matcher.node.MatcherNode;
import net.darktree.matcher.node.Node;
import net.darktree.matcher.node.ParserNode;
import net.darktree.matcher.node.RootNode;
import net.darktree.matcher.token.LiteralTokenMatcher;
import net.darktree.matcher.token.PairedTokenMatcher;
import net.darktree.matcher.token.RangedTokenMatcher;
import net.darktree.matcher.token.predicate.TokenPair;
import net.darktree.matcher.token.predicate.TokenPredicate;
import net.darktree.matcher.token.predicate.TokenPredicateFactory;
import net.darktree.parser.TokenParser;
import net.darktree.tokenizer.TokenType;

public class MatcherBuilder {

	private final MessageConsumer sink;
	private MatcherBuilder junction;
	private final MatcherBuilder parent;
	private final Node root;
	private Node self;

	private MatcherBuilder(MatcherBuilder parent, Node root, Node self, MessageConsumer sink) {
		this.parent = parent;
		this.junction = null;
		this.root = root;
		this.self = self;
		this.sink = sink;
	}

	private MatcherBuilder withJunction(MatcherBuilder junction) {
		this.junction = junction;
		return this;
	}

	public static MatcherBuilder begin(MessageConsumer sink) {
		RootNode node = new RootNode(sink);
		return new MatcherBuilder(null, node, node, sink);
	}

	private MatcherBuilder push(Node node) {
		if (parent != null) {
			parent.self.add(node);
		}

		self = node;

		return new MatcherBuilder(this, root, null, sink).withJunction(junction);
	}

	public MatcherBuilder split() {
		MatcherBuilder builder = new MatcherBuilder(this, root, null, sink);
		return builder.withJunction(builder);
	}

	public MatcherBuilder optional(TokenType type) {
		return push(new MatcherNode(new LiteralTokenMatcher(TokenPredicateFactory.typed(type), true), sink));
	}

	public MatcherBuilder match(TokenType type) {
		return push(new MatcherNode(new LiteralTokenMatcher(TokenPredicateFactory.typed(type), false), sink));
	}

	public MatcherBuilder optional(String raw) {
		return push(new MatcherNode(new LiteralTokenMatcher(TokenPredicateFactory.literal(raw), true), sink));
	}

	public MatcherBuilder match(String raw) {
		return push(new MatcherNode(new LiteralTokenMatcher(TokenPredicateFactory.literal(raw), false), sink));
	}

//	public MatcherBuilder paired(String open, String close, boolean recursive) {
//		return push(new MatcherNode(new PairedTokenMatcher(TokenPredicateFactory.literal(open), TokenPredicateFactory.literal(close), recursive), sink));
//	}

	public MatcherBuilder match(TokenPair pair) {
		return push(new MatcherNode(new PairedTokenMatcher(pair.open, pair.close, true), sink));
	}

	public MatcherBuilder until(String raw) {
		return push(new MatcherNode(new RangedTokenMatcher(TokenPredicateFactory.literal(raw)), sink));
	}

	public MatcherBuilder parser(TokenParser parser) {
		push(new ParserNode(parser));
		return junction != null ? junction : parent;
	}

	public Node build() {
		return root;
	}

}
