package net.darktree.tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tokenizer {

	private final static List<String> white = Arrays.asList(" ", "\n", "\t");
	private final static List<String> singular = Arrays.asList("[", "]", "(", ")", "{", "}", ",", ";", "-", "+", "/", "*");
	private final static List<String> doubles = Arrays.asList("==", ">=", "<=", "!=", "&&", "||");

	private final String code;
	private final List<Token> tokens = new ArrayList<>();
	private StringBuilder accumulator = new StringBuilder();
	private int line = 1;

	private Tokenizer(String code) {
		this.code = code;
	}

	public static Tokenizer from(String code) {
		return new Tokenizer(code);
	}

	public List<Token> getTokens() {
		if (tokens.size() == 0) {
			compute(code);
		}

		return tokens;
	}

	private void compute(String code) {
		int size = code.length();
		boolean string = false;

		for (int i = 0; i < size; i ++) {
			String c = String.valueOf(code.charAt(i));
			String n = (i + 1 >= size) ? "" : String.valueOf(code.charAt(i + 1));
			//String p = (i - 1 >= 0) ? String.valueOf(code.charAt(i - 1)) : "";
			String pair = n.length() > 0 ? c + n : "";

			if (string) {
				accumulator.append(c);

				if ("\"".equals(c)) {
					string = false;
					submit();
				}
				continue;
			}

			// whitespaces end the previous token
			if (white.contains(c)) {
				submit();

				// count lines
				if (c.equals("\n")) line ++;
				continue;
			}

			// double tokens end previous tokens and are one on their own
			if (doubles.contains(pair)) {
				submit();
				accumulator = new StringBuilder(pair);
				submit();
				continue;
			}

			// singular tokens end previous tokens and are one on their own
			if (singular.contains(c)) {
				submit();
				accumulator = new StringBuilder(c);
				submit();
				continue;
			}

			if ("\"".equals(c)) {
				string = true;
			}

			// by default add to the accumulator
			accumulator.append(c);
		}

		// submit any leftover tokens
		submit();
	}

	private void submit() {
		String token = accumulator.toString();

		if (token.length() > 0) {
			tokens.add(categorize(token));
			accumulator = new StringBuilder();
		}
	}

	private Token categorize(String string) {

		if (string.startsWith("\"") && string.endsWith("\"")) {
			return new Token(line, string, TokenType.STRING);
		}

		if (string.equals("public") || string.equals("private")) {
			return new Token(line, string, TokenType.ACCESS);
		}

		if (Character.isAlphabetic(string.charAt(0)) && string.matches("[A-Za-z0-9]+")) {
			return new Token(line, string, TokenType.IDENTIFIER);
		}

		return new Token(line, string, TokenType.OTHER);
	}

}
