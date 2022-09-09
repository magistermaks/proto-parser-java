package net.darktree.error;

public class MessageSink implements MessageConsumer {

	@Override
	public void accept(String message, boolean fatal) {
		System.out.println("Error: " + message);
	}

}
