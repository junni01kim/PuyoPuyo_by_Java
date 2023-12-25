import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class GamePanel extends JPanel {
	// 1P ���� ����
	private GameGround gameGround1P = new GameGround();

	// 2P ���� ����
	private GameGround gameGround2P = new GameGround();
	
	// ���� �г�
	private ScorePanel scorePanel = new ScorePanel(); 
	
	// ȭ�� ���� �г�
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
		
		// �� �÷��̾��� move() Key ���� ���ÿ� �ޱ� ���ؼ�
		setFocusable(true);
		requestFocus();
	}
	
	// ȭ���� �������ش�.
	private void splitPanel() {
		// 1�� 1P �г� �и�
		splitPanel1P.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPanel1P.setDividerLocation(450);
		splitPanel1P.setDividerSize(0);
		add(splitPanel1P);
		
		// 2�� ���� �гΰ� 2P �и�
		splitPanel2P.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPanel2P.setDividerLocation(380);
		splitPanel2P.setDividerSize(0);
		
		splitPanel1P.setLeftComponent(gameGround1P);
		splitPanel2P.setLeftComponent(scorePanel);
		splitPanel2P.setRightComponent(gameGround2P);
		
		splitPanel1P.setRightComponent(splitPanel2P);
	}
	
	//���� ������
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
		
		// puyoLogic�� �缳���ϴ� �Լ��̴�.
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
			System.out.println("ControlPuyo ������");
		}
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			//Player1
			case KeyEvent.VK_W:
				System.out.println("KeyUp");
				//�� �ѿ��� ��ġ�� ���� �ѿ並 ȸ����Ų��.
				// ���1. �¿� ��ġ - (�ѿ�1)(�ѿ�2)
				if(gameGround1P.getPuyo1().getX()<=gameGround1P.getPuyo2().getX()&&gameGround1P.getPuyo1().getY()==gameGround1P.getPuyo2().getY()) {
					// ����ó��: �ѿ䰡 �ٴڿ��� ȸ���Ͽ� ���� �հ� ������ ���
					if(gameGround1P.getPuyo1().PixelYToindex()==11
							// ����ó��: �ѿ䰡 �ٸ� �ѿ� ������ ȸ���Ͽ� �ٸ� �ѿ�� ��ġ�� ���
							||(gameGround1P.getPuyo1().PixelYToindex()<11&&roundThread.getPlayerThread1P().getPuyoMap()[gameGround1P.getPuyo1().PixelXToindex()][gameGround1P.getPuyo1().PixelYToindex()+1]!=null)) {
						gameGround1P.getPuyo1().setLocation(gameGround1P.getPuyo1().getX(), gameGround1P.getPuyo1().getY()-60);
						gameGround1P.getPuyo2().setLocation(gameGround1P.getPuyo1().getX(), gameGround1P.getPuyo1().getY()+60);
					}
					gameGround1P.getPuyo2().setLocation(gameGround1P.getPuyo1().getX(), gameGround1P.getPuyo1().getY()+60);
					break;
				}
				// ���2. �¿� ��ġ - (�ѿ�2)(�ѿ�1)
				if(gameGround1P.getPuyo1().getX()>gameGround1P.getPuyo2().getX()&&gameGround1P.getPuyo1().getY()==gameGround1P.getPuyo2().getY()) {
					gameGround1P.getPuyo2().setLocation(gameGround1P.getPuyo1().getX(), gameGround1P.getPuyo1().getY()-60);
					break;
				}
				// ���3. ���� ��ġ - (�ѿ�1)(�ѿ�2)
				if(gameGround1P.getPuyo1().getX()==gameGround1P.getPuyo2().getX()&&gameGround1P.getPuyo1().getY()<=gameGround1P.getPuyo2().getY()) {
					// ����ó��: �ѿ䰡 ������ ���� ä ȸ���Ͽ� ���� �հ� ������ ���
					if(gameGround1P.getPuyo1().PixelXToindex()==0
							// ����ó��: �ѿ䰡 �ٸ� �ѿ� ������ ȸ���Ͽ� �ٸ� �ѿ�� ��ġ�� ���
							||(gameGround1P.getPuyo1().PixelXToindex()>0&&roundThread.getPlayerThread1P().getPuyoMap()[gameGround1P.getPuyo1().PixelXToindex()-1][gameGround1P.getPuyo1().PixelYToindex()]!=null)) {
						gameGround1P.getPuyo1().setLocation(gameGround1P.getPuyo1().getX()+60, gameGround1P.getPuyo1().getY());
						gameGround1P.getPuyo2().setLocation(gameGround1P.getPuyo1().getX()-60, gameGround1P.getPuyo1().getY());
					}
					gameGround1P.getPuyo2().setLocation(gameGround1P.getPuyo1().getX()-60, gameGround1P.getPuyo1().getY());
					break;
				}
				// ���4. ���� ��ġ - (�ѿ�2)(�ѿ�1)					
				if(gameGround1P.getPuyo1().getX()==gameGround1P.getPuyo2().getX()&&gameGround1P.getPuyo1().getY()>gameGround1P.getPuyo2().getY()) {
					// ����ó��: �ѿ䰡 ������ ���� ä ȸ���Ͽ� ���� �հ� ������ ���
					if(gameGround1P.getPuyo1().PixelXToindex()==5
							// ����ó��: �ѿ䰡 �ٸ� �ѿ� ������ ȸ���Ͽ� �ٸ� �ѿ�� ��ġ�� ���
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
				//����ó��: ���ʿ� ���� �ִµ� ����Ű�� ������ ���
				if(gameGround1P.getPuyo1().PixelXToindex()<=0||gameGround1P.getPuyo2().PixelXToindex()<=0)
					break;
				//����ó��: ���ʿ� ����� �ִµ� ����Ű�� ������ ���
				if(roundThread.getPlayerThread1P().getPuyoMap()[gameGround1P.getPuyo1().PixelXToindex()-1][gameGround1P.getPuyo1().PixelYToindex()]!=null
						||roundThread.getPlayerThread1P().getPuyoMap()[gameGround1P.getPuyo2().PixelXToindex()-1][gameGround1P.getPuyo2().PixelYToindex()]!=null)
					break;
				
				gameGround1P.getPuyo1().setLocation(gameGround1P.getPuyo1().getX()-60,gameGround1P.getPuyo1().getY()+60);
				gameGround1P.getPuyo2().setLocation(gameGround1P.getPuyo2().getX()-60,gameGround1P.getPuyo2().getY()+60);
				break;
			case KeyEvent.VK_D:
				System.out.println("KeyRight");
				//����ó��: �����ʿ� ��� Ȥ�� ���� �ִµ� ����Ű�� ������ ���
				if(gameGround1P.getPuyo1().PixelXToindex()>=5||gameGround1P.getPuyo2().PixelXToindex()>=5)
					break;
				//����ó��: �����ʿ� ����� �ִµ� ����Ű�� ������ ���
				if(roundThread.getPlayerThread1P().getPuyoMap()[gameGround1P.getPuyo1().PixelXToindex()+1][gameGround1P.getPuyo1().PixelYToindex()]!=null
						||roundThread.getPlayerThread1P().getPuyoMap()[gameGround1P.getPuyo2().PixelXToindex()+1][gameGround1P.getPuyo2().PixelYToindex()]!=null)
					break;
				gameGround1P.getPuyo1().setLocation(gameGround1P.getPuyo1().getX()+60,gameGround1P.getPuyo1().getY());
				gameGround1P.getPuyo2().setLocation(gameGround1P.getPuyo2().getX()+60,gameGround1P.getPuyo2().getY());
				break;
			//Player2	
			case KeyEvent.VK_UP:
				System.out.println("KeyUp");
				//�� �ѿ��� ��ġ�� ���� �ѿ並 ȸ����Ų��.
				// ���1. �¿� ��ġ - (�ѿ�1)(�ѿ�2)
				if(gameGround2P.getPuyo1().getX()<=gameGround2P.getPuyo2().getX()&&gameGround2P.getPuyo1().getY()==gameGround2P.getPuyo2().getY()) {
					// ����ó��: �ѿ䰡 �ٴڿ��� ȸ���Ͽ� ���� �հ� ������ ���
					if(gameGround2P.getPuyo1().PixelYToindex()==11
							// ����ó��: �ѿ䰡 �ٸ� �ѿ� ������ ȸ���Ͽ� �ٸ� �ѿ�� ��ġ�� ���
							||(gameGround2P.getPuyo1().PixelYToindex()<11&&roundThread.getPlayerThread2P().getPuyoMap()[gameGround2P.getPuyo1().PixelXToindex()][gameGround2P.getPuyo1().PixelYToindex()+1]!=null)) {
						gameGround2P.getPuyo1().setLocation(gameGround2P.getPuyo1().getX(), gameGround2P.getPuyo1().getY()-60);
						gameGround2P.getPuyo2().setLocation(gameGround2P.getPuyo1().getX(), gameGround2P.getPuyo1().getY()+60);
					}
					gameGround2P.getPuyo2().setLocation(gameGround2P.getPuyo1().getX(), gameGround2P.getPuyo1().getY()+60);
					break;
				}
				// ���2. �¿� ��ġ - (�ѿ�2)(�ѿ�1)
				if(gameGround2P.getPuyo1().getX()>gameGround2P.getPuyo2().getX()&&gameGround2P.getPuyo1().getY()==gameGround2P.getPuyo2().getY()) {
					gameGround2P.getPuyo2().setLocation(gameGround2P.getPuyo1().getX(), gameGround2P.getPuyo1().getY()-60);
					break;
				}
				// ���3. ���� ��ġ - (�ѿ�1)(�ѿ�2)
				if(gameGround2P.getPuyo1().getX()==gameGround2P.getPuyo2().getX()&&gameGround2P.getPuyo1().getY()<=gameGround2P.getPuyo2().getY()) {
					// ����ó��: �ѿ䰡 ������ ���� ä ȸ���Ͽ� ���� �հ� ������ ���
					if(gameGround2P.getPuyo1().PixelXToindex()==0
							// ����ó��: �ѿ䰡 �ٸ� �ѿ� ������ ȸ���Ͽ� �ٸ� �ѿ�� ��ġ�� ���
							||(gameGround2P.getPuyo1().PixelXToindex()>0&&roundThread.getPlayerThread2P().getPuyoMap()[gameGround2P.getPuyo1().PixelXToindex()-1][gameGround2P.getPuyo1().PixelYToindex()]!=null)) {
						gameGround2P.getPuyo1().setLocation(gameGround2P.getPuyo1().getX()+60, gameGround2P.getPuyo1().getY());
						gameGround2P.getPuyo2().setLocation(gameGround2P.getPuyo1().getX()-60, gameGround2P.getPuyo1().getY());
					}
					gameGround2P.getPuyo2().setLocation(gameGround2P.getPuyo1().getX()-60, gameGround2P.getPuyo1().getY());
					break;
				}
				// ���4. ���� ��ġ - (�ѿ�2)(�ѿ�1)					
				if(gameGround2P.getPuyo1().getX()==gameGround2P.getPuyo2().getX()&&gameGround2P.getPuyo1().getY()>gameGround2P.getPuyo2().getY()) {
					// ����ó��: �ѿ䰡 ������ ���� ä ȸ���Ͽ� ���� �հ� ������ ���
					if(gameGround2P.getPuyo1().PixelXToindex()==5
							// ����ó��: �ѿ䰡 �ٸ� �ѿ� ������ ȸ���Ͽ� �ٸ� �ѿ�� ��ġ�� ���
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
				//����ó��: ���ʿ� ��� Ȥ�� ���� �ִµ� ����Ű�� ������ ���
				if(gameGround2P.getPuyo1().PixelXToindex()<=0||gameGround2P.getPuyo2().PixelXToindex()<=0)
					break;
				//����ó��: ���ʿ� ����� �ִµ� ����Ű�� ������ ���
				if(roundThread.getPlayerThread2P().getPuyoMap()[gameGround2P.getPuyo1().PixelXToindex()-1][gameGround2P.getPuyo1().PixelYToindex()]!=null
						||roundThread.getPlayerThread2P().getPuyoMap()[gameGround2P.getPuyo2().PixelXToindex()-1][gameGround2P.getPuyo2().PixelYToindex()]!=null)
					break;
				
				gameGround2P.getPuyo1().setLocation(gameGround2P.getPuyo1().getX()-60,gameGround2P.getPuyo1().getY()+60);
				gameGround2P.getPuyo2().setLocation(gameGround2P.getPuyo2().getX()-60,gameGround2P.getPuyo2().getY()+60);
				break;
			case KeyEvent.VK_RIGHT:
				System.out.println("KeyRight");
				//����ó��: �����ʿ� ��� Ȥ�� ���� �ִµ� ����Ű�� ������ ���
				if(gameGround2P.getPuyo1().PixelXToindex()>=5||gameGround2P.getPuyo2().PixelXToindex()>=5)
					break;
				//����ó��: �����ʿ� ����� �ִµ� ����Ű�� ������ ���
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
