package puyopuyo;

import puyopuyo.client.frame.Frame;
import puyopuyo.client.panel.start.StartPanel;

public class PuyoPuyo {
	public static void main(String[] args) {
		var frame = Frame.getInstance();
		frame.setVisible(true);
		frame.changePanel(StartPanel.getInstance());
	}
}