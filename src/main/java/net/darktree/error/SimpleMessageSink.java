package net.darktree.error;

import net.darktree.matcher.token.match.MatchStage;

import java.util.function.Consumer;

public class SimpleMessageSink implements MessageConsumer {

	private final String prefix;
	private final Consumer<String> logger;

	public SimpleMessageSink(String prefix, Consumer<String> logger) {
		this.prefix = prefix;
		this.logger = logger;
	}

	public void report(ErrorContext context) {
		StringBuilder builder = new StringBuilder(prefix);
		int count = context.expected.size();

		if (count == 0) {
			builder.append("No token was expected,");
		} else {
			builder.append("Expected: ");
		}

		builder.append(getExpected(context, context.stage));

		if (context.found != null) {
			builder.append(" but found '");
			builder.append(context.found.raw);
			builder.append("' on line: ");
			builder.append(context.found.line);
		} else {
			builder.append(" but reached the end of scope");
		}

		builder.append("!");
		logger.accept(builder.toString());
	}

	private String getExpected(ErrorContext context, MatchStage stage) {
		StringBuilder builder = new StringBuilder();

		int size = context.expected.size() - 1;
		int last = size - 1;

		for (int i = 0; i <= size; i ++) {
			builder.append(context.expected.get(i).getExpected(stage));

			if (i < size) {
				builder.append(i == last ? ", or " : ", ");
			}
		}

		return builder.toString();
	}

}
