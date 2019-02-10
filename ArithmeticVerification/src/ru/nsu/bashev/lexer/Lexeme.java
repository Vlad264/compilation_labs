package ru.nsu.bashev.lexer;

public class Lexeme {

    private final LexemeType type;
    private final int position;
    private final String value;

    Lexeme(LexemeType type, int position) {
        this(type, position, "");
    }

    Lexeme(LexemeType type, int position, String value) {
        this.type = type;
        this.position = position;
        this.value = value;
    }

    public LexemeType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getPosition() {
        return position;
    }

    public enum LexemeType {
        NUMBER,
        PLUS,
        MINUS,
        MULTIPLICATION,
        DIVISION,
        POWER,
        LEFT_BRACKET,
        RIGHT_BRACKET,
        EOF
    }
}
