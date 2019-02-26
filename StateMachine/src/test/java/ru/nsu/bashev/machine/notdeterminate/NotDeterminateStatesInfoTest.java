package ru.nsu.bashev.machine.notdeterminate;

import org.junit.Test;
import ru.nsu.bashev.machine.State;
import ru.nsu.bashev.machine.StateParser;

import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class NotDeterminateStatesInfoTest {

    private NotDeterminateStatesInfo info(String... lines) {
        try {
            return StateParser.parseNotDeterminateFrom(new StringReader(String.join("\n", lines)));
        } catch (Exception e) {
            fail();
        }
        return null;
    }

    private Transit tr(int id, char tr) {
        return new Transit(id, tr);
    }

    private State st(int id, boolean isFinal) {
        return new State(id, isFinal);
    }

    private void assertGood(NotDeterminateStatesInfo info, Transit... transits) {
        Set<State> states = new HashSet<>();
        states.add(st(1, false));
        try {
            for (Transit tr : transits) {
                states = info.nextStates(states, tr.tr);
                boolean flag = false;
                for (State st : states) {
                    if (st.getId() == tr.id) {
                        flag = true;
                    }
                }
                assertTrue(flag);
            }
        } catch (Exception e) {
            fail();
        }
    }

    private void assertBad(NotDeterminateStatesInfo info, int failId, Transit... transits) {
        boolean flag = false;
        Set<State> states = new HashSet<>();
        states.add(st(1, false));
        for (Transit tr : transits) {
            try {
                states = info.nextStates(states, tr.tr);
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
        assertGood(info("", "1 a 2"), tr(2, 'a'));
    }

    @Test
    public void nextStateGoodChose() {
        assertGood(info("", "1 a 2", "1 b 3"), tr(2, 'a'));
        assertGood(info("", "1 a 2", "1 b 3"), tr(3, 'b'));
        assertGood(info("", "1 a 2", "1 a 3"), tr(2, 'a'));
        assertGood(info("", "1 a 2", "1 a 3"), tr(3, 'a'));
    }

    @Test
    public void nextStateGoodSequence() {
        assertGood(info("", "1 a 2", "2 a 3"), tr(2, 'a'), tr(3, 'a'));
        assertGood(info("", "1 a 2", "2 a 3", "3 a 4"), tr(2, 'a'), tr(3, 'a'), tr(4, 'a'));
    }

    @Test
    public void nextStateBadNoTransit() {
        assertBad(info("", "1 a 2"), 3, tr(2, 'a'), tr(3, 'a'));
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