package data;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import models.cards.AttackType;
import models.cards.Card;
import models.cards.Hero;
import models.cards.Minion;
import models.cards.buff.*;
import models.cards.spell.SpecialPowerActivateTime;
import models.cards.spell.Spell;
import models.cards.spell.TargetType;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.*;

public class JsonParser {
    private static YaGson yagson = new YaGson();
    private static Type cardArrayType = new TypeToken<List<Card>>(){}.getType();

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

    public static void main(String[] args) throws FileNotFoundException, JSONException {
        System.out.println(getHeroes());
    }

}