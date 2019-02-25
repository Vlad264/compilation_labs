package ru.nsu.bashev.machine.notdeterminate;

import ru.nsu.bashev.machine.State;

import java.util.*;

public class NotDeterminateStatesInfo {

    private final Map<Integer, Map<Character, List<Integer>>> transitions;
    private final Set<Integer> finalStates;

    public NotDeterminateStatesInfo(Map<Integer, Map<Character, List<Integer>>> transitions, Set<Integer> finalStates) {
        this.transitions = transitions;
        this.finalStates = finalStates;
        if (!transitions.containsKey(1)) {
            throw new IllegalArgumentException("There are no transits for first state");
        }
    }

    public List<State> nextStates(State start, char transition) {
        Map<Character, List<Integer>> targets = transitions.get(start.getId());
        if (targets == null) {
            throw new IllegalArgumentException(
                    String.format("There are not transit for %d state", start.getId()));
        }
        List<Integer> nextStates = targets.get(transition);
        if (nextStates == null) {
            return new LinkedList<>();
        }
        List<State> result = new LinkedList<>();
        for (int state : nextStates) {
            result.add(new State(state, finalStates.contains(state)));
        }
        return result;
    }

    public State getFirstState() {
        return new State(1, finalStates.contains(1));
    }

}
