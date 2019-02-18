package ru.nsu.bashev.machine;

import java.io.IOException;
import java.io.Reader;

public class StateMachine {

    private final StatesInfo states;

    public StateMachine(StatesInfo states) {
        this.states = states;
    }

    public boolean isValidInput(Reader reader) throws IOException {
        int buff;
        while ((buff = reader.read()) != -1) {
            states.nextState((char) buff);
        }

        return states.getLastState().isFinal();
    }
}
