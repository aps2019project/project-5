package server;

import server.controllers.Authentication;
import server.data.DataWriter;
import server.data.Files;

public class Test {
    public static void main(String... args) {
        DataWriter.saveData(Files.CARD_DATA, Authentication.users);
    }
}

