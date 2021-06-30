package service;


import model.Timer;

import javax.swing.*;

public class TimerService implements Runnable {
    private final Timer timer;
    private JLabel jLabel;

    public TimerService(Timer timer, JLabel jLabel) {
        this.timer = timer;
        this.jLabel = jLabel;
    }

    private static String print(int minute, int second) {

        StringBuilder stringBuilder = new StringBuilder();
        if (minute < 10) {
            stringBuilder.append("0").append(minute).append(":");
        } else {
            stringBuilder.append(minute).append(":");
        }
        if (second < 10) {
            stringBuilder.append("0").append(second);
        } else {
            stringBuilder.append(second);
        }
        return stringBuilder.toString();
    }

    @Override
    public void run() {
        int minute = timer.getMinute();
        int second = timer.getSecond();
        while (minute != 0 || second != 0) {
            if (second == 0) {
                if (minute == 0) {
                    minute = 59;
                } else {
                    minute--;
                }
                second = 59;
            } else {
                second--;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                minute = 0;
                second = 0;
            }
            jLabel.setText(print(minute, second));
        }
    }
}