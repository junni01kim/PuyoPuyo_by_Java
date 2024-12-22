package puyopuyo.client;

import puyopuyo.client.panel.start.StartPanel;
import puyopuyo.client.frame.Frame;

public class Client {
    public static void main(String[] args) {
        var frame = Frame.getInstance();
        frame.setVisible(true);
        frame.changePanel(StartPanel.getInstance());
    }
}
