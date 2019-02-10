package ru.nsu.bashev.lexer;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;

import static ru.nsu.bashev.lexer.Lexeme.LexemeType.*;

public class Lexer {

    private final Reader reader;
    private int curr;
    private int pos = 0;

    public Lexer(Reader reader) {
        this.reader = reader;
    }

    public Lexeme nextLexeme() throws IOException, ParseException {
        nextChar();
        switch (curr) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 0:
                return new Lexeme(NUMBER, nextNumber());
            case '+':
                return new Lexeme(PLUS);
            case '-':
                return new Lexeme(MINUS);
            case '*':
                return new Lexeme(MULTIPLICATION);
            case '/':
                return new Lexeme(DIVISION);
            case '^':
                return new Lexeme(POWER);
            case '(':
                return new Lexeme(LEFT_BRACKET);
            case ')':
                return new Lexeme(RIGHT_BRACKET);
            case -1:
                return new Lexeme(NONE);
            default:
                throw new ParseException(String.format("Unknown character: \'%c\' on position: %d", curr, pos), pos);
        }
    }

    private String nextNumber() throws IOException {
        StringBuilder number = new StringBuilder();
        while (Character.isDigit(curr)) {
            number.append(curr);
            nextChar();
        }
        return number.toString();
    }

    private void nextChar() throws IOException {
        curr = reader.read();
        pos++;
    }
}
