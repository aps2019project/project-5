package client.models;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;

import javax.swing.*;

import java.util.function.Consumer;


public class Timer extends Thread {
    private int eachTurnTime;
    private Label timer;
    private Runnable runnable;

    public Timer(int eachTurnTime, Label timer, Runnable runnable) {
        super();
        this.eachTurnTime = eachTurnTime;
        this.timer = timer;
        this.runnable = runnable;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        long timer = 0, lastT = 0;
        while (timer < eachTurnTime) {
            timer = (System.currentTimeMillis() - start) / 1000;
            if ( timer > lastT) {
                lastT = timer;
                final long finalTimer = timer;
                Platform.runLater(() -> this.timer.setText(getTimeString(finalTimer)));
            }
        }
        runnable.run();
    }

    private String getTimeString(long timer) {
        timer = eachTurnTime - timer;
        long min = timer / 60;
        long sec = timer % 60;
        return String.format("%02d : %02d", min, sec);
    }


}
