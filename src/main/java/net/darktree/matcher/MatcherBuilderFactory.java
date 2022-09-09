package net.darktree.matcher;

import net.darktree.error.MessageConsumer;

import java.util.function.Supplier;

public class MatcherBuilderFactory {

	private final MessageConsumer sink;

	public MatcherBuilderFactory(Supplier<MessageConsumer> sink) {
		this.sink = sink.get();
	}

	public MatcherBuilder begin() {
		return MatcherBuilder.begin(sink);
	}

}
