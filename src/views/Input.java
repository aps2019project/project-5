package views;

import java.util.Scanner;

public class Input {
    private static Scanner scanner = new Scanner(System.in);

    public static String getCommand() {
        return scanner.nextLine().trim();
    }
}
