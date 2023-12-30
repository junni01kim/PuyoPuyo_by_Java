import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel{
	Puyo nextLeftControlPuyo1P;
	Puyo nextRightControlPuyo1P;
	Puyo nextLeftControlPuyo2P;
	Puyo nextRightControlPuyo2P;

	private RoundThread roundThread;
	
	public Puyo getNextLeftControlPuyo1P() {return nextLeftControlPuyo1P;}
	public Puyo getNextRightControlPuyo1P() {return nextRightControlPuyo1P;}
	public Puyo getNextLeftControlPuyo2P() {return nextLeftControlPuyo2P;}
	public Puyo getNextRightControlPuyo2P() {return nextRightControlPuyo2P;}
	
	ScorePanel(GameGround gameGround1P, GameGround gameGround2P, RoundThread roundThread) {
		this.roundThread = roundThread;
		
		nextLeftControlPuyo1P = new Puyo(gameGround1P,0);
		nextRightControlPuyo1P = new Puyo(gameGround1P,0);
		nextLeftControlPuyo2P = new Puyo(gameGround2P,0);
		nextRightControlPuyo2P = new Puyo(gameGround2P,0);
		
		setBackground(Color.YELLOW);
		setSize(300, 750);
		setLayout(null);
		
		// 다음 뿌요위치
		nextLeftControlPuyo1P.setLocation(this.getWidth()/2-60,100);
		nextRightControlPuyo1P.setLocation(this.getWidth()/2,100);
		
		nextLeftControlPuyo2P.setLocation(this.getWidth()/2-60,200);
		nextRightControlPuyo2P.setLocation(this.getWidth()/2,200);
		
		nextLeftControlPuyo1P.setSize(60, 60);
		nextRightControlPuyo1P.setSize(60, 60);
		
		nextLeftControlPuyo2P.setSize(60, 60);
		nextRightControlPuyo2P.setSize(60, 60);
		
		add(nextLeftControlPuyo1P);
		add(nextRightControlPuyo1P);
		add(nextLeftControlPuyo2P);
		add(nextRightControlPuyo2P);
		
		// 넘길 방해뿌요 수 그림
		// 남은 라운드 현재 라운드 수
		// 자신의 점수
	}
}
