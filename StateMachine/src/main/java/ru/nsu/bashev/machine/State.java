package ru.nsu.bashev.machine;

class State {

    private final int id;
    private final boolean isFinal;

    State(int id, boolean isFinal) {
        this.id = id;
        this.isFinal = isFinal;
    }

    int getId() {
        return id;
    }

    boolean isFinal() {
        return isFinal;
    }
}
