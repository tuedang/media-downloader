package com.media.browser;

import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        String appUrl = "http://localhost:8080";
        if (java.awt.Desktop.getDesktop() != null) {
            JFrame frame = new JFrame();
            JPanel panel = new JPanel();
            final JTextField urlField = new JTextField(appUrl);
            JButton webButton = new JButton("Launch download manager");
            webButton.addActionListener(e -> BareBonesBrowserLaunch.openURL(urlField.getText().trim()));
            frame.setTitle("Musicdownloader Browser Launch");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            panel.add(new JLabel("URL:"));
            panel.add(urlField);
            panel.add(webButton);
            frame.getContentPane().add(panel);
            frame.pack();
            frame.setVisible(true);
        } else {
            BareBonesBrowserLaunch.openURL(appUrl);
        }

    }

}
