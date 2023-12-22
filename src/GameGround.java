
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameGround extends JPanel {
	JLabel testLabel1 = new JLabel("하이요");
	JLabel testLabel2 = new JLabel("헬로임");
	
	JLabel getTestLabel1() {return testLabel1;}
	JLabel getTestLabel2() {return testLabel2;}
	
	GameGround() {
		setBackground(Color.CYAN);
		setLayout(null);
		setSize(400,590);
		
		testLabel1.setSize(50, 20);
		testLabel2.setSize(50, 20);
		
		testLabel1.setBackground(Color.WHITE);
		testLabel2.setBackground(Color.WHITE);
		
		testLabel1.setLocation(100,100);
		testLabel2.setLocation(130,100);
		
		add(testLabel1);
		add(testLabel2);
	}
}
