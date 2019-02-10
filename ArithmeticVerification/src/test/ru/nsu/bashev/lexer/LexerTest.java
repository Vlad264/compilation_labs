package test.ru.nsu.bashev.lexer;

import main.ru.nsu.bashev.lexer.Lexeme;
import main.ru.nsu.bashev.lexer.Lexer;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;

import static org.junit.Assert.*;

import static main.ru.nsu.bashev.lexer.Lexeme.LexemeType.*;

public class LexerTest {

    private void assertTypes(String input, Lexeme.LexemeType... types) {
        Lexer lexer = new Lexer(new StringReader(input));
        try {
            for (Lexeme.LexemeType type : types) {
                assertEquals(type, lexer.nextLexeme().getType());
            }
        } catch (IOException | ParseException e) {
            fail();
        }
    }

    private void assertThrows(String input) {
        Lexer lexer = new Lexer(new StringReader(input));
        try {
            Lexeme.LexemeType type;
            do {
                type = lexer.nextLexeme().getType();
            } while (type != EOF);
        } catch (IOException | ParseException e) {
            assertTrue(true);
        }
    }

    private void assertValue(String input, int... values) {
        Lexer lexer = new Lexer(new StringReader(input));
        try {
            for (int value : values) {
                Lexeme lexeme;
                do {
                    lexeme = lexer.nextLexeme();
                } while (lexeme.getType() != NUMBER && lexeme.getType() != EOF);
                if (lexeme.getType() == NUMBER) {
                    assertEquals(value, Integer.parseInt(lexeme.getValue()));
                }
            }
        } catch (IOException | ParseException e) {
            fail();
        }
    }

    @Test
    public void nextLexemeEOF() {
        assertTypes("", EOF);
        assertTypes(" ", EOF);
        assertTypes("   ", EOF);
        assertTypes("\t", EOF);
        assertTypes("\t\t", EOF);
        assertTypes("\t\t\t\t", EOF);
        assertTypes("      \t  \t\t    ", EOF);
    }

    @Test
    public void nextLexemeNumber() {
        assertTypes("1", NUMBER, EOF);
        assertTypes("2", NUMBER, EOF);
        assertTypes("3", NUMBER, EOF);
        assertTypes("4", NUMBER, EOF);
        assertTypes("5", NUMBER, EOF);
        assertTypes("6", NUMBER, EOF);
        assertTypes("7", NUMBER, EOF);
        assertTypes("8", NUMBER, EOF);
        assertTypes("9", NUMBER, EOF);
        assertTypes("0", NUMBER, EOF);
        assertTypes("123", NUMBER, EOF);
        assertTypes("0123456789", NUMBER, EOF);
    }

    @Test
    public void nextLexemeSign() {
        assertTypes("+", PLUS, EOF);
        assertTypes("++", PLUS, PLUS, EOF);
        assertTypes("-", MINUS, EOF);
        assertTypes("--", MINUS, MINUS, EOF);
        assertTypes("*", MULTIPLICATION, EOF);
        assertTypes("**", MULTIPLICATION, MULTIPLICATION, EOF);
        assertTypes("/", DIVISION, EOF);
        assertTypes("//", DIVISION, DIVISION, EOF);
        assertTypes("^", POWER, EOF);
        assertTypes("^^", POWER, POWER, EOF);
        assertTypes("+-*/^", PLUS, MINUS, MULTIPLICATION, DIVISION, POWER, EOF);
    }

    @Test
    public void nextLexemeAll() {
        assertTypes("2+23-4*123/^", NUMBER, PLUS, NUMBER, MINUS, NUMBER, MULTIPLICATION, NUMBER, DIVISION, POWER, EOF);
    }

    @Test
    public void nextLexemeWrong() {
        assertThrows("a");
        assertThrows("+-  a");
        assertThrows("1231a");
    }

    @Test
    public void nextLexemeValues() {
        assertValue("1", 1);
        assertValue("2", 2);
        assertValue("3", 3);
        assertValue("4", 4);
        assertValue("5", 5);
        assertValue("6", 6);
        assertValue("7", 7);
        assertValue("8", 8);
        assertValue("9", 9);
        assertValue("0", 0);
        assertValue("123", 123);
        assertValue("1+2+3+4+5+6+7+8+9+0", 1, 2, 3, 4, 5, 6, 7, 8, 9, 0);
    }


}