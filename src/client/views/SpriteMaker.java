package client.views;

import com.dd.plist.NSDictionary;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListFormatException;
import com.dd.plist.PropertyListParser;
import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import client.models.Action;
import client.models.MiniPicPeculiarities;
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
        filePath = "src/client/resources/sprites/" + name;
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

    public static ImageView getAndShowAnimation(ImageView imageView, String name, Action action, int cycle) {
        SpriteMaker spriteMaker = null;
        try {
            spriteMaker = new SpriteMaker(imageView, name, action, cycle);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            loadPlist(spriteMaker);
        } catch (ParserConfigurationException | IOException
                | PropertyListFormatException | SAXException | ParseException e) {
            e.printStackTrace();
        }

        Duration seconds = Duration.seconds(0.07 * spriteMaker.datas.size());
        if (action == Action.RUN) {
            seconds = Duration.seconds(0.045555 * spriteMaker.datas.size());
        }
        Animation animation = new SpriteAnimation(spriteMaker.bigImage, seconds, spriteMaker.datas);
        animation.setCycleCount(cycle);
        animation.play();
        return spriteMaker.bigImage;
    }

    public static double getAnimationTime(ImageView imageView, String name, Action action, int cycle) {
        SpriteMaker spriteMaker = null;
        try {
            spriteMaker = new SpriteMaker(imageView, name, action, cycle);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            loadPlist(spriteMaker);
        } catch (ParserConfigurationException | IOException
                | PropertyListFormatException | SAXException | ParseException e) {
            e.printStackTrace();
        }
        Duration seconds = Duration.seconds(0.07 * spriteMaker.datas.size());
        if (action == Action.RUN) {
            seconds = Duration.seconds(0.045555 * spriteMaker.datas.size());
        }
        return seconds.toMillis();
    }

}
