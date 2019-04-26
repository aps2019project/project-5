package data;

import models.cards.Minion;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class JsonParser {

    public static ArrayList<Minion> parseMinion() throws FileNotFoundException {
        Gson gson = new Gson();
        String minionsData = new FileReader().getFileContent(FileReader.MINIONS_DATA);
        
        return null;
    }

}