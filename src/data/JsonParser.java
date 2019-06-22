package data;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import models.cards.AttackType;
import models.cards.Card;
import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;

public class JsonParser {
    private static YaGson yagson = new YaGson();
    private static Type cardArrayType = new TypeToken<List<Card>>() {
    }.getType();

    public static String getFileData(String filename) throws FileNotFoundException {
        return new FileReader().getFileContent(filename);
    }

    public static List<Card> getMinions() throws FileNotFoundException {
        String minionsJSON = getFileData(FileReader.MINIONS_DATA);
        ArrayList<Card> cards = yagson.fromJson(minionsJSON, cardArrayType);
        return cards;
    }

    public static List<Card> getSpells() throws FileNotFoundException {
        String minionsJSON = getFileData(FileReader.SPELLS_DATA);
        ArrayList<Card> cards = yagson.fromJson(minionsJSON, cardArrayType);
        return cards;
    }

    public static List<Card> getHeroes() throws FileNotFoundException {
        String minionsJSON = getFileData(FileReader.HEROES_DATA);
        ArrayList<Card> cards = yagson.fromJson(minionsJSON, cardArrayType);
        return cards;
    }

    public static void write(ArrayList<Card> c) {
        YaGson y = new YaGson();
        URL url = FileReader.class.getResource("minions.json");
        File file = new File(url.getPath());
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(y.toJson(c, cardArrayType));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException, JSONException {
        System.out.println(getHeroes());
    }

}