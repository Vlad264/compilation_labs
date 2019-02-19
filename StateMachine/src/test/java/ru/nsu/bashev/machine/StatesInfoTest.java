package ru.nsu.bashev.machine;

import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.*;

public class StatesInfoTest {

    private StatesInfo states(String... lines) {
        try {
            return StateParser.parseFrom(new StringReader(String.join("\n", lines)));
        } catch (Exception e) {
            fail();
        }
        return null;
    }

    private Transit tr(int id, char tr) {
        return new Transit(id, tr);
    }

    private void assertGood(StatesInfo states, Transit... transits) {
        try {
            for (Transit tr : transits) {
                states.nextState(tr.tr);
                assertEquals(tr.id, states.getLastState().getId());
            }
        } catch (Exception e) {
            fail();
        }
    }

    private void assertBad(StatesInfo states, int failId, Transit... transits) {
        boolean flag = false;
        for (Transit tr : transits) {
            try {
                states.nextState(tr.tr);
            } catch (Exception e) {
                if (tr.id != failId) {
                    fail();
                }
                flag = true;
            }
        }
        if (!flag) {
            fail();
        }
    }

    @Test
    public void nextStateGood() {
        assertGood(states("", "1 a 2"), tr(2, 'a'));
        assertGood(states("", "1 a 2", "1 b 3"), tr(2, 'a'));
        assertGood(states("", "1 a 2", "1 b 3"), tr(3, 'b'));
        assertGood(states("", "1 a 2", "2 a 3"), tr(2, 'a'), tr(3, 'a'));
        assertGood(states("", "1 a 2", "2 a 3", "3 a 4"), tr(2, 'a'), tr(3, 'a'), tr(4, 'a'));
    }

    @Test
    public void nextStateBad() {
        assertBad(states("", "1 a 2"), 3, tr(2, 'a'), tr(3, 'a'));
        assertBad(states("", "1 a 2", "2 b 3"), 3, tr(2, 'a'), tr(3, 'a'));
    }

    private class Transit {
        int id;
        char tr;

        Transit(int id, char tr) {
            this.id = id;
            this.tr = tr;
        }
    }
}