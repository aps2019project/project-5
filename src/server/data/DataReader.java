package server.data;

import client.data.FileReader;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import models.Account;
import client.models.cards.Card;
import models.cards.Collection;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Scanner;

public class DataReader {
    public static Type ACCOUNTS_TYPE = new TypeToken<HashMap<String, Account>>() {
    }.getType();
    public static Type CARDS_TYPE = new TypeToken<Collection>() {
    }.getType();
    private static YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();

    public static HashMap<String, Account> readAccounts() {
        HashMap<String, Account> accounts;
        String data = getFileData(Files.USER_DATA);
        accounts = yaGson.fromJson(data, ACCOUNTS_TYPE);
        return accounts;
    }

    public static Collection getShopCollection() {
        Collection shop;
        String data = getFileData(Files.CARD_DATA);
        shop = yaGson.fromJson(data, CARDS_TYPE);
        return shop;
    }

    public static String getFileData(String fileName) {
        File file = new File("src/server/data/" + fileName);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNext())
            stringBuilder.append(String.format("%s\n", scanner.nextLine()));
        return stringBuilder.toString();
    }

    public static String getFileData(Files file) {
        return getFileData(file.toString());
    }
}
