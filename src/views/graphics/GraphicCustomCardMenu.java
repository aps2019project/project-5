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
import models.cards.Minion;
import models.cards.spell.SpecialPowerActivateTime;
import views.Graphics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

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
    public JFXTextField maxDisTxt;
    public JFXTextField comboAbilityTxt;
    public JFXButton addBtn;
    public JFXComboBox counterAttackCmb;
    public JFXComboBox turnAttackAvCmb;
    public JFXComboBox moveAvCmb;
    public JFXComboBox attackTypeCmb;
    public JFXTabPane tabs;

    public void addCard(ActionEvent actionEvent) {
        playMusic("sfx_ui_select.m4a");
        String name = nameTxt.getText();
        boolean flag = true;

        if (name.equals("")) {
            changeAsWrong(nameTxt, true);
            flag = false;
        }

        int id = 0;
        try {
            id = Integer.parseInt(idTxt.getText());
        } catch (NumberFormatException e) {
            changeAsWrong(idTxt, true);
            flag = false;
        }

        int price = 0;
        try {
            price = Integer.parseInt(priceTxt.getText());
        } catch (NumberFormatException e) {
            changeAsWrong(priceTxt, true);
            flag = false;
        }

        int manaPoint = 0;
        try {
            manaPoint = Integer.parseInt(manaPointTxt.getText());
        } catch (NumberFormatException e) {
            changeAsWrong(manaPointTxt, true);
            flag = false;
        }

        int power = 0;
        try {
            power = Integer.parseInt(powerTxt.getText());
        } catch (NumberFormatException e) {
            changeAsWrong(powerTxt, true);
            flag = false;
        }

        int health = 0;
        try {
            health = Integer.parseInt(healthTxt.getText());
        } catch (NumberFormatException e) {
            changeAsWrong(healthTxt, true);
            flag = false;
        }

        int range = 0;
        try {
            range = Integer.parseInt(rangeTxt.getText());
        } catch (NumberFormatException e) {
            changeAsWrong(rangeTxt, true);
            flag = false;
        }

        int attackPoint = 0;
        try {
            attackPoint = Integer.parseInt(attackPointTxt.getText());
        } catch (NumberFormatException e) {
            changeAsWrong(attackPointTxt, true);
            flag = false;
        }

        int maxDistance = 0;
        try {
            maxDistance = Integer.parseInt(maxDisTxt.getText());
        } catch (NumberFormatException e) {
            changeAsWrong(maxDisTxt, true);
            flag = false;
        }

        int combo = 0;
        try {
            combo = Integer.parseInt(comboAbilityTxt.getText());
        } catch (NumberFormatException e) {
            changeAsWrong(comboAbilityTxt, true);
            flag = false;
        }

        String attackType = "";
        if (attackTypeCmb.getSelectionModel().isEmpty()) {
            flag = false;
            changeAsWrong(attackTypeCmb, true);
        } else {
            attackType = attackTypeCmb.getValue().toString();
        }

        String counterAttack;
        if (counterAttackCmb.getSelectionModel().isEmpty()) {
            flag = false;
            changeAsWrong(counterAttackCmb, true);
        } else {
            counterAttack = attackTypeCmb.getValue().toString();
        }

        String turnAttack;
        if (turnAttackAvCmb.getSelectionModel().isEmpty()) {
            flag = false;
            changeAsWrong(turnAttackAvCmb, true);
        } else {
            turnAttack = attackTypeCmb.getValue().toString();
        }

        String moveAvailale;
        if (moveAvCmb.getSelectionModel().isEmpty()) {
            flag = false;
            changeAsWrong(moveAvCmb, true);
        } else {
            moveAvailale = attackTypeCmb.getValue().toString();
        }


        if (!flag)
            return;
        SpecialPowerActivateTime specialPowerActivateTime = SpecialPowerActivateTime.ON_ATTACK;
        AttackType attackType1 = AttackType.getAttackType(attackType);
        Minion minion = new Minion(id, name, "", manaPoint, price, health, attackPoint, attackType1, range, specialPowerActivateTime);
        try {
            Shop.getInstance().getCardsCollection().addCard(minion);
            YaGsonBuilder jsonCardBuilder = new YaGsonBuilder();

            jsonCardBuilder.setPrettyPrinting();
            YaGson jsonCard = jsonCardBuilder.create();
            URL url = FileReader.class.getResource("minions.json");
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
