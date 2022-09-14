package net.darktree.error;

public class MessageSink {

	private static MessageConsumer sink;

	public static void setSink(MessageConsumer sink) {
		MessageSink.sink = sink;
	}

	public static MessageConsumer getSink() {
		return sink;
	}

}
