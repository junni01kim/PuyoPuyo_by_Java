import java.awt.Color;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel{
	Puyo nextLeftControlPuyo1P;
	Puyo nextRightControlPuyo1P;
	Puyo nextLeftControlPuyo2P;
	Puyo nextRightControlPuyo2P;

	private RoundThread roundThread;
	
	private JLabel roundCountLabel = new JLabel("1");
	private JLabel roundLabel = new JLabel("R O U N D");
	private JLabel scoreTextLabel = new JLabel("S C O R E");
	private JLabel scoreLabel1P = new JLabel("0");
	private JLabel scoreLabel2P = new JLabel("0");
	private JLabel numberOfGarbagePuyoLabel1P = new JLabel("�ӽ�");
	private JLabel numberOfGarbagePuyoLabel2P = new JLabel("�ӽ�");
	
	private JLabel timerTextLabel = new JLabel("T I M E");
	private JLabel timerLabel = new JLabel("0");
	
	public Puyo getNextLeftControlPuyo1P() {return nextLeftControlPuyo1P;}
	public Puyo getNextRightControlPuyo1P() {return nextRightControlPuyo1P;}
	public Puyo getNextLeftControlPuyo2P() {return nextLeftControlPuyo2P;}
	public Puyo getNextRightControlPuyo2P() {return nextRightControlPuyo2P;}
	public JLabel getScoreLabel1P() {return scoreLabel1P;}
	public JLabel getScoreLabel2P() {return scoreLabel2P;}
	public JLabel getNumberOfGarbagePuyoLabel1P() {return numberOfGarbagePuyoLabel1P;}
	public JLabel getNumberOfGarbagePuyoLabel2P() {return numberOfGarbagePuyoLabel2P;}
	
	ScorePanel(GameGround gameGround1P, GameGround gameGround2P, RoundThread roundThread) {
		this.roundThread = roundThread;
		
		nextLeftControlPuyo1P = new Puyo(0);
		nextRightControlPuyo1P = new Puyo(0);
		nextLeftControlPuyo2P = new Puyo(0);
		nextRightControlPuyo2P = new Puyo(0);
		
		setBackground(Color.YELLOW);
		setSize(300, 750);
		setLayout(null);
		
		// ���� �ѿ���ġ
		nextLeftControlPuyo1P.setLocation(12+this.getWidth()/4-60,100);
		nextRightControlPuyo1P.setLocation(12+this.getWidth()/4,100);
		
		nextLeftControlPuyo2P.setLocation(this.getWidth()/4*3-60-12,100);
		nextRightControlPuyo2P.setLocation(this.getWidth()/4*3-12,100);
		
		nextLeftControlPuyo1P.setSize(60, 60);
		nextRightControlPuyo1P.setSize(60, 60);
		
		nextLeftControlPuyo2P.setSize(60, 60);
		nextRightControlPuyo2P.setSize(60, 60);
		
		add(nextLeftControlPuyo1P);
		add(nextRightControlPuyo1P);
		add(nextLeftControlPuyo2P);
		add(nextRightControlPuyo2P);
		
		roundCountLabel.setFont(new Font("Serif", Font.BOLD, 27));
		roundCountLabel.setLocation(this.getWidth()/2-75, 43);
		roundCountLabel.setSize(300, 30);
		add(roundCountLabel);
		
		roundLabel.setFont(new Font("Serif", Font.BOLD, 27));
		roundLabel.setLocation(this.getWidth()/2-50, 43);
		roundLabel.setSize(300, 30);
		add(roundLabel);
		
		scoreTextLabel.setFont(new Font("Serif", Font.BOLD, 15));
		scoreTextLabel.setLocation(this.getWidth()/2-29, 175);
		scoreTextLabel.setSize(100, 30);
		add(scoreTextLabel);
		
		scoreLabel1P.setFont(new Font("Serif", Font.BOLD, 15));
		scoreLabel1P.setLocation(this.getWidth()/2, 205);
		scoreLabel1P.setSize(100, 30);
		add(scoreLabel1P);
		
		scoreLabel2P.setFont(new Font("Serif", Font.BOLD, 15));
		scoreLabel2P.setLocation(this.getWidth()/2, 235);
		scoreLabel2P.setSize(100, 30);
		add(scoreLabel2P);
		
		//Ÿ�̸�
		//timmerLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		timerTextLabel.setFont(new Font("Serif", Font.BOLD, 15));
		timerTextLabel.setLocation(this.getWidth()/2-20, 360);
		timerTextLabel.setSize(100, 30);
		add(timerTextLabel);
		
		timerLabel.setFont(new Font("Serif", Font.BOLD, 15));
		timerLabel.setLocation(this.getWidth()/2, 400);
		timerLabel.setSize(100, 30);
		add(timerLabel);
		
		numberOfGarbagePuyoLabel1P.setLocation(this.getWidth()/2-75, 600);
		numberOfGarbagePuyoLabel1P.setSize(100,30);
		add(numberOfGarbagePuyoLabel1P);
		
		numberOfGarbagePuyoLabel2P.setLocation(this.getWidth()/2+50, 600);
		numberOfGarbagePuyoLabel2P.setSize(100,30);
		add(numberOfGarbagePuyoLabel2P);
		
		// �ѱ� ���ػѿ� �� �׸�
		// ���� ���� ���� ���� ��
		// �ڽ��� ����
	}
}
