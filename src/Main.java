import com.google.gson.Gson;
import data.JsonParser;
import models.cards.Minion;
import views.menus.AccountMenu;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Minion> minions = JsonParser.parseMinion();
        System.out.println(minions.get(0));
        new AccountMenu().handleMenu();
    }
}