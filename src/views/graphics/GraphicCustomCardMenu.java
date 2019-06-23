package views.graphics;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.skins.JFXCheckBoxOldSkin;
import data.FileReader;
import javafx.event.ActionEvent;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import models.Shop;
import models.cards.AttackType;
import models.cards.Card;
import models.cards.Minion;
import models.cards.spell.SpecialPowerActivateTime;
import models.cards.spell.Spell;
import views.Graphics;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static views.Graphics.Menu.MAIN_MENU;
import static views.Graphics.Menu.SHOP_MENU;
import static views.Graphics.playMusic;

public class GraphicCustomCardMenu {

    public JFXTextField nameTxt;
    public JFXTextField idTxt;
    public JFXTextField priceTxt;
    public JFXTextField manaPointTxt;
    public JFXTextField powerTxt;
    public JFXTextField healthTxt;
    public JFXTextField rangeTxt;
    public JFXTextField attackPointTxt;
    public JFXButton addBtn;
    public JFXComboBox attackTypeCmb;
    public JFXTabPane tabs;
    public JFXTextField spellNameTxt;
    public JFXTextField spellIdTxt;
    public JFXTextField spellPriceTxt;
    public JFXTextField spellManaPointTxt;
    public JFXTextField spellPowerTxt;
    public JFXTextField spellDescription;
    public JFXTextField spellBuff;
    private boolean minionFlag;
    private boolean spellFlag;
    private boolean heroFlag;

    public int getTextInfo(JFXTextField t, String type) {
        int number = 0;
        try {
            number = Integer.parseInt(t.getText());
        } catch (NumberFormatException e) {
            changeAsWrong(t, true);
            switch (type) {
                case "minion":
                    minionFlag = false;
                    break;

                case "hero":
                    heroFlag = false;
                    break;

                case "spell":
                    spellFlag = false;
                    break;
            }
        }
        return number;
    }

    public void saveNewCard(String type, Card card) {
        try {
            Shop.getInstance().getCardsCollection().addCard(card);
            YaGsonBuilder jsonCardBuilder = new YaGsonBuilder();

            jsonCardBuilder.setPrettyPrinting();
            YaGson jsonCard = jsonCardBuilder.create();
            URL url = FileReader.class.getResource("minions.json");
            ArrayList<Card> cards = new ArrayList<>();
            if (type.equals("spell")) {
                url = FileReader.class.getResource("spells.json");
                jsonCard.toJson(Shop.getInstance().getCardsCollection().getSpells());
            }
            if (type.equals("spell")) {
                url = FileReader.class.getResource("hero.json");
                jsonCard.toJson(Shop.getInstance().getCardsCollection().getHeroes());
            }
            File file = new File(url.getPath());
            FileWriter cardWriter = new FileWriter(file);
            cardWriter.write(jsonCard.toJson(Shop.getInstance().getCardsCollection().getMinions()));
            cardWriter.flush();
            cardWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics.alert("Congrats", "Card Added", "Your new card added successfully.");
    }

    public void spellAddCard(ActionEvent actionEvent) {
        playMusic("sfx_ui_select.m4a");
        spellFlag = true;

        String name = spellNameTxt.getText();
        if (name.equals("")) {
            changeAsWrong(spellNameTxt, true);
            spellFlag = false;
        }

        int id = 0;
        try {
            id = Integer.parseInt(spellIdTxt.getText());
        } catch (NumberFormatException e) {
            changeAsWrong(spellIdTxt, true);
            spellFlag = false;
        }

        int price = getTextInfo(spellPriceTxt, "spell");


        int manaPoint = getTextInfo(spellManaPointTxt, "spell");
        if (!spellFlag)
            return;
        Spell spell = new Spell(id, name, "", manaPoint, price);
        saveNewCard("spell", spell);

    }

    public void addCard(ActionEvent actionEvent) {
        playMusic("sfx_ui_select.m4a");
        String name = nameTxt.getText();
        minionFlag = true;
        if (name.equals("")) {
            changeAsWrong(nameTxt, true);
            minionFlag = false;
        }

        int id = 0;
        try {
            id = Integer.parseInt(idTxt.getText());
        } catch (NumberFormatException e) {
            changeAsWrong(idTxt, true);
            minionFlag = false;
        }

        int price = 0;


        int manaPoint = getTextInfo(manaPointTxt, "minion");

        int power = getTextInfo(powerTxt, "minion");

        int health = getTextInfo(healthTxt, "minion");

        int range = getTextInfo(rangeTxt, "minion");

        int attackPoint = getTextInfo(attackPointTxt, "minion");


        String attackType = "";
        if (attackTypeCmb.getSelectionModel().isEmpty()) {
            minionFlag = false;
            changeAsWrong(attackTypeCmb, true);
        } else {
            attackType = attackTypeCmb.getValue().toString();
        }
        if (!minionFlag)
            return;
        SpecialPowerActivateTime specialPowerActivateTime = SpecialPowerActivateTime.ON_ATTACK;
        AttackType attackType1 = AttackType.getAttackType(attackType);
        Minion minion = new Minion(id, name, "", manaPoint, price, health, attackPoint, attackType1, range, specialPowerActivateTime);
        saveNewCard("minion", minion);
    }


    private void changeAsWrong(JFXTextField textField, boolean isWrong) {
        addBtn.setDisable(isWrong);
        if (isWrong) {
            textField.setStyle("-jfx-focus-color: #FF0000; -jfx-unfocus-color: #FF0000;");
        } else {
            textField.setStyle("-jfx-focus-color: #F47B20; -jfx-unfocus-color: #FFC20E;");
        }
    }

    private void changeAsWrong(JFXComboBox comboBox, boolean isWrong) {
        addBtn.setDisable(isWrong);
        if (isWrong) {
            comboBox.setStyle("-jfx-focus-color: #FF0000; -jfx-unfocus-color: #FF0000;");
        } else {
            comboBox.setStyle("-jfx-focus-color: #F47B20; -jfx-unfocus-color: #FFC20E;");
        }
    }

    public void backToNormal(InputEvent keyEvent) {
        try {
            changeAsWrong((JFXComboBox) keyEvent.getSource(), false);
        } catch (Exception e) {
            changeAsWrong((JFXTextField) keyEvent.getSource(), false);
        }
    }

    public void back(MouseEvent mouseEvent) {
        playMusic("sfx_ui_select.m4a");
        Graphics.setMenu(SHOP_MENU);
    }
}
