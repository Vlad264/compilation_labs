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

    public Set<State> nextStates(Set<State> startStates, char transition) {
        Set<State> result = new HashSet<>();
        for (State state : startStates) {
            Map<Character, List<Integer>> targets = transitions.get(state.getId());
            if (targets == null) {
                throw new IllegalArgumentException(
                        String.format("There are not transit for %d state", state.getId()));
            }
            List<Integer> nextStates = targets.get(transition);
            if (nextStates == null) {
                continue;
            }
            for (int next : nextStates) {
                result.add(new State(next, finalStates.contains(next)));
            }
        }
        return result;
    }

    public State getFirstState() {
        return new State(1, finalStates.contains(1));
    }

}
