package net.darktree.matcher.pipeline;

import net.darktree.matcher.node.Node;
import net.darktree.parser.tree.AbstractSyntaxNode;
import net.darktree.tokenizer.Token;

import java.util.List;
import java.util.function.Consumer;

/**
 * Token pipeline is responsible for feeding the correct tokens through the
 * token matcher tree - and ultimately into the parsing function
 */
public interface TokenPipeline {

	/**
	 * Invoke this pipeline on the given data
	 *
	 * @param tokens the global token array
	 * @param node the matcher tree
	 * @param consumer the AST consumer
	 */
	void parse(List<Token> tokens, Node node, Consumer<AbstractSyntaxNode> consumer);

}
