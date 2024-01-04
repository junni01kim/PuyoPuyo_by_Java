
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class GamePanel extends JPanel {
	// 1P ���� ����
	private GameGround gameGround1P = new GameGround(this);

	// 2P ���� ����
	private GameGround gameGround2P = new GameGround(this);
	
	
	// ���� ���� ���� ������
	private RoundThread roundThread = null;
	
	// ���� �г�
	private ScorePanel scorePanel = new ScorePanel(gameGround1P, gameGround2P, roundThread);
	
	// ȭ�� ���� �г�
	private JSplitPane splitPanel1P = new JSplitPane();
	private JSplitPane splitPanel2P = new JSplitPane();
	
	public ScorePanel getScorePanel() {return scorePanel;}
	public GameGround getGameGround1P() {return gameGround1P;}
	public GameGround getGameGround2P() {return gameGround2P;}
	public RoundThread getRoundThread() {return roundThread;}
	
	public GamePanel() {
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
	
	private class ControlPuyoKeyListener extends KeyAdapter {
		public ControlPuyoKeyListener() {
			System.out.println("ControlPuyo ������");
		}
		synchronized public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			//Player1
			case KeyEvent.VK_W:
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
						// ����ó��: ȸ���Ϸ��� ��ҿ� �̹� �ѿ䰡 ���� �ִ� ���
						if(roundThread.getPlayerThread1P().getPuyoMap()[gameGround1P.getPuyo1().PixelXToindex()+1][gameGround1P.getPuyo1().PixelYToindex()]!=null)
							break;
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
						// ����ó��: ȸ���Ϸ��� ��ҿ� �̹� �ѿ䰡 ���� �ִ� ���
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
				//����ó��: ���ʿ� ���� �ִµ� ����Ű�� ������ ���
				if(gameGround1P.getPuyo1().PixelXToindex()<=0||gameGround1P.getPuyo2().PixelXToindex()<=0)
					break;
				//����ó��: ���ʿ� ����� �ִµ� ����Ű�� ������ ���
				if(roundThread.getPlayerThread1P().getPuyoMap()[gameGround1P.getPuyo1().PixelXToindex()-1][gameGround1P.getPuyo1().PixelYToindex()]!=null
						||roundThread.getPlayerThread1P().getPuyoMap()[gameGround1P.getPuyo2().PixelXToindex()-1][gameGround1P.getPuyo2().PixelYToindex()]!=null)
					break;
				
				gameGround1P.getPuyo1().setLocation(gameGround1P.getPuyo1().getX()-60,gameGround1P.getPuyo1().getY());
				gameGround1P.getPuyo2().setLocation(gameGround1P.getPuyo2().getX()-60,gameGround1P.getPuyo2().getY());
				break;
			case KeyEvent.VK_D:
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
						// ����ó��: ȸ���Ϸ��� ��ҿ� �̹� �ѿ䰡 ���� �ִ� ���
						if(roundThread.getPlayerThread2P().getPuyoMap()[gameGround2P.getPuyo1().PixelXToindex()+1][gameGround2P.getPuyo1().PixelYToindex()]!=null)
							break;
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
						// ����ó��: ȸ���Ϸ��� ��ҿ� �̹� �ѿ䰡 ���� �ִ� ���
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
				//����ó��: ���ʿ� ��� Ȥ�� ���� �ִµ� ����Ű�� ������ ���
				if(gameGround2P.getPuyo1().PixelXToindex()<=0||gameGround2P.getPuyo2().PixelXToindex()<=0)
					break;
				//����ó��: ���ʿ� ����� �ִµ� ����Ű�� ������ ���
				if(roundThread.getPlayerThread2P().getPuyoMap()[gameGround2P.getPuyo1().PixelXToindex()-1][gameGround2P.getPuyo1().PixelYToindex()]!=null
						||roundThread.getPlayerThread2P().getPuyoMap()[gameGround2P.getPuyo2().PixelXToindex()-1][gameGround2P.getPuyo2().PixelYToindex()]!=null)
					break;
				
				gameGround2P.getPuyo1().setLocation(gameGround2P.getPuyo1().getX()-60,gameGround2P.getPuyo1().getY());
				gameGround2P.getPuyo2().setLocation(gameGround2P.getPuyo2().getX()-60,gameGround2P.getPuyo2().getY());
				break;
			case KeyEvent.VK_RIGHT:
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
