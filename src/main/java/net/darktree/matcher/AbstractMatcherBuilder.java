package net.darktree.matcher;

import net.darktree.error.MessageConsumer;
import net.darktree.matcher.node.MatcherNode;
import net.darktree.matcher.node.Node;
import net.darktree.matcher.token.LiteralTokenMatcher;
import net.darktree.matcher.token.PairedTokenMatcher;
import net.darktree.matcher.token.RangedTokenMatcher;
import net.darktree.matcher.token.TokenMatcher;
import net.darktree.matcher.token.predicate.TokenPair;
import net.darktree.matcher.token.predicate.TokenPredicate;
import net.darktree.matcher.token.predicate.TokenPredicateFactory;
import net.darktree.tokenizer.TokenType;

public abstract class AbstractMatcherBuilder {

	protected final MessageConsumer sink;

	protected AbstractMatcherBuilder(MessageConsumer sink) {
		this.sink = sink;
	}

	protected abstract MatcherBuilder push(Node node);

	/**
	 * Optionally match a single token by its type
	 */
	public MatcherBuilder optional(TokenType type) {
		return matcher(new LiteralTokenMatcher(TokenPredicateFactory.typed(type), true));
	}

	/**
	 * Match a single token by its type
	 */
	public MatcherBuilder match(TokenType type) {
		return matcher(new LiteralTokenMatcher(TokenPredicateFactory.typed(type), false));
	}

	/**
	 * Optionally match a single token by its value
	 */
	public MatcherBuilder optional(String raw) {
		return matcher(new LiteralTokenMatcher(TokenPredicateFactory.literal(raw), true));
	}

	/**
	 * Match a single token by its type by its value
	 */
	public MatcherBuilder match(String raw) {
		return matcher(new LiteralTokenMatcher(TokenPredicateFactory.literal(raw), false));
	}

	/**
	 * Match a recursive token range specified by predicate pair
	 */
	public MatcherBuilder pair(TokenPair pair) {
		return matcher(new PairedTokenMatcher(pair.getOpen(), pair.getClose(), true));
	}

	/**
	 * Match until a token is found
	 */
	public MatcherBuilder until(String raw) {
		return matcher(new RangedTokenMatcher(TokenPredicateFactory.literal(raw), true));
	}

	/**
	 * Match until a predicate matches
	 */
	public MatcherBuilder until(TokenPredicate predicate, boolean greedy) {
		return matcher(new RangedTokenMatcher(predicate, greedy));
	}

	/**
	 * Match until a predicate matches
	 */
	public MatcherBuilder matcher(TokenMatcher matcher) {
		return push(new MatcherNode(matcher, sink));
	}

}
