package server.data;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class DataWriter {
    public static YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();

    public static void saveData(String fileName, Object data) {

        String jsonData = yaGson.toJson(data);
        try {
            File file = new File("src/server/data/" + fileName);
            FileOutputStream outputStream = new FileOutputStream(file);
            PrintWriter out = new PrintWriter(outputStream);
            out.print(jsonData);
            out.flush();
            out.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveData(Files file, Object data) {
        saveData(file.toString(), data);
    }
}
