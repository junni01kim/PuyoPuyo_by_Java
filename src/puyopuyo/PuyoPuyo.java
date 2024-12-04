package puyopuyo;

import client.frame.Frame;
import client.panel.start.StartPanel;

public class PuyoPuyo {
	public static void main(String[] args) {
		var frame = Frame.getInstance();
		frame.setVisible(true);
		frame.changePanel(StartPanel.getInstance());
	}
}