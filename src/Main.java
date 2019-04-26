import data.JsonParser;
import views.menus.AccountMenu;

public class Main {
    public static void main(String[] args) {
        JsonParser jsonParser = new JsonParser();
        System.out.println(jsonParser.minionParse().size());
        System.out.println(jsonParser.minionParse().get(0).getAttackPoint());
        new AccountMenu().handleMenu();
    }
}