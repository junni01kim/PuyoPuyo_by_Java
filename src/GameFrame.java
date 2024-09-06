
import java.awt.BorderLayout;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	private GamePanel gamePanel = null;
	private GameMenuPanel gameMenuPanel = null;

	/**
	 * GamePanel을 구성하기 위한 기본적인 아이템 세팅 <br>
	 * 1. GamePanel을 생성한다. <br>
	 * 2. GamePanel을 배치한다. <br>
	 * 3. GamePanel을 보이게 한다.
	 */
	public void openGamePanel() {
		gamePanel = new GamePanel(this);
		getContentPane().add(gamePanel, BorderLayout.CENTER);
		gamePanel.setVisible(true);
		gamePanel.requestFocus();
	}

	/**
	 * GamePanel을 종료한다.(보이지 않게 한다)
	 */
	public void closeGamePanel() {
		gamePanel.setVisible(true);
	}

	/**
	 * GameMenuPanel을 구성하기 위한 기본적인 아이템 세팅 <br>
	 * 1. GameMenuPanel을 생성한다. <br>
	 * 2. GameMunuPanel을 배치한다. <br>
	 * 3. GameManuPanel을 보이게 한다.
	 */
	public void openGameMenuPanel() {
		gameMenuPanel = new GameMenuPanel(this);
		getContentPane().add(gameMenuPanel, BorderLayout.CENTER);
		gameMenuPanel.setVisible(true);
	}

	/**
	 * GamePanel을 제거한다.
	 */
	public void closeGameMenuPanel() {
		getContentPane().remove(gameMenuPanel);
	}

	/**
	 * 화면을 구성하는 유일한 프레임이다. <br>
	 * 게임 시작화면은 GameMenuPanel로 구성된다.
	 */
	public GameFrame() {
		setTitle("뿌요뿌요");
		setSize(1280, 870);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		openGameMenuPanel();

		setVisible(true);
	}
}
