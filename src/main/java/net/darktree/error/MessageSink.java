package net.darktree.error;

/**
 * This class contains the current message consumer
 * that is used by pipelines ({@link net.darktree.matcher.pipeline.TokenPipeline}) to report interrupts
 */
public class MessageSink {

	private static MessageConsumer sink;

	public static void setSink(MessageConsumer sink) {
		MessageSink.sink = sink;
	}

	public static MessageConsumer getSink() {
		return sink;
	}

}
