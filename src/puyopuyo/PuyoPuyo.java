package puyopuyo;

import puyopuyo.frame.Frame;
import puyopuyo.Panel.start.StartPanel;

public class PuyoPuyo {
	public static void main(String[] args) {
		var frame = Frame.getInstance();

		frame.changePanel(StartPanel.getInstance());
	}
}