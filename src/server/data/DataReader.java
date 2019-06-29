package server.data;

import client.data.FileReader;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import models.Account;
import models.cards.Card;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Scanner;

public class DataReader {
    public static Type ACCOUNTS_TYPE = new TypeToken<HashMap<String, Account>>(){}.getType();
    public static Type CARDS_TYPE = new TypeToken<HashMap<Card, Integer>>(){}.getType();
    public static YaGson yaGson = new YaGson();

    public static HashMap<String, Account> readAccounts() {
        HashMap<String, Account> accounts = null;
        String data = getFileData(Files.USER_DATA);
        accounts = yaGson.fromJson(data, ACCOUNTS_TYPE);
        return accounts;
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
