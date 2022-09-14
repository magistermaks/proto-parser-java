package net.darktree.matcher.pipeline;

import net.darktree.error.ErrorContext;
import net.darktree.error.MessageSink;

public class PipelineInterruptException extends Exception {

	private final ErrorContext context;

	public PipelineInterruptException(ErrorContext context) {
		this.context = context;
	}

	public void report() {
		MessageSink.getSink().report(context);
	}

}
