package jFrame;

import service.TimerService;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.Timer;


public class MyJFrame {
    private static ExecutorService service;

    private static JPanel jPanel = getPanel();
    private static final JLabel label = new JLabel("00:00");

    public static JFrame startTimer() {
        JFrame frame = new javax.swing.JFrame();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Timer");


        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();

        frame.setBounds(dimension.width / 2 - 200, dimension.height / 2 - 50, 400, 200);

        jPanel.add(label);
        frame.add(jPanel);

        return frame;
    }

    private static JPanel getPanel() {
        JPanel jPanel = new JPanel();

        JButton start = new JButton("Start");
        JButton stop = new JButton("Stop");

        JTextField minute = new JTextField();
        JTextField second = new JTextField();
        minute.setColumns(5);
        second.setColumns(5);

        jPanel.add(minute);
        jPanel.add(second);

        jPanel.add(start);
        jPanel.add(stop);

        start.addActionListener(e -> {
            boolean isValid = validate(minute.getText(), second.getText());

            if (isValid) {
                startTimer(Integer.parseInt(minute.getText()),
                        Integer.parseInt(second.getText()));
                start.setEnabled(false);
            } else {
                label.setText("Input cannot be more than 60 and less than 0");
            }

        });

        stop.addActionListener(e -> {
            start.setEnabled(true);
            service.shutdownNow();
        });
        return jPanel;
    }

    private static boolean validate(String minute, String second) {
        try {
            if (Integer.parseInt(minute) < 0 || Integer.parseInt(minute) > 59 ||
                    Integer.parseInt(second) < 0 || Integer.parseInt(second) > 59) {
                return false;
            } else
                return true;

        } catch (Exception e) {
            return false;
        }
    }

    private static void startTimer(int minute, int second) {
        service = Executors.newSingleThreadExecutor();
        Timer timer = new Timer(minute, second);
        service.execute(new TimerService(timer, label));

    }

}
