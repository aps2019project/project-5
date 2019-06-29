package server;

import models.Account;
import server.controllers.Authentication;
import server.data.DataWriter;
import server.data.Files;

public class Test {
    public static void main(String... args) {
        DataWriter.saveData(Files.USER_DATA, Authentication.users);
    }
}

