package data;

import models.cards.AttackType;
import models.cards.Hero;
import models.cards.Minion;
import models.cards.spell.SpecialPowerActivateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileNotFoundException;
import java.util.*;

public class JsonParser {

    public static String getFileData(String filename) throws FileNotFoundException {
        return new FileReader().getFileContent(filename);
    }

    private static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if(json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    private static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    private static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }


    public static List<Minion> getMinions() throws FileNotFoundException, JSONException {
        JSONArray minionsJSON = new JSONArray(getFileData(FileReader.MINIONS_DATA));
        List<Object> minionsObject = toList(minionsJSON);
        ArrayList<Minion> minions = new ArrayList<>();
        int id = 1;
        for(Object minionObject : minionsObject) {
            HashMap<String, Object> minionHashMap = (HashMap<String, Object>) minionObject;
            SpecialPowerActivateTime specialPowerActivateTime = null;
            AttackType attackType = null;
            try {
                specialPowerActivateTime = SpecialPowerActivateTime.valueOf((String) minionHashMap.get("specialPowerActivate"));
            } catch (Exception ignored) {}
            try {
                attackType = AttackType.valueOf((String) minionHashMap.get("attackType"));
            } catch (Exception ignored) {}
            Minion minion = new Minion(
                    id++,
                    (String) minionHashMap.get("name"),
                    (String) minionHashMap.get("description"),
                    (int) minionHashMap.get("manaPoint"),
                    (int) minionHashMap.get("price"),
                    (int) minionHashMap.get("health"),
                    (int) minionHashMap.get("attackPoint"),
                    attackType,
                    (int) minionHashMap.get("range"),
                    specialPowerActivateTime
            );
            minions.add(minion);
        }
        return minions;
    }

    public static List<Hero> getHeroes() throws FileNotFoundException, JSONException {
        JSONArray heroesJSON = new JSONArray(getFileData(FileReader.HEROES_DATA));
        List<Object> heroesObject = toList(heroesJSON);
        ArrayList<Hero> heroes = new ArrayList<>();
        int id = 1;
        for(Object heroObject : heroesObject) {
            HashMap<String, Object> heroHashMap = (HashMap<String, Object>) heroObject;
            AttackType attackType = null;
            try {
                attackType = AttackType.valueOf((String) heroHashMap.get("attackType"));
            } catch (Exception ignored) {}
            Hero hero = new Hero(
                    id++,
                    (String) heroHashMap.get("name"),
                    (String) heroHashMap.get("description"),
                    (int) heroHashMap.get("manaPoint"),
                    (int) heroHashMap.get("price"),
                    (int) heroHashMap.get("health"),
                    (int) heroHashMap.get("attackPoint"),
                    attackType,
                    (int) heroHashMap.get("range"),
                    (int) heroHashMap.get("coolDown")

            );
            heroes.add(hero);
        }
        return heroes;
    }


    public static void main(String[] args) throws FileNotFoundException, JSONException {
        List<Hero> heroes = getHeroes();
        System.out.println(heroes.toString());
    }

}