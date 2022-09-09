package net.darktree.tokenizer;

public enum TokenType {

	STRING("string"),
	IDENTIFIER("identifier"),
	ACCESS("access modifier"),
	OTHER("other"); //TODO that shouldn't exist

	private final String name;

	TokenType(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
