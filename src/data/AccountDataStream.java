package data;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import models.Account;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountDataStream {
    private static Type accountsArray = new TypeToken<List<Account>>() {}.getType();
    private static URL url = AccountDataStream.class.getResource("accounts.json");
    private static File file = new File(url.getPath());

    public static void saveAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        Account.getAccounts().forEach((name, account) -> accounts.add(account));
        try {
            YaGson saveAccounts = new YaGson();
            FileWriter accountWriter = new FileWriter(file);
            accountWriter.write(saveAccounts.toJson(accounts));
            accountWriter.flush();
            accountWriter.close();
        } catch (IOException e) {
        }
    }

    public static HashMap<String, Account> loadAccounts() {
        HashMap<String, Account> result = new HashMap<>();
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            YaGson accountLoader = new YaGson();
            java.io.FileReader accountReader = new java.io.FileReader(file);
            accounts = accountLoader.fromJson(accountReader, accountsArray);
            accountReader.close();
        } catch (IOException e) {
            System.out.println("loading is not supported");
        }
        accounts.forEach(account -> result.put(account.getUsername(), account));
        return result;

    }
}
