package puyopuyo;

import puyopuyo.frame.Frame;
import puyopuyo.menu.MenuPanel;

public class PuyoPuyo {
	public static void main(String[] args) {
		var frame = Frame.getInstance();

		var menuPanel = MenuPanel.getInstance();
		frame.changePanel(menuPanel);
	}
}