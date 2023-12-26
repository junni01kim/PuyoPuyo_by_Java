
import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameGround extends JPanel {
	private ImageIcon puyoIcon[] = {new ImageIcon("GreenPuyo.png"),new ImageIcon("RedPuyo.png"),new ImageIcon("YellowPuyo.png"),new ImageIcon("BluePuyo.png"),new ImageIcon("PurplePuyo.png")};
	
	Puyo puyo1 = new Puyo(this, 0, 140, 10);
	Puyo puyo2 = new Puyo(this, 0, 200, 10);
	
	ImageIcon[] getPuyoIcon() {return puyoIcon;}
	Puyo getPuyo1() {return puyo1;}
	Puyo getPuyo2() {return puyo2;}
	
	GameGround() {
		setBackground(Color.CYAN);
		setLayout(null);
		setSize(400,750);
		
		add(puyo1);
		add(puyo2);
	}
}