package data;

import models.cards.AttackType;
import models.cards.Card;
import models.cards.Hero;
import models.cards.Minion;
import models.cards.buff.*;
import models.cards.spell.SpecialPowerActivateTime;
import models.cards.spell.Spell;
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


    public static List<Card> getMinions() throws FileNotFoundException, JSONException {
        JSONArray minionsJSON = new JSONArray(getFileData(FileReader.MINIONS_DATA));
        List<Object> minionsObject = toList(minionsJSON);
        ArrayList<Card> minions = new ArrayList<>();
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

    public static List<Card> getHeroes() throws FileNotFoundException, JSONException {
        JSONArray heroesJSON = new JSONArray(getFileData(FileReader.HEROES_DATA));
        List<Object> heroesObject = toList(heroesJSON);
        ArrayList<Card> heroes = new ArrayList<>();
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

    public static List<Card> getSpells() throws FileNotFoundException, JSONException {
        JSONArray spellsJSON = new JSONArray(getFileData(FileReader.SPELLS_DATA));
        List<Object> spellsObject = toList(spellsJSON);
        ArrayList<Card> spells = new ArrayList<>();
        int id = 1;
        for(Object spellObject : spellsObject) {
            HashMap<String, Object> spellHashMap = (HashMap<String, Object>) spellObject;
            AttackType attackType = null;
            try {
                attackType = AttackType.valueOf((String) spellHashMap.get("attackType"));
            } catch (Exception ignored) {}
            Spell spell = new Spell(
                    id++,
                    (String) spellHashMap.get("name"),
                    (String) spellHashMap.get("description"),
                    (int) spellHashMap.get("manaPoint"),
                    (int) spellHashMap.get("price")
            );
            List<Object> buffs = (List<Object>) spellHashMap.get("effect");
            for(Object buffObject : buffs) {
                HashMap<String, Object> buffHashMap = (HashMap<String, Object>) buffObject;
                Buff buff = new Buff() {
                    @Override
                    public void buffEffect(Card card) {}
                };

                if(((String) buffHashMap.get("name")).equalsIgnoreCase("DisarmBuff")) {
                    buff = new DisarmBuff(
                            (int) buffHashMap.get("maxActivateTime"),
                            (boolean) buffHashMap.get("isContinues")
                    );
                } else if(((String) buffHashMap.get("name")).equalsIgnoreCase("HolyBuff")) {
                    buff = new HolyBuff(
                            (int) buffHashMap.get("healthPoint"),
                            (int) buffHashMap.get("maxActivateTime"),
                            (boolean) buffHashMap.get("isContinues")
                    );
                } else if(((String) buffHashMap.get("name")).equalsIgnoreCase("PoisonBuff")) {
                    buff = new PoisonBuff(
                            (int) buffHashMap.get("maxActivateTime"),
                            (int) buffHashMap.get("healthPoint"),
                            (boolean) buffHashMap.get("isContinues")
                    );
                } else if(((String) buffHashMap.get("name")).equalsIgnoreCase("PowerBuff")) {
                    buff = new PowerBuff(
                            (int) buffHashMap.get("powerPoint"),
                            (int) buffHashMap.get("maxActivateTime"),
                            (boolean) buffHashMap.get("isContinues")
                    );
                } else if(((String) buffHashMap.get("name")).equalsIgnoreCase("StunBuff")) {
                    buff = new StunBuff(
                            (int) buffHashMap.get("maxActivateTime"),
                            (boolean) buffHashMap.get("isContinues")
                    );
                } else if(((String) buffHashMap.get("name")).equalsIgnoreCase("WeaknessBuff")) {
                    buff = new WeaknessBuff(
                            (int) buffHashMap.get("powerPoint"),
                            (int) buffHashMap.get("maxActivateTime"),
                            (boolean) buffHashMap.get("isContinues")
                    );
                }
                spell.getBuffs().add(buff);
                // TODO: Add buff to spell's buffs
            }
            spells.add(spell);
        }
        return spells;
    }

    public static void main(String[] args) throws FileNotFoundException, JSONException {
        List<Card> heroes = getHeroes();
        System.out.println(heroes.toString());
    }

}