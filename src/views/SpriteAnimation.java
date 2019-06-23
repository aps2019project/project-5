package views;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import models.MiniPicPeculiarities;

import java.util.ArrayList;

public class SpriteAnimation extends Transition {


    private final ImageView imageView;
    private ArrayList<MiniPicPeculiarities> picPeculiarities;
    private int count;
    private int lastIndex;

    public SpriteAnimation(ImageView imageView, Duration duration, ArrayList<MiniPicPeculiarities> picPeculiarities) {
        this.picPeculiarities = picPeculiarities;
        this.imageView = imageView;
        count = picPeculiarities.size();
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        MiniPicPeculiarities peculiarities = picPeculiarities.get(index);
        imageView.setViewport(new Rectangle2D(peculiarities.x, peculiarities.y, peculiarities.width, peculiarities.height));
    }

}