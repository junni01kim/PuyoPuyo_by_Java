import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class GamePanel extends JPanel {
	// 1P 게임 보드
	private GameGround gameGround1P = new GameGround();

	// 2P 게임 보드
	private GameGround gameGround2P = new GameGround();
	
	// 점수 패널
	private ScorePanel scorePanel = new ScorePanel(); 
	
	// 화면 분할 패널
	private JSplitPane splitPanel1P = new JSplitPane();
	private JSplitPane splitPanel2P = new JSplitPane();
	
	private RoundThread roundThread = new RoundThread(this);
	
	public GamePanel() {
		setBackground(Color.RED);
		addKeyListener(new ControlPuyo());
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
		requestFocus();
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
	
	//라운드 스레드
	public class RoundThread extends Thread {
		GamePanel gamePanel;
		int puyoLogic[] = new int[25];
		PlayerThread playerThread1P;
		PlayerThread playerThread2P;
		
		public PlayerThread getPlayerThread1P() {return playerThread1P;}
		public PlayerThread getPlayerThread2P() {return playerThread2P;}
		
		public RoundThread(GamePanel gamePanel) {
			this.gamePanel = gamePanel;
			makePuyoLogic();
			playerThread1P = new PlayerThread(gameGround1P, puyoLogic);
			playerThread2P = new PlayerThread(gameGround2P, puyoLogic);
			System.out.println("gamePanel");
		}
		
		// puyoLogic을 재설정하는 함수이다.
		private void makePuyoLogic() {
			int puyoCase[][] = new int[5][5];
			int firstPuyo;
			int secondPuyo;
			for (int i = 0; i < 25; i++)
			{
				while (true) {
					firstPuyo = (int)(Math.random()*5);
					secondPuyo = (int)(Math.random()*5);
					if (puyoCase[firstPuyo][secondPuyo] == 0) {
						puyoCase[firstPuyo][secondPuyo] = 1;
						puyoLogic[i] = firstPuyo * 10 + secondPuyo;
						break;
					}
				}
			}
			System.out.println("makePuyoLogic");
		}
		
		@Override
		public void run() {
			playerThread1P.start();
			playerThread2P.start();
			System.out.println("gamePanel.run");
		}
	}
	
	private class ControlPuyo extends KeyAdapter {
		public ControlPuyo() {
			System.out.println("ControlPuyo 생성자");
		}
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			//Player1
			case KeyEvent.VK_W:
				System.out.println("KeyUp");
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
						gameGround1P.getPuyo1().setLocation(gameGround1P.getPuyo1().getX()-60, gameGround1P.getPuyo1().getY());
						gameGround1P.getPuyo2().setLocation(gameGround1P.getPuyo1().getX()+60, gameGround1P.getPuyo1().getY());
					}
					gameGround1P.getPuyo2().setLocation(gameGround1P.getPuyo1().getX()+60, gameGround1P.getPuyo1().getY());
					break;
				}
				break;
			case KeyEvent.VK_S:
				System.out.println("KeyDown");
				roundThread.getPlayerThread1P().dropPuyo();
				break;
			case KeyEvent.VK_A:
				System.out.println("KeyLeft");
				//예외처리: 왼쪽에 벽이 있는데 좌측키를 누르는 경우
				if(gameGround1P.getPuyo1().PixelXToindex()<=0||gameGround1P.getPuyo2().PixelXToindex()<=0)
					break;
				//예외처리: 왼쪽에 블록이 있는데 좌측키를 누르는 경우
				if(roundThread.getPlayerThread1P().getPuyoMap()[gameGround1P.getPuyo1().PixelXToindex()-1][gameGround1P.getPuyo1().PixelYToindex()]!=null
						||roundThread.getPlayerThread1P().getPuyoMap()[gameGround1P.getPuyo2().PixelXToindex()-1][gameGround1P.getPuyo2().PixelYToindex()]!=null)
					break;
				
				gameGround1P.getPuyo1().setLocation(gameGround1P.getPuyo1().getX()-60,gameGround1P.getPuyo1().getY()+60);
				gameGround1P.getPuyo2().setLocation(gameGround1P.getPuyo2().getX()-60,gameGround1P.getPuyo2().getY()+60);
				break;
			case KeyEvent.VK_D:
				System.out.println("KeyRight");
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
				System.out.println("KeyUp");
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
						gameGround2P.getPuyo1().setLocation(gameGround2P.getPuyo1().getX()-60, gameGround2P.getPuyo1().getY());
						gameGround2P.getPuyo2().setLocation(gameGround2P.getPuyo1().getX()+60, gameGround2P.getPuyo1().getY());
					}
					gameGround2P.getPuyo2().setLocation(gameGround2P.getPuyo1().getX()+60, gameGround2P.getPuyo1().getY());
					break;
				}
				break;
			case KeyEvent.VK_DOWN:
				System.out.println("KeyDown");
				roundThread.getPlayerThread2P().dropPuyo();
				break;
			case KeyEvent.VK_LEFT:
				System.out.println("KeyLeft");
				//예외처리: 왼쪽에 블록 혹은 벽이 있는데 좌측키를 누르는 경우
				if(gameGround2P.getPuyo1().PixelXToindex()<=0||gameGround2P.getPuyo2().PixelXToindex()<=0)
					break;
				//예외처리: 왼쪽에 블록이 있는데 좌측키를 누르는 경우
				if(roundThread.getPlayerThread2P().getPuyoMap()[gameGround2P.getPuyo1().PixelXToindex()-1][gameGround2P.getPuyo1().PixelYToindex()]!=null
						||roundThread.getPlayerThread2P().getPuyoMap()[gameGround2P.getPuyo2().PixelXToindex()-1][gameGround2P.getPuyo2().PixelYToindex()]!=null)
					break;
				
				gameGround2P.getPuyo1().setLocation(gameGround2P.getPuyo1().getX()-60,gameGround2P.getPuyo1().getY()+60);
				gameGround2P.getPuyo2().setLocation(gameGround2P.getPuyo2().getX()-60,gameGround2P.getPuyo2().getY()+60);
				break;
			case KeyEvent.VK_RIGHT:
				System.out.println("KeyRight");
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
