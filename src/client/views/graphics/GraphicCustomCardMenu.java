package client.views.graphics;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import data.FileReader;
import javafx.event.ActionEvent;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import client.models.Shop;
import client.models.cards.AttackType;
import client.models.cards.Card;
import client.models.cards.Hero;
import client.models.cards.Minion;
import client.models.cards.spell.SpecialPowerActivateTime;
import client.models.cards.spell.Spell;
import client.views.Graphics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static client.views.Graphics.Menu.SHOP_MENU;
import static client.views.Graphics.playMusic;

public class GraphicCustomCardMenu {

    public JFXTextField nameTxt;
    public JFXTextField idTxt;
    public JFXTextField priceTxt;
    public JFXTextField manaPointTxt;
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
    public JFXTextField heroNameTxt;
    public JFXTextField heroIdTxt;
    public JFXTextField heroPriceTxt;
    public JFXTextField heroManaPointTxt;
    public JFXTextField heroPowerTxt;
    public JFXTextField heroHealthTxt;
    public JFXTextField heroRangeTxt;
    public JFXComboBox heroAttackTypeCmb;
    public JFXTextField heroAttackPointTxt;
    public JFXTextField heroCooldown;
    public JFXTextField spriteAnimationPathHeroTxt;
    public JFXTextField spriteAnimationPathMinionTxt;
    public JFXTextField spriteAnimationPathSpellTxt;
    private boolean minionFlag;
    private boolean spellFlag;
    private boolean heroFlag;
    private File heroSpriteFile;
    private File minionSpriteFile;
    private File spellSpriteFile;
    File spriteAnimationResourcePath = new File("src" + File.separator + "resources" + File.separator + "sprites");

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
            List<Card> cards = new ArrayList<>(Shop.getInstance().getCardsCollection().getMinions());
            if (type.equals("spell")) {
                url = FileReader.class.getResource("spell.json");
                cards = new ArrayList<>(Shop.getInstance().getCardsCollection().getSpells());
            }
            if (type.equals("hero")) {
                url = FileReader.class.getResource("heroes.json");
                cards = new ArrayList<>(Shop.getInstance().getCardsCollection().getHeroes());
            }
            File file = new File(url.getPath());
            FileWriter cardWriter = new FileWriter(file);
            cardWriter.write(jsonCard.toJson(cards));
            cardWriter.flush();
            cardWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics.alert("Congrats", "Card Added", "Your new card added successfully.");
    }

    public void addSpellCard(ActionEvent actionEvent) {
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

        File spriteAnimation = new File(spriteAnimationPathSpellTxt.getText());
        if (!spriteAnimation.exists()) {
            spellFlag = false;
            changeAsWrong(spriteAnimationPathSpellTxt, true);
        }
        final String regex = "(?<fileName>[\\w \\d _ -]+)\\.png";
        String fileName = spriteAnimation.getName().replaceAll("\\.\\w+", "");
        final String spritePath = spriteAnimation.getAbsolutePath().replaceAll(regex, "");
        File plistFile = new File(spritePath + fileName + ".plist");

        if (!plistFile.exists()) {
            spellFlag = false;
            changeAsWrong(spriteAnimationPathSpellTxt, true);
        }

        if (!spellFlag) {
            return;
        }

        spriteAnimation.renameTo(new File(spritePath + name + ".png"));
        plistFile.renameTo(new File(spritePath + name + ".plist"));


        Spell spell = new Spell(id, name, "", manaPoint, price);
        saveNewCard("spell", spell);

    }

    public void addMinionCard(ActionEvent actionEvent) {
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

        int price = getTextInfo(priceTxt, "minion");


        int manaPoint = getTextInfo(manaPointTxt, "minion");


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

        File spriteAnimation = new File(spriteAnimationPathMinionTxt.getText());
        if (!spriteAnimation.exists()) {
            minionFlag = false;
            changeAsWrong(spriteAnimationPathMinionTxt, true);
        }
        final String regex = "(?<fileName>[\\w \\d _ -]+)\\.png";
        String fileName = spriteAnimation.getName().replaceAll("\\.\\w+", "");
        final String spritePath = spriteAnimation.getAbsolutePath().replaceAll(regex, "");
        File plistFile = new File(spritePath + fileName + ".plist");

        if (!plistFile.exists()) {
            minionFlag = false;
            changeAsWrong(spriteAnimationPathMinionTxt, true);
        }

        if (!minionFlag) {
            return;
        }

        spriteAnimation.renameTo(new File(spritePath + name + ".png"));
        plistFile.renameTo(new File(spritePath + name + ".plist"));

        SpecialPowerActivateTime specialPowerActivateTime = SpecialPowerActivateTime.ON_ATTACK;
        AttackType attackType1 = AttackType.getAttackType(attackType);
        Minion minion = new Minion(id, name, "", manaPoint, price, health, attackPoint, attackType1, range, specialPowerActivateTime);
        saveNewCard("minion", minion);
    }

    public void addHeroCard(ActionEvent actionEvent) {
        playMusic("sfx_ui_select.m4a");
        heroFlag = true;
        String name = heroNameTxt.getText();
        if (name.equals("")) {
            System.out.println( heroNameTxt.getText());
            changeAsWrong(heroNameTxt, true);
            heroFlag = false;
        }

        int id = getTextInfo(heroIdTxt, "hero");

        int price = getTextInfo(heroPriceTxt, "hero");


        int manaPoint = getTextInfo(heroManaPointTxt, "hero");

        int attackPoint = getTextInfo(heroAttackPointTxt, "hero");

        int health = getTextInfo(heroHealthTxt, "hero");

        int range = getTextInfo(heroRangeTxt, "hero");

        int cooldown = getTextInfo(heroCooldown, "hero");

        String attackType = "";
        if (heroAttackTypeCmb.getSelectionModel().isEmpty()) {
            heroFlag = false;
            changeAsWrong(heroAttackTypeCmb, true);
        } else {
            attackType = heroAttackTypeCmb.getValue().toString();
        }



        File spriteAnimation = new File(spriteAnimationPathHeroTxt.getText());
        if (!spriteAnimation.exists()) {
            heroFlag = false;
            changeAsWrong(spriteAnimationPathHeroTxt, true);
        }
        final String regex = "(?<fileName>[\\w \\d _ -]+)\\.png";
        String fileName = spriteAnimation.getName().replaceAll("\\.\\w+", "");
        final String spritePath = spriteAnimation.getAbsolutePath().replaceAll(regex, "");
        File plistFile = new File(spritePath + fileName + ".plist");

        File logo = new File(spritePath + "HeroLogos" + File.separator + fileName + ".png");


        if (!plistFile.exists()) {
            heroFlag = false;
            changeAsWrong(spriteAnimationPathHeroTxt, true);
        }

        if (!heroFlag) {
            return;
        }

        spriteAnimation.renameTo(new File(spritePath + name + ".png"));
        plistFile.renameTo(new File(spritePath + name + ".plist"));
//        File newFile = new File(String.valueOf(Graphics.class.getResource("resources/sprites/HeroLogos/" + name + ".png")));
        logo.renameTo(new File(String.valueOf(Graphics.class.getResource("client/resources/sprites/HeroLogos/" + name + ".png"))));


        AttackType attackType1 = AttackType.getAttackType(attackType);
        Hero hero = new Hero(id, name, "", manaPoint, price, health, attackPoint, attackType1, range, cooldown);
        saveNewCard("hero", hero);
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


    public void openFileChooser(MouseEvent mouseEvent) {
        changeAsWrong((JFXTextField) mouseEvent.getSource(), false);
        String fileName = "";
        if (((JFXTextField) mouseEvent.getSource()).getPromptText().contains("hero"))
            fileName = "Hero";
        ((JFXTextField)mouseEvent.getSource()).setText(getFile(fileName).getAbsolutePath());
    }

    private File getFile(String fileName) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sprite Animation", "*" + fileName + ".png"));

        fileChooser.setInitialDirectory(spriteAnimationResourcePath);
        fileChooser.setTitle("Please select a sprite animation with its .plist file");

        File file = fileChooser.showOpenDialog(Graphics.stage);

        return file;
    }
}
