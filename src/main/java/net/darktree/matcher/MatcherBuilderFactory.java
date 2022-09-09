package net.darktree.matcher;

import net.darktree.error.MessageConsumer;

public class MatcherBuilderFactory {

	private final MessageConsumer sink;

	public MatcherBuilderFactory(MessageConsumer sink) {
		this.sink = sink;
	}

	public MatcherBuilder begin() {
		return MatcherBuilder.begin(sink);
	}

}
