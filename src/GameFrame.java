
import java.awt.BorderLayout;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	private GamePanel gamePanel = new GamePanel();
	
	public GameFrame() {
		setTitle("»Ñ¿ä»Ñ¿ä");
		setSize(1280, 720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().add(gamePanel, BorderLayout.CENTER);
		
		setVisible(true);
	}	
}
