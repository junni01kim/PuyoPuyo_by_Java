package puyopuyo.gamepanel;

import puyopuyo.*;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	/** 타 패널들을 참조하기 위함 */
	private final GameFrame gameFrame;

	/** 1P 게임 보드 */
	private final GameGround gameGround1P = new GameGround(this);
	/** 2P 게임 보드 */
	private final GameGround gameGround2P = new GameGround(this);

	/** 게임 라운드 진행 스레드 */
	private RoundThread roundThread = null;

	/** 점수 패널 */
	private final ScorePanel scorePanel = new ScorePanel();

	/** getter */
	public ScorePanel getScorePanel() {return scorePanel;}
	public RoundThread getRoundThread() {return roundThread;}
	public GameFrame getGameFrame() {return gameFrame;}

	/**
	 * 게임을 진행하는 화면이다.
	 * <p>
	 * 1. 기본세팅: 키보드 값을 받기위한 리스너가 존재한다. <br>
	 * 2. 라운드를 진행하는 스레드가 존재한다. <br>
	 * 3. TODO: 지금 패널 배치를 수동으로 지정해두었는데, 화면을 자유롭게 조정할 수 있도록 배치관리자를 주성할 것 <br>
	 * 4. 실제 게임이 진행될 화면 1p, 2p를 구성한다. <br>
	 * 5. 게임의 점수가 표시될 scorePanel을 생성한다. <br>
	 * @param gameFrame 다른 패널들을 참조하기 위해 gameFrame의 패널 객체를 이용한다.
	 */
	public GamePanel(GameFrame gameFrame) {
		addKeyListener(new ControlPuyoKeyListener());
		setLayout(null);

		this.gameFrame = gameFrame;
		roundThread = new RoundThread(this, gameGround1P, gameGround2P, scorePanel);

		gameGround1P.setLocation(50,60);
		add(gameGround1P);
		
		gameGround2P.setLocation(830,60);
		add(gameGround2P);
		
		scorePanel.setLocation(490,60);
		add(scorePanel);
		
		roundThread.start();

		/*
		 * 해당 패널의 키보드 값을 받도록 설정.
		 * 보통은 따로하나, 동시에 1p, 2p의 키보드 값을 받게하기 위함
		 */
		setFocusable(true);
	}

	/** GamePanel의 키 입력 로직 */
	// TODO: 개인적으로 굳이 픽셀 단위로 하는 것 보다 인덱스 단위로 하는게 더 편할거 같긴한데, 나중에 기회되면 바꿔보기
	private class ControlPuyoKeyListener extends KeyAdapter {
		synchronized public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				/** Player1에 대한 키입력 */
				case KeyEvent.VK_W:
					//두 뿌요의 위치에 따라서 뿌요를 회전시킨다.
					// 경우1. 좌우 배치 - (뿌요1)(뿌요2)
					if(gameGround1P.getPuyo1().getX()<=gameGround1P.getPuyo2().getX()&&gameGround1P.getPuyo1().getY()==gameGround1P.getPuyo2().getY()) {
						// 예외처리: 뿌요가 바닥에서 회전하여 맵을 뚫고 나가는 경우
						if(gameGround1P.getPuyo1().PixelYToindex()==11
								// 예외처리: 뿌요가 다른 뿌요 위에서 회전하여 다른 뿌요와 겹치는 경우
								||(gameGround1P.getPuyo1().PixelYToindex()<11&&roundThread.getPlayerThread1P().getPuyoMap()[gameGround1P.getPuyo1().PixelXToindex()][gameGround1P.getPuyo1().PixelYToindex()+1]!=null)) {
							gameGround1P.getPuyo1().setLocation(gameGround1P.getPuyo1().getX(), gameGround1P.getPuyo1().getY()-60);
							gameGround1P.getPuyo2().setLocation(gameGround1P.getPuyo1().getX(), gameGround1P.getPuyo1().getY()+60);
						}
						gameGround1P.getPuyo2().setLocation(gameGround1P.getPuyo1().getX(), gameGround1P.getPuyo1().getY()+60);
						break;
					}
					// 경우2. 좌우 배치 - (뿌요2)(뿌요1)
					if(gameGround1P.getPuyo1().getX()>gameGround1P.getPuyo2().getX()&&gameGround1P.getPuyo1().getY()==gameGround1P.getPuyo2().getY()) {
						gameGround1P.getPuyo2().setLocation(gameGround1P.getPuyo1().getX(), gameGround1P.getPuyo1().getY()-60);
						break;
					}
					// 경우3. 상하 배치 - (뿌요1)(뿌요2)
					if(gameGround1P.getPuyo1().getX()==gameGround1P.getPuyo2().getX()&&gameGround1P.getPuyo1().getY()<=gameGround1P.getPuyo2().getY()) {
						// 예외처리: 뿌요가 벽에서 붙은 채 회전하여 맵을 뚫고 나가는 경우
						if(gameGround1P.getPuyo1().PixelXToindex()==0
								// 예외처리: 뿌요가 다른 뿌요 옆에서 회전하여 다른 뿌요와 겹치는 경우
								||(gameGround1P.getPuyo1().PixelXToindex()>0&&roundThread.getPlayerThread1P().getPuyoMap()[gameGround1P.getPuyo1().PixelXToindex()-1][gameGround1P.getPuyo1().PixelYToindex()]!=null)) {
							// 예외처리: 회전하려는 장소에 이미 뿌요가 막혀 있는 경우
							if(roundThread.getPlayerThread1P().getPuyoMap()[gameGround1P.getPuyo1().PixelXToindex()+1][gameGround1P.getPuyo1().PixelYToindex()]!=null)
								break;
							gameGround1P.getPuyo1().setLocation(gameGround1P.getPuyo1().getX()+60, gameGround1P.getPuyo1().getY());
							gameGround1P.getPuyo2().setLocation(gameGround1P.getPuyo1().getX()-60, gameGround1P.getPuyo1().getY());
						}
						gameGround1P.getPuyo2().setLocation(gameGround1P.getPuyo1().getX()-60, gameGround1P.getPuyo1().getY());
						break;
					}
					// 경우4. 상하 배치 - (뿌요2)(뿌요1)
					if(gameGround1P.getPuyo1().getX()==gameGround1P.getPuyo2().getX()&&gameGround1P.getPuyo1().getY()>gameGround1P.getPuyo2().getY()) {
						// 예외처리: 뿌요가 벽에서 붙은 채 회전하여 맵을 뚫고 나가는 경우
						if(gameGround1P.getPuyo1().PixelXToindex()==5
								// 예외처리: 뿌요가 다른 뿌요 옆에서 회전하여 다른 뿌요와 겹치는 경우
								||(gameGround1P.getPuyo1().PixelXToindex()<5&&roundThread.getPlayerThread1P().getPuyoMap()[gameGround1P.getPuyo1().PixelXToindex()+1][gameGround1P.getPuyo1().PixelYToindex()]!=null)) {
							// 예외처리: 회전하려는 장소에 이미 뿌요가 막혀 있는 경우
							if(roundThread.getPlayerThread1P().getPuyoMap()[gameGround1P.getPuyo1().PixelXToindex()-1][gameGround1P.getPuyo1().PixelYToindex()]!=null)
								break;

							gameGround1P.getPuyo1().setLocation(gameGround1P.getPuyo1().getX()-60, gameGround1P.getPuyo1().getY());
							gameGround1P.getPuyo2().setLocation(gameGround1P.getPuyo1().getX()+60, gameGround1P.getPuyo1().getY());
						}
						gameGround1P.getPuyo2().setLocation(gameGround1P.getPuyo1().getX()+60, gameGround1P.getPuyo1().getY());
						break;
					}
					break;
				case KeyEvent.VK_S:
					roundThread.getPlayerThread1P().dropPuyo();
					break;
				case KeyEvent.VK_A:
					//예외처리: 왼쪽에 벽이 있는데 좌측키를 누르는 경우
					if(gameGround1P.getPuyo1().PixelXToindex()<=0||gameGround1P.getPuyo2().PixelXToindex()<=0)
						break;
					//예외처리: 왼쪽에 블록이 있는데 좌측키를 누르는 경우
					if(roundThread.getPlayerThread1P().getPuyoMap()[gameGround1P.getPuyo1().PixelXToindex()-1][gameGround1P.getPuyo1().PixelYToindex()]!=null
							||roundThread.getPlayerThread1P().getPuyoMap()[gameGround1P.getPuyo2().PixelXToindex()-1][gameGround1P.getPuyo2().PixelYToindex()]!=null)
						break;

					gameGround1P.getPuyo1().setLocation(gameGround1P.getPuyo1().getX()-60,gameGround1P.getPuyo1().getY());
					gameGround1P.getPuyo2().setLocation(gameGround1P.getPuyo2().getX()-60,gameGround1P.getPuyo2().getY());
					break;
				case KeyEvent.VK_D:
					//예외처리: 오른쪽에 블록 혹은 벽이 있는데 우측키를 누르는 경우
					if(gameGround1P.getPuyo1().PixelXToindex()>=5||gameGround1P.getPuyo2().PixelXToindex()>=5)
						break;
					//예외처리: 오른쪽에 블록이 있는데 우측키를 누르는 경우
					if(roundThread.getPlayerThread1P().getPuyoMap()[gameGround1P.getPuyo1().PixelXToindex()+1][gameGround1P.getPuyo1().PixelYToindex()]!=null
							||roundThread.getPlayerThread1P().getPuyoMap()[gameGround1P.getPuyo2().PixelXToindex()+1][gameGround1P.getPuyo2().PixelYToindex()]!=null)
						break;
					gameGround1P.getPuyo1().setLocation(gameGround1P.getPuyo1().getX()+60,gameGround1P.getPuyo1().getY());
					gameGround1P.getPuyo2().setLocation(gameGround1P.getPuyo2().getX()+60,gameGround1P.getPuyo2().getY());
					break;
				/** Player2에 대한 키입력 */
				case KeyEvent.VK_UP:
					//두 뿌요의 위치에 따라서 뿌요를 회전시킨다.
					// 경우1. 좌우 배치 - (뿌요1)(뿌요2)
					if(gameGround2P.getPuyo1().getX()<=gameGround2P.getPuyo2().getX()&&gameGround2P.getPuyo1().getY()==gameGround2P.getPuyo2().getY()) {
						// 예외처리: 뿌요가 바닥에서 회전하여 맵을 뚫고 나가는 경우
						if(gameGround2P.getPuyo1().PixelYToindex()==11
								// 예외처리: 뿌요가 다른 뿌요 위에서 회전하여 다른 뿌요와 겹치는 경우
								||(gameGround2P.getPuyo1().PixelYToindex()<11&&roundThread.getPlayerThread2P().getPuyoMap()[gameGround2P.getPuyo1().PixelXToindex()][gameGround2P.getPuyo1().PixelYToindex()+1]!=null)) {
							gameGround2P.getPuyo1().setLocation(gameGround2P.getPuyo1().getX(), gameGround2P.getPuyo1().getY()-60);
							gameGround2P.getPuyo2().setLocation(gameGround2P.getPuyo1().getX(), gameGround2P.getPuyo1().getY()+60);
						}
						gameGround2P.getPuyo2().setLocation(gameGround2P.getPuyo1().getX(), gameGround2P.getPuyo1().getY()+60);
						break;
					}
					// 경우2. 좌우 배치 - (뿌요2)(뿌요1)
					if(gameGround2P.getPuyo1().getX()>gameGround2P.getPuyo2().getX()&&gameGround2P.getPuyo1().getY()==gameGround2P.getPuyo2().getY()) {
						gameGround2P.getPuyo2().setLocation(gameGround2P.getPuyo1().getX(), gameGround2P.getPuyo1().getY()-60);
						break;
					}
					// 경우3. 상하 배치 - (뿌요1)(뿌요2)
					if(gameGround2P.getPuyo1().getX()==gameGround2P.getPuyo2().getX()&&gameGround2P.getPuyo1().getY()<=gameGround2P.getPuyo2().getY()) {
						// 예외처리: 뿌요가 벽에서 붙은 채 회전하여 맵을 뚫고 나가는 경우
						if(gameGround2P.getPuyo1().PixelXToindex()==0
								// 예외처리: 뿌요가 다른 뿌요 옆에서 회전하여 다른 뿌요와 겹치는 경우
								||(gameGround2P.getPuyo1().PixelXToindex()>0&&roundThread.getPlayerThread2P().getPuyoMap()[gameGround2P.getPuyo1().PixelXToindex()-1][gameGround2P.getPuyo1().PixelYToindex()]!=null)) {
							// 예외처리: 회전하려는 장소에 이미 뿌요가 막혀 있는 경우
							if(roundThread.getPlayerThread2P().getPuyoMap()[gameGround2P.getPuyo1().PixelXToindex()+1][gameGround2P.getPuyo1().PixelYToindex()]!=null)
								break;
							gameGround2P.getPuyo1().setLocation(gameGround2P.getPuyo1().getX()+60, gameGround2P.getPuyo1().getY());
							gameGround2P.getPuyo2().setLocation(gameGround2P.getPuyo1().getX()-60, gameGround2P.getPuyo1().getY());
						}
						gameGround2P.getPuyo2().setLocation(gameGround2P.getPuyo1().getX()-60, gameGround2P.getPuyo1().getY());
						break;
					}
					// 경우4. 상하 배치 - (뿌요2)(뿌요1)
					if(gameGround2P.getPuyo1().getX()==gameGround2P.getPuyo2().getX()&&gameGround2P.getPuyo1().getY()>gameGround2P.getPuyo2().getY()) {
						// 예외처리: 뿌요가 벽에서 붙은 채 회전하여 맵을 뚫고 나가는 경우
						if(gameGround2P.getPuyo1().PixelXToindex()==5
								// 예외처리: 뿌요가 다른 뿌요 옆에서 회전하여 다른 뿌요와 겹치는 경우
								||(gameGround2P.getPuyo1().PixelXToindex()<5&&roundThread.getPlayerThread2P().getPuyoMap()[gameGround2P.getPuyo1().PixelXToindex()+1][gameGround2P.getPuyo1().PixelYToindex()]!=null)) {
							// 예외처리: 회전하려는 장소에 이미 뿌요가 막혀 있는 경우
							if(roundThread.getPlayerThread2P().getPuyoMap()[gameGround2P.getPuyo1().PixelXToindex()-1][gameGround2P.getPuyo1().PixelYToindex()]!=null)
								break;
							gameGround2P.getPuyo1().setLocation(gameGround2P.getPuyo1().getX()-60, gameGround2P.getPuyo1().getY());
							gameGround2P.getPuyo2().setLocation(gameGround2P.getPuyo1().getX()+60, gameGround2P.getPuyo1().getY());
						}
						gameGround2P.getPuyo2().setLocation(gameGround2P.getPuyo1().getX()+60, gameGround2P.getPuyo1().getY());
						break;
					}
					break;
				case KeyEvent.VK_DOWN:
					roundThread.getPlayerThread2P().dropPuyo();
					break;
				case KeyEvent.VK_LEFT:
					//예외처리: 왼쪽에 블록 혹은 벽이 있는데 좌측키를 누르는 경우
					if(gameGround2P.getPuyo1().PixelXToindex()<=0||gameGround2P.getPuyo2().PixelXToindex()<=0)
						break;
					//예외처리: 왼쪽에 블록이 있는데 좌측키를 누르는 경우
					if(roundThread.getPlayerThread2P().getPuyoMap()[gameGround2P.getPuyo1().PixelXToindex()-1][gameGround2P.getPuyo1().PixelYToindex()]!=null
							||roundThread.getPlayerThread2P().getPuyoMap()[gameGround2P.getPuyo2().PixelXToindex()-1][gameGround2P.getPuyo2().PixelYToindex()]!=null)
						break;

					gameGround2P.getPuyo1().setLocation(gameGround2P.getPuyo1().getX()-60,gameGround2P.getPuyo1().getY());
					gameGround2P.getPuyo2().setLocation(gameGround2P.getPuyo2().getX()-60,gameGround2P.getPuyo2().getY());
					break;
				case KeyEvent.VK_RIGHT:
					//예외처리: 오른쪽에 블록 혹은 벽이 있는데 우측키를 누르는 경우
					if(gameGround2P.getPuyo1().PixelXToindex()>=5||gameGround2P.getPuyo2().PixelXToindex()>=5)
						break;
					//예외처리: 오른쪽에 블록이 있는데 우측키를 누르는 경우
					if(roundThread.getPlayerThread2P().getPuyoMap()[gameGround2P.getPuyo1().PixelXToindex()+1][gameGround2P.getPuyo1().PixelYToindex()]!=null
							||roundThread.getPlayerThread2P().getPuyoMap()[gameGround2P.getPuyo2().PixelXToindex()+1][gameGround2P.getPuyo2().PixelYToindex()]!=null)
						break;

					gameGround2P.getPuyo1().setLocation(gameGround2P.getPuyo1().getX()+60,gameGround2P.getPuyo1().getY());
					gameGround2P.getPuyo2().setLocation(gameGround2P.getPuyo2().getX()+60,gameGround2P.getPuyo2().getY());
					break;
				}
		}
	}

	/**
	 * 패널의 백그라운드 이미지를 그리기 위한 함수 <br>
	 * 전체 화면 크기로 이미지가 배치 된다.
	 * @param graphics the <code>Graphics</code> object to protect
	 */
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		graphics.drawImage(GameImageIcon.gamePanelIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
