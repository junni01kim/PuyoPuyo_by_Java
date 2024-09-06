
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class GamePanel extends JPanel {
	
	private GameFrame gameFrame = null;
	// 1P 게임 보드
	private GameGround gameGround1P = new GameGround(this);

	// 2P 게임 보드
	private GameGround gameGround2P = new GameGround(this);


	// 게임 라운드 진행 스레드
	private RoundThread roundThread = null;

	// 점수 패널
	private ScorePanel scorePanel = new ScorePanel(gameGround1P, gameGround2P, roundThread);

	// 화면 분할 패널
	private JSplitPane splitPanel1P = new JSplitPane();
	private JSplitPane splitPanel2P = new JSplitPane();
	
	public ScorePanel getScorePanel() {return scorePanel;}
	public GameGround getGameGround1P() {return gameGround1P;}
	public GameGround getGameGround2P() {return gameGround2P;}
	public RoundThread getRoundThread() {return roundThread;}
	public GameFrame getGameFrame() {return gameFrame;}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(GameImageIcon.gamePanelIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
	public GamePanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		roundThread = new RoundThread(this, gameGround1P, gameGround2P, scorePanel);
		
		setBackground(Color.RED);
		addKeyListener(new ControlPuyoKeyListener());
		setLayout(null);
		gameGround1P.setLocation(50,60);
		add(gameGround1P);
		
		gameGround2P.setLocation(830,60);
		add(gameGround2P);
		
		scorePanel.setLocation(490,60);
		add(scorePanel);
		
		roundThread.start();

		// 각 플레이어의 move() Key 값을 동시에 받기 위해서
		setFocusable(true);
	}

	// 화면을 분할해준다.
	private void splitPanel() {
		// 1차 1P 패널 분리
		splitPanel1P.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPanel1P.setDividerLocation(450);
		splitPanel1P.setDividerSize(0);
		add(splitPanel1P);

		// 2차 점수 패널과 2P 분리
		splitPanel2P.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPanel2P.setDividerLocation(380);
		splitPanel2P.setDividerSize(0);
		
		splitPanel1P.setLeftComponent(gameGround1P);
		splitPanel2P.setLeftComponent(scorePanel);
		splitPanel2P.setRightComponent(gameGround2P);
		
		splitPanel1P.setRightComponent(splitPanel2P);
	}
	
	private class ControlPuyoKeyListener extends KeyAdapter {
		synchronized public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			//Player1
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
			//Player2	
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
}
