package net.darktree.error;

@FunctionalInterface
public interface MessageConsumer {

	void report(ErrorContext context);

}
