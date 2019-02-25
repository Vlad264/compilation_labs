package ru.nsu.bashev.machine.determinate;

import ru.nsu.bashev.machine.StateMachine;

import java.io.IOException;
import java.io.Reader;

public class DeterminateStateMachine implements StateMachine {

    private final DeterminateStatesInfo states;

    public DeterminateStateMachine(DeterminateStatesInfo states) {
        this.states = states;
    }

    @Override
    public boolean isValidInput(Reader reader) throws IOException {
        int buff;
        while ((buff = reader.read()) != -1) {
            states.nextState((char) buff);
        }

        return states.getLastState().isFinal();
    }
}
