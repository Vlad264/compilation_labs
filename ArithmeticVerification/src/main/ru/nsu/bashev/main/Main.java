package main.ru.nsu.bashev.main;

import main.ru.nsu.bashev.lexer.Lexer;
import main.ru.nsu.bashev.lexer.Parser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) {
        Lexer lexer = new Lexer(new InputStreamReader(System.in));
        Parser parser = new Parser(lexer);

        try {
            int result = parser.executeCalculation();
            System.out.println("Answer: " + result);
        } catch (IOException e) {
            System.err.println("IOException happened: " + e.getLocalizedMessage());
        } catch (ParseException e) {
            System.err.println("ParseException happened: " + e.getLocalizedMessage());
        }
    }

}
