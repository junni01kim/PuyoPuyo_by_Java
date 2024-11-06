package puyopuyo;

import puyopuyo.frame.Frame;
import puyopuyo.Panel.menu.StartPanel;

public class PuyoPuyo {
	public static void main(String[] args) {
		var frame = Frame.getInstance();

		var startPanel = StartPanel.getInstance();
		frame.changePanel(startPanel);
	}
}