package main.ru.nsu.bashev.lexer;

import java.io.IOException;
import java.text.ParseException;

public class Parser {

    private final Lexer lexer;
    private Lexeme curr;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public int executeCalculation() throws IOException, ParseException {
        nextLexeme();
        int result = executeExpression();
        return result;
    }

    private int executeExpression() throws IOException, ParseException {
        int result = executeTerm();
        Lexeme.LexemeType type = curr.getType();

        while (type == Lexeme.LexemeType.PLUS || type == Lexeme.LexemeType.MINUS) {
            nextLexeme();
            if (type == Lexeme.LexemeType.PLUS) {
                result += executeTerm();
            } else {
                result -= executeTerm();
            }
            type = curr.getType();
        }
        return result;
    }

    private int executeTerm() throws IOException, ParseException {
        int result = executeFractal();
        Lexeme.LexemeType type = curr.getType();

        while (type == Lexeme.LexemeType.MULTIPLICATION || type == Lexeme.LexemeType.DIVISION) {
            nextLexeme();
            if (type == Lexeme.LexemeType.MULTIPLICATION) {
                result *= executeTerm();
            } else {
                result /= executeTerm();
            }
            type = curr.getType();
        }
        return result;
    }

    private int executeFractal() throws IOException, ParseException {
        int result = executePower();
        if (curr.getType() == Lexeme.LexemeType.POWER) {
            nextLexeme();
            result = (int) Math.pow(result, executeFractal());
        }
        return result;
    }

    private int executePower() throws IOException, ParseException {
        if (curr.getType() == Lexeme.LexemeType.MINUS) {
            nextLexeme();
            return -executeAtom();
        }
        return executeAtom();
    }

    private int executeAtom() throws IOException, ParseException {
        int result;
        if (curr.getType() == Lexeme.LexemeType.NUMBER) {
            result = Integer.parseInt(curr.getValue());
            nextLexeme();
        } else if (curr.getType() == Lexeme.LexemeType.LEFT_BRACKET) {
            nextLexeme();
            result = executeExpression();
            if (curr.getType() != Lexeme.LexemeType.RIGHT_BRACKET) {
                throw new ParseException(
                        String.format("Miss close bracket on position: %d", curr.getPosition()),
                        curr.getPosition());
            }
            nextLexeme();
        } else {
            throw new ParseException(
                    String.format("Incorrect expression on position: %d", curr.getPosition()),
                    curr.getPosition());
        }
        return result;
    }

    private void nextLexeme() throws IOException, ParseException {
        curr = lexer.nextLexeme();
    }
}
