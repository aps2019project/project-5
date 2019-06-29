package client.views;

import java.util.Scanner;

public class Input {
    private static Scanner scanner = new Scanner(System.in);
    private static Input instance = new Input();

    public String getCommand() {
        return scanner.nextLine().trim();
    }

    public String getCommand(String menuName) {
        System.out.print(menuName + "> ");
        return scanner.nextLine().trim();
    }

    protected Input() {}

    public static Input getInstance() {
        return instance;
    }

    public static String getString(String prefix) {
        System.out.print(prefix);
        return scanner.nextLine();
    }
}
