package ru.nsu.bashev.main;

import ru.nsu.bashev.machine.StateParser;
import ru.nsu.bashev.machine.determinate.DeterminateStateMachine;
import ru.nsu.bashev.machine.determinate.DeterminateStatesInfo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Main STATES_INFO_FILE INPUT_FILE");
            System.exit(-1);
        }

        DeterminateStatesInfo statesInfo = null;
        try {
            statesInfo = StateParser.parseDeterminateFrom(new FileReader(args[0]));
        } catch (FileNotFoundException e) {
            System.out.println(String.format("File \"%s\" not found", args[0]));
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.out.println("Bad format: " + e.getMessage());
            System.exit(1);
        }

        DeterminateStateMachine determinateStateMachine = new DeterminateStateMachine(statesInfo);
        boolean result = false;
        try {
            result = determinateStateMachine.isValidInput(new FileReader(args[1]));
        } catch (FileNotFoundException e) {
            System.out.println(String.format("File \"%s\" not found", args[0]));
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.out.println("Bad line: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        if (result) {
            System.out.println("Input valid!");
        } else {
            System.out.println("Input invalid!");
        }
    }
}
