package ru.nsu.bashev.machine.notdeterminate;

import org.junit.Test;
import ru.nsu.bashev.machine.StateParser;
import ru.nsu.bashev.machine.determinate.DeterminateStateMachine;

import java.io.StringReader;

import static org.junit.Assert.*;

public class NotDeterminateStateMachineTest {

    private NotDeterminateStateMachine machine(String... lines) {
        try {
            return new NotDeterminateStateMachine(StateParser.parseNotDeterminateFrom(new StringReader(String.join("\n", lines))));
        } catch (Exception e) {
            fail();
        }
        return null;
    }

    private void assertValid(String input, String... lines) {
        try {
            assertTrue(machine(lines).isValidInput(new StringReader(input)));
        } catch (Exception e) {
            fail();
        }
    }

    private void assertInvalid(String input, String... lines) {
        try {
            assertFalse(machine(lines).isValidInput(new StringReader(input)));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void isValidInputEmpty() {
        assertValid("", "1", "1 a 2");
        assertInvalid("", "", "1 a 2");
    }

    @Test
    public void isValidInputSingle() {
        assertValid("a", "2", "1 a 2");
        assertInvalid("a", "", "1 a 2");
    }

    @Test
    public void isValidInputMulti() {
        assertValid("ab", "3", "1 a 2", "2 b 3");
        assertValid("abc", "4", "1 a 2", "2 b 3", "3 c 4");
        assertValid("abbbbbbbc", "3", "1 a 2", "2 b 2", "2 c 3");

        assertInvalid("abc", "2", "1 a 2", "2 b 3", "3 c 4");
    }
}