package ru.nsu.bashev.machine;

import org.junit.Test;
import ru.nsu.bashev.machine.determinate.DeterminateStatesInfo;

import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DeterminateStatesInfoTest {

    private DeterminateStatesInfo states(String... lines) {
        try {
            return StateParser.parseDeterminateFrom(new StringReader(String.join("\n", lines)));
        } catch (Exception e) {
            fail();
        }
        return null;
    }

    private Transit tr(int id, char tr) {
        return new Transit(id, tr);
    }

    private void assertGood(DeterminateStatesInfo states, Transit... transits) {
        try {
            for (Transit tr : transits) {
                states.nextState(tr.tr);
                assertEquals(tr.id, states.getLastState().getId());
            }
        } catch (Exception e) {
            fail();
        }
    }

    private void assertBad(DeterminateStatesInfo states, int failId, Transit... transits) {
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
    public void nextStateGoodSingle() {
        assertGood(states("", "1 a 2"), tr(2, 'a'));
    }

    @Test
    public void nextStateGoodChose() {
        assertGood(states("", "1 a 2", "1 b 3"), tr(2, 'a'));
        assertGood(states("", "1 a 2", "1 b 3"), tr(3, 'b'));
    }

    @Test
    public void nextStateGoodSequence() {
        assertGood(states("", "1 a 2", "2 a 3"), tr(2, 'a'), tr(3, 'a'));
        assertGood(states("", "1 a 2", "2 a 3", "3 a 4"), tr(2, 'a'), tr(3, 'a'), tr(4, 'a'));
    }

    @Test
    public void nextStateBadNoTransit() {
        assertBad(states("", "1 a 2"), 3, tr(2, 'a'), tr(3, 'a'));
    }

    @Test
    public void nextStateBadNoNeededTransit() {
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