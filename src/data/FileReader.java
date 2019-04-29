package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class FileReader {
    public static final String MINIONS_DATA = "minions.json";
    public static final String HEROES_DATA = "heroes.json";

    public String getFileContent(String fileName) throws FileNotFoundException {
        URL url = getClass().getResource(fileName);
        File file = new File(url.getPath());
        Scanner scanner = new Scanner(file);
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNext())
            stringBuilder.append(String.format("%s\n", scanner.nextLine()));
        return stringBuilder.toString();
    }
}
