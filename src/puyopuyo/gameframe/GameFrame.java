package puyopuyo.gameframe;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	private final GameFrameService gameFrameService = new GameFrameService(this);

	/**
	 * 화면을 구성하는 유일한 프레임이다. <br>
	 * 게임 시작화면은 GameMenuPanel로 구성된다.
	 */
	public GameFrame() {

		setTitle("뿌요뿌요");
		setSize(1280, 870);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gameFrameService.openGameMenuPanel();

		setVisible(true);
	}
}
