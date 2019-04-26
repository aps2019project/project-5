import data.FileReader;
import views.menus.AccountMenu;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(new FileReader().getFileContent(FileReader.MINIONS_DATA));
        new AccountMenu().handleMenu();
    }
}