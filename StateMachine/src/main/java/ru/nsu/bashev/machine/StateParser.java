package ru.nsu.bashev.machine;

import com.sun.istack.internal.NotNull;

import java.io.Reader;
import java.util.*;

public class StateParser {

    public static StatesInfo parseFrom(@NotNull Reader reader) {
        Map<Integer, Map<Character, Integer>> transitions = new HashMap<>();
        Set<Integer> finalStates = new HashSet<>();
        Scanner scanner = new Scanner(reader);

        try {
            String[] states = scanner.nextLine().split("[ ]");
            for (String str : states) {
                finalStates.add(Integer.parseInt(str));
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("It is't a final state (bad symbol)");
        }

        try {
            while (scanner.hasNextLine()) {
                int from = scanner.nextInt();
                String str = scanner.next("[a-zA-Z]+");
                int to = scanner.nextInt();
                Map<Character, Integer> targets = transitions.get(from);
                if (targets == null) {
                    targets = new HashMap<>();
                }
                for (char c : str.toCharArray()) {
                    targets.put(c, to);
                }
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Bad transit line");
        }

        return new StatesInfo(transitions, finalStates);
    }
}
