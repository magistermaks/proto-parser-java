package net.darktree.error;

import net.darktree.matcher.node.ParentalNode;

@FunctionalInterface
public interface MessageConsumer {

	void accept(String message, boolean fatal);

	default void report(ErrorContext context) {
		StringBuilder builder = new StringBuilder();
		int count = context.expected.size();

		if (count == 0) {
			builder.append("No token was expected,");
		} else {
			builder.append("Expected: ");
		}

		builder.append(context.getExpected());

		if (context.found != null) {
			builder.append(" but found '");
			builder.append(context.found.raw);
			builder.append("' on line: ");
			builder.append(context.found.line);
		} else {
			builder.append(" but reached the end of scope");
		}

		builder.append("!");

		accept(builder.toString(), true);
	}

	default void reportCommit(ParentalNode node) {

	}

}
