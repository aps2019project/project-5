package server.data;

import com.gilecode.yagson.YaGson;

public class DataWriter {
    public static YaGson yaGson = new YaGson();

    public static void saveData(String FileName, Object data) {
        System.out.println(yaGson.toJson(data));
    }

    public static void saveData(Files file, Object data) {
        saveData(file.toString(), data);
    }
}
