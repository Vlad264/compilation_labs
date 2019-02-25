package ru.nsu.bashev.machine;

import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.*;

public class StateParserTest {

    private void assertGood(String... lines) {
        try {
            StateParser.parseDeterminateFrom(new StringReader(String.join("\n", lines)));
        } catch (Exception e) {
            fail();
        }
    }

    private void assertBad(String... lines) {
        try {
            StateParser.parseDeterminateFrom(new StringReader(String.join("\n", lines)));
            fail();
        } catch (Exception ignore) {

        }
    }

    @Test
    public void parseFromGood() {
        assertGood("", "1 a 2");
        assertGood("1 2 3", "1 a 2");
        assertGood("1 2 3", "1 a 2", "5 z 9");
    }

    @Test
    public void parseFromBad() {
        assertBad("", "");
        assertBad("1 a 3", "1 a 2");
        assertBad("1 2 3", "1 2");
        assertBad("1 2 3", "1 a");
        assertBad("1 2 3", "a 2");
    }

}