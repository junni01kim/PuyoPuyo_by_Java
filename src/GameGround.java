
import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameGround extends JPanel {
	
	GamePanel gamePanel;
	
	Puyo leftControlPuyo = new Puyo(0);
	Puyo rightControlPuyo = new Puyo(0);
	
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