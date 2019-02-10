package main.ru.nsu.bashev.lexer;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;

import static main.ru.nsu.bashev.lexer.Lexeme.LexemeType.*;

public class Lexer {

    private final Reader reader;
    private int curr;
    private int pos = 0;
    private boolean flag = false;

    public Lexer(Reader reader) {
        this.reader = reader;
    }

    public Lexeme nextLexeme() throws IOException, ParseException {
        nextChar();
        switch (curr) {
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '0':
                return new Lexeme(NUMBER, pos, nextNumber());
            case '+':
                return new Lexeme(PLUS, pos);
            case '-':
                return new Lexeme(MINUS, pos);
            case '*':
                return new Lexeme(MULTIPLICATION, pos);
            case '/':
                return new Lexeme(DIVISION, pos);
            case '^':
                return new Lexeme(POWER, pos);
            case '(':
                return new Lexeme(LEFT_BRACKET, pos);
            case ')':
                return new Lexeme(RIGHT_BRACKET, pos);
            case -1:
                return new Lexeme(EOF, pos);
            default:
                throw new ParseException(String.format("Unknown character: \'%c\' on position: %d", curr, pos), pos);
        }
    }

    private String nextNumber() throws IOException {
        StringBuilder number = new StringBuilder();
        while (Character.isDigit(curr)) {
            number.append((char) curr);
            nextChar();
        }
        flag = true;
        return number.toString();
    }

    private void nextChar() throws IOException {
        if (flag) {
            flag = false;
            return;
        }
        do {
            curr = reader.read();
        } while (curr == ' ' || curr == '\t');
        pos++;
    }
}
