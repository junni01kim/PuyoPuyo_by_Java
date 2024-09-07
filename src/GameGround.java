
import java.awt.Color;

import javax.swing.JPanel;

public class GameGround extends JPanel {
	GamePanel gamePanel;

	/**
	 * 조작할 Puyo
	 */
	Puyo leftControlPuyo = new Puyo(0);
	Puyo rightControlPuyo = new Puyo(0);

	/** gamePanel getter */
	public GamePanel getGamePanel() { return gamePanel; }

	/** getter */
	Puyo getPuyo1() { return leftControlPuyo; }
	Puyo getPuyo2() { return rightControlPuyo; }

	/**
	 * 게임 보드를 구성하는 화면이다.
	 * <p>
	 * 1. 기본세팅: 키보드 값을 받기위한 리스너가 존재한다. <br>
	 * 2. 플레이어가 조작할 뿌요들을 구성한다. <br>
	 *
	 * @param gamePanel 다른 패널들을 참조하기 위해 gamePanel의 패널 객체를 이용한다.
	 */
	GameGround(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		setBackground(Color.CYAN);
		setLayout(null);
		setSize(400,750);
		
		add(leftControlPuyo);
		add(rightControlPuyo);
	}
}