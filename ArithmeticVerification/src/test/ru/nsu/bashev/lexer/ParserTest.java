package test.ru.nsu.bashev.lexer;

import main.ru.nsu.bashev.lexer.Lexer;
import main.ru.nsu.bashev.lexer.Parser;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;

import static org.junit.Assert.*;

public class ParserTest {

    private void assertCalculation(String input, int result) {
        Parser parser = new Parser(new Lexer(new StringReader(input)));
        try {
            assertEquals(result, parser.executeCalculation());
        } catch (IOException | ParseException e) {
            fail();
        }
    }

    private void assertThrows(String input) {
        Parser parser = new Parser(new Lexer(new StringReader(input)));
        try {
            parser.executeCalculation();
        } catch (IOException | ParseException | ArithmeticException e) {
            assertTrue(true);
        }
    }

    @Test
    public void executeCalculationSingleNumber() {
        assertCalculation("123456789", 123456789);
        assertCalculation("-123456789", -123456789);
    }

    @Test
    public void executeCalculationSingleOperation() {
        assertCalculation("2+3", 5);
        assertCalculation("3-2", 1);
        assertCalculation("2*3", 6);
        assertCalculation("4/2", 2);
        assertCalculation("2^3", 8);
    }

    @Test
    public void executeCalculationBracket() {
        assertCalculation("(123)", 123);
        assertCalculation("((123))", 123);
        assertCalculation("((((123))))", 123);
    }

    @Test
    public void executeCalculationBracketAndOperation() {
        assertCalculation("(18+32)", 50);
        assertCalculation("(20)-(40)", -20);
        assertCalculation("((2)+(2))", 4);
    }

    @Test
    public void executeCalculationUnaryMinus() {
        assertCalculation("-10", -10);
        assertCalculation("100+(-100)", 0);
        assertCalculation("(-2)^3", -8);
    }

    @Test
    public void executeCalculationComplexOperation() {
        assertCalculation("4/2+2*2-3^2", -3);
        assertCalculation("(2+3)*2-2^(10/5+1)", 2);
    }

    @Test
    public void executeCalculationSyntaxBrackets() {
        assertThrows("(");
        assertThrows("()");
        assertThrows("((())");
    }

    @Test
    public void executeCalculationSyntaxOperations() {
        assertThrows("2*");
        assertThrows("/4");
        assertThrows("5^");
    }

    @Test
    public void executeCalculationDivByZero() {
        assertThrows("2/0");
        assertThrows("2/(5-5)");
    }
}