package resources;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Fog {
    private final int width;
    private final int height;
    private final Pane fog;
    private final Random RNG = new Random();

    public Fog(int width, int height, Color color) {
        this.width = width;
        this.height = height;
        this.fog = new Pane();
        Rectangle rect = new Rectangle(0, 0, width, height);
        rect.setFill(color);
        fog.getChildren().add(rect);

        for (int i = 0; i < 5; i++) {
            fog.getChildren().add(createFogElement());
        }

        fog.setEffect(new GaussianBlur((width + height) / 50));

    }

    private Ellipse createFogElement() {
        int radius = 100 + RNG.nextInt(150);
        Ellipse ellipse = new Ellipse(RNG.nextInt(width - 50) + 25, RNG.nextInt(height - 50) + 25, radius * 2, radius);
        int shade = 0xcf + RNG.nextInt(0x20);
        ellipse.setFill(Color.rgb(shade, shade, shade, 0.3));
        AnimationTimer anim = new AnimationTimer() {

            double xVel = RNG.nextDouble() * 40 - 20;
            double yVel = RNG.nextDouble() * 40 - 20;

            long lastUpdate = 0;

            @Override
            public void handle(long now) {
                new Thread(() -> {
                    if (lastUpdate > 0) {
                        double elapsedSeconds = (now - lastUpdate) / 1_000_000_000.0;
                        double x = ellipse.getCenterX();
                        double y = ellipse.getCenterY();
                        if (x + elapsedSeconds * xVel > width || x + elapsedSeconds * xVel < 0) {
                            xVel = -xVel;
                        }
                        if (y + elapsedSeconds * yVel > height || y + elapsedSeconds * yVel < 0) {
                            yVel = -yVel;
                        }
                        ellipse.setCenterX(x + elapsedSeconds * xVel);
                        ellipse.setCenterY(y + elapsedSeconds * yVel);
                    }
                    lastUpdate = now;
                }).start();
            }

        };
        anim.start();
        return ellipse;
    }

    public Node getView() {
        return fog;
    }
}
