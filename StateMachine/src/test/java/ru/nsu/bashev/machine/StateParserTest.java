package ru.nsu.bashev.machine;

import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.*;

public class StateParserTest {

    void assertGood(String... lines) {
        /*for (String str : lines) {
            str += '\n';
        }*/

        try {
            StateParser.parseFrom(new StringReader(String.join("\n", lines)));
            assertTrue(true);
        } catch (Exception e) {
            fail();
        }
    }

    void assertBad(String... lines) {
        for (String str : lines) {
            str += '\n';
        }

        try {
            StateParser.parseFrom(new StringReader(String.join("\n", lines)));
            fail();
        } catch (Exception e) {
            assertTrue(true);
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