package views;

import java.util.Scanner;

public class Input {
    private static Scanner scanner = new Scanner(System.in);

    public static String getCommand() {
        return scanner.nextLine().trim();
    }

    public static String getCommand(String menuName) {
        System.out.print(String.format("%s> ", menuName));
        return scanner.nextLine().trim();
    }

    public static String getString(String prefix) {
        System.out.print(prefix);
        return scanner.nextLine();
    }
}
