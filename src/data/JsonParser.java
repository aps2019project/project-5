package data;

import com.gilecode.yagson.com.google.gson.Gson;
import com.gilecode.yagson.com.google.gson.JsonArray;
import com.gilecode.yagson.com.google.gson.JsonObject;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import com.gilecode.yagson.com.google.gson.stream.JsonReader;
import models.cards.Minion;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.List;

public class JsonParser {
    private String fileAddress;
    private final Type minionType = new TypeToken<List<Minion>>() {
    }.getType();

    public List<Minion> minionParse() {
        fileAddress = "src\\data\\minions.json";
//        File file=new File("src\\data\\minions.json");
//        Reader reader=null;
//        try {
//            reader=new FileReader("minions.json");
//            reader.read();
//        }catch (Exception FileNotFoundException){
//            System.out.println("gbg");
//        }
//
//        Gson gson = new Gson();
        try {
//            JsonReader jsonReader= new JsonReader(new FileReader(fileAddress));
            Object object=new JSONParser().parse(new FileReader("minions.java"));
            JsonArray jsonArray=(JsonArray)object;
            System.out.println(jsonArray.size());
////            reader.beginObject();
////            List<Minion> minions = gson.fromJson(jsonReader, minionType); // contains the whole reviews list
//            Minion minion=gson.fromJson(jsonReader,minionType);
////            return minions;
        } catch (Exception FileNotFoundException) {
            System.out.println("d");
        }
        return null;
    }
}

