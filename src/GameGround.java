
import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameGround extends JPanel {
	private ImageIcon puyoIcon[] = {new ImageIcon("GreenPuyo.png"),new ImageIcon("RedPuyo.png"),new ImageIcon("YellowPuyo.png"),new ImageIcon("BluePuyo.png"),new ImageIcon("PurplePuyo.png"), new ImageIcon("GarbagePuyo.png")};
	
	GamePanel gamePanel;
	
	Puyo leftControlPuyo = new Puyo(this, 0);
	Puyo rightControlPuyo = new Puyo(this, 0);
	
	ImageIcon[] getPuyoIcon() {return puyoIcon;}
	public GamePanel getGamePanel() {return gamePanel;}
	Puyo getPuyo1() {return leftControlPuyo;}
	Puyo getPuyo2() {return rightControlPuyo;}
	
	GameGround(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		setBackground(Color.CYAN);
		setLayout(null);
		setSize(400,750);
		
		add(leftControlPuyo);
		add(rightControlPuyo);
	}
}