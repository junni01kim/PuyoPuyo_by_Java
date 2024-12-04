package client;

import client.panel.start.StartPanel;
import client.frame.Frame;

public class Client {
    public static void main(String[] args) {
        var frame = Frame.getInstance();
        frame.setVisible(true);
        frame.changePanel(StartPanel.getInstance());
    }
}
