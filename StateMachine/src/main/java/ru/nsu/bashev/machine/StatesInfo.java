package ru.nsu.bashev.machine;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class StatesInfo {

    private final Map<Integer, Map<Character, Integer>> transitions;
    private final Set<Integer> finalStates;
    private State currentState;

    StatesInfo(Map<Integer, Map<Character, Integer>> transitions, Set<Integer> finalStates) {
        this.transitions = transitions;
        this.finalStates = finalStates;
        currentState = new State(1, finalStates.contains(1));
        if (!transitions.containsKey(1)) {
            throw new IllegalArgumentException("There are no transits for first state");
        }
    }

     void nextState(char transition) {
        Map<Character, Integer> targets = transitions.get(currentState.getId());
        if (targets == null) {
            throw new IllegalArgumentException(
                    String.format("There are not transit for %d state", currentState.getId()));
        }
        Integer nextState = targets.get(transition);
        if (nextState == null) {
            throw new NoSuchElementException(
                    String.format("There are not transit from %d by %c", currentState.getId(), transition));
        }
        currentState = new State(nextState, finalStates.contains(nextState));
    }

    State getLastState() {
        return currentState;
    }

}
