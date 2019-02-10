package ru.nsu.bashev.lexer;

public class Lexeme {

    private final LexemeType type;
    private final String value;

    Lexeme(LexemeType type) {
        this(type, "");
    }

    Lexeme(LexemeType type, String value) {
        this.type = type;
        this.value = value;
    }

    public LexemeType getType() {
        return type;
    }

    public String getValue() {
        return value;
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
        NONE
    }
}
