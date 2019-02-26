package ru.nsu.bashev.machine.notdeterminate;

import ru.nsu.bashev.machine.State;
import ru.nsu.bashev.machine.StateMachine;

import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

public class NotDeterminateStateMachine implements StateMachine {

    private NotDeterminateStatesInfo states;

    public NotDeterminateStateMachine(NotDeterminateStatesInfo states) {
        this.states = states;
    }

    @Override
    public boolean isValidInput(Reader reader) throws IOException {
        Set<State> currentStates = new HashSet<>();
        currentStates.add(states.getFirstState());
        int buff;

        while ((buff = reader.read()) != -1) {
            currentStates = states.nextStates(currentStates, (char) buff);
            if (currentStates.isEmpty()) {
                return false;
            }
        }
        for (State state : currentStates) {
            if (state.isFinal()) {
                return false;
            }
        }
        return false;
    }
}
