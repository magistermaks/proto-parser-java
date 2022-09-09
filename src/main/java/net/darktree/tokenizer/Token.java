package net.darktree.tokenizer;

public class Token {

	public final int line;
	public final String raw;
	public final TokenType type;

	public Token(int line, String raw, TokenType type) {
		this.line = line;
		this.raw = raw;
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("Token{");
		builder.append("line=").append(line).append(", ");
		builder.append("raw=").append(raw).append(", ");
		builder.append("type=").append(type);
		builder.append("}");

		return builder.toString();
	}

}
