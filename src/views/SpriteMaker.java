package views;

import com.dd.plist.NSDictionary;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListFormatException;
import com.dd.plist.PropertyListParser;
import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import models.Action;
import models.MiniPicPeculiarities;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpriteMaker {

    String name;
    String filePath;
    int cycle;
    Action action;
    File spriteFile;
    private ImageView bigImage;
    private ArrayList<MiniPicPeculiarities> datas = new ArrayList<>();

    private SpriteMaker(ImageView imageView, String name, Action action, int cycle) throws FileNotFoundException {
        this.bigImage = imageView;
        this.name = name;
        this.action = action;
        filePath = "src/resources/sprites/" + name;
        spriteFile = new File(filePath + ".plist");
        bigImage.setImage(new Image(new FileInputStream(filePath + ".png")));
        this.cycle = cycle;
    }

    private static void loadPlist(SpriteMaker spriteMaker) throws ParserConfigurationException, ParseException, SAXException, PropertyListFormatException, IOException {
        NSDictionary mainDictionary = (NSDictionary) PropertyListParser.parse(spriteMaker.spriteFile);

        NSDictionary imageDictionary = (NSDictionary) mainDictionary.objectForKey("frames");
        imageDictionary.getHashMap().forEach((key, values) -> {
            if (key.contains(spriteMaker.action.getCode())) {
                NSString cornerString = (NSString) ((NSDictionary) values).objectForKey("frame");
                Pattern numberPattern = Pattern.compile("\\d+");
                Matcher matcher = numberPattern.matcher(cornerString.toString());
                List<Integer> pecuList = new ArrayList<>();
                while (matcher.find())
                    pecuList.add(Integer.parseInt(matcher.group()));
                spriteMaker.datas.add(new MiniPicPeculiarities(pecuList.get(0), pecuList.get(1), pecuList.get(2), pecuList.get(3)));
            }
        });

    }

    public static ImageView getAndShowAnimation( ImageView imageView, String name, Action action, int cycle) {
        SpriteMaker spriteMaker = null;
        try {
            spriteMaker = new SpriteMaker(imageView, name, action, cycle);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            loadPlist(spriteMaker);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (PropertyListFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Animation animation = new SpriteAnimation(spriteMaker.bigImage, Duration.seconds(2), spriteMaker.datas);
        animation.setCycleCount(cycle);
        animation.play();
        return spriteMaker.bigImage;
    }

}
