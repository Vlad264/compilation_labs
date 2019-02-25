package ru.nsu.bashev.machine;

import ru.nsu.bashev.machine.determinate.DeterminateStatesInfo;
import ru.nsu.bashev.machine.notdeterminate.NotDeterminateStatesInfo;

import java.io.Reader;
import java.util.*;

public class StateParser {

    private static void parseFinalStates(Set<Integer> finalStates, Scanner scanner) {
        try {
            String[] states = scanner.nextLine().split("[ ]");
            for (String str : states) {
                if (!str.isEmpty()) {
                    finalStates.add(Integer.parseInt(str));
                }
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("It is't a final state (bad symbol)");
        }
    }

    public static DeterminateStatesInfo parseDeterminateFrom(Reader reader) {
        Map<Integer, Map<Character, Integer>> transitions = new HashMap<>();
        Set<Integer> finalStates = new HashSet<>();
        Scanner scanner = new Scanner(reader);

        parseFinalStates(finalStates, scanner);

        try {
            while (scanner.hasNextLine()) {
                int from = scanner.nextInt();
                String str = scanner.next("[a-zA-Z]+");
                int to = scanner.nextInt();
                Map<Character, Integer> targets = transitions.computeIfAbsent(from, k -> new HashMap<>());
                for (char c : str.toCharArray()) {
                    targets.put(c, to);
                }
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Bad transit line");
        }

        return new DeterminateStatesInfo(transitions, finalStates);
    }

    public static NotDeterminateStatesInfo parseNotDeterminateFrom(Reader reader) {
        Map<Integer, Map<Character, List<Integer>>> transitions = new HashMap<>();
        Set<Integer> finalStates = new HashSet<>();
        Scanner scanner = new Scanner(reader);

        parseFinalStates(finalStates, scanner);

        try {
            while (scanner.hasNextLine()) {
                int from = scanner.nextInt();
                String str = scanner.next("[a-zA-Z]+");
                int to = scanner.nextInt();
                Map<Character, List<Integer>> targets = transitions.computeIfAbsent(from, k -> new HashMap<>());
                for (char c : str.toCharArray()) {
                    targets.computeIfAbsent(c, k -> new LinkedList<>()).add(to);
                }
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Bad transit line");
        }

        return new NotDeterminateStatesInfo(transitions, finalStates);
    }
}
