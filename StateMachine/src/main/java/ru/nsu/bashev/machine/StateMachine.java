package ru.nsu.bashev.machine;

import java.io.IOException;
import java.io.Reader;

public interface StateMachine {

    boolean isValidInput(Reader reader) throws IOException;

}
