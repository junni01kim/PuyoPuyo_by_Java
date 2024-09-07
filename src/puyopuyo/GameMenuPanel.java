package puyopuyo;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GameMenuPanel extends JPanel{
	private final GameFrame gameFrame;

	/**
	 * 게임 메뉴를 구성하는 화면이다.
	 * <p>
	 * 1. 기본세팅: 키보드 값을 받기위한 리스너가 존재한다. <br>
	 * 2. 시작버튼: 게임을 진행하는 GamePanel로 이동한다. <br>
	 * 3. 설명버튼: 게임 설명이 나오는 ExplainPanel로 이동한다. TODO: 제작해야 한다.
	 *
	 * @param gameFrame 다른 패널들을 참조하기 위해 gameFrame의 패널 객체를 이용한다.
	 */
	public GameMenuPanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		setLayout(null);

        JButton startGameButton = new JButton(GameImageIcon.startGameButtonImage);
        startGameButton.addActionListener(new StartGameButtonActionListener());
		startGameButton.setLocation(550, 540);
		startGameButton.setSize(150, 60);
		add(startGameButton);

        JButton explainGameButton = new JButton("ExplainGame");
        explainGameButton.addActionListener(null);
		explainGameButton.setLocation(550, 620);
		explainGameButton.setSize(150, 60);
		add(explainGameButton);
	}

	/**
	 * 패널의 백그라운드 이미지를 그리기 위한 함수 <br>
	 * 전체 화면 크기로 이미지가 배치 된다.
	 * @param graphics the <code>Graphics</code> object to protect
	 */
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		graphics.drawImage(GameImageIcon.gameMenuPanelIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
	}

	/**
	 * GamePanel로 이동하는 클릭 리스너
	 */
	private class StartGameButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton myButton = (JButton)e.getSource();
			myButton.getParent().setVisible(false);
			gameFrame.openGamePanel();
		}
	}
}
