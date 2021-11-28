package view;

import java.util.HashMap;
import java.util.Scanner;

public class TextMenu {

    private HashMap<String, Command> commandDict;

    public TextMenu() {

        commandDict = new HashMap<>();
    }

    public void addCommand(Command c) {

        commandDict.put(c.getKey(), c);
    }

    private void printMenu() {

        for (Command comm : commandDict.values()) {

            String line = String.format("%4s : %s", comm.getKey(), comm.getDescription());
            System.out.println(line);
        }
    }

    public void start() {

        System.out.print("\033[H\033[2J");
        System.out.flush();
        Scanner scanner = new Scanner(System.in);

        while (true) {

            printMenu();
            System.out.print("\nInput the option: ");

            String key = scanner.nextLine();
            Command comm = commandDict.get(key);

            if (comm == null) {

                System.out.println("\nInvalid Option");
                continue;
            }

            comm.execute();
        }

    }
}
