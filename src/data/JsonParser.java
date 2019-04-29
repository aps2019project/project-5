package data;

import models.cards.Minion;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class JsonParser {

    public static ArrayList<Minion> parseMinion() throws FileNotFoundException {
        String minionsData = new FileReader().getFileContent(FileReader.MINIONS_DATA);
        
        return null;
    }

}