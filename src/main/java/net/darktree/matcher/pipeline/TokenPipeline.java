package net.darktree.matcher.pipeline;

import net.darktree.matcher.node.Node;
import net.darktree.parser.tree.AbstractSyntaxNode;
import net.darktree.tokenizer.Token;

import java.util.List;
import java.util.function.Consumer;

public interface TokenPipeline {

	void parse(List<Token> tokens, Node node, Consumer<AbstractSyntaxNode> consumer);

}
