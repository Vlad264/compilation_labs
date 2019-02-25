package ru.nsu.bashev.machine.notdeterminate;

import ru.nsu.bashev.machine.State;
import ru.nsu.bashev.machine.StateMachine;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

public class NotDeterminateStateMachine implements StateMachine {

    private NotDeterminateStatesInfo states;

    public NotDeterminateStateMachine(NotDeterminateStatesInfo states) {
        this.states = states;
    }

    @Override
    public boolean isValidInput(Reader reader) throws IOException {
        List<State> stack = new LinkedList<>();
        stack.add(states.getFirstState());
        int buff;

        while ((buff = reader.read()) != -1) {
            List<State> newStack = new LinkedList<>();
            for (State state : stack) {
                newStack.addAll(states.nextStates(state, (char) buff));
            }
            stack = newStack;
            if (stack.isEmpty()) {
                return false;
            }
        }
        for (State state : stack) {
            if (state.isFinal()) {
                return false;
            }
        }
        return false;
    }
}
