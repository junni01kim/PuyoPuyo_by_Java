
import java.awt.BorderLayout;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	private GamePanel gamePanel = null;
	private GameMenuPanel gameMenuPanel = null;
	
	public void openGamePanel() {
		gamePanel = new GamePanel(this);
		getContentPane().add(gamePanel, BorderLayout.CENTER);
		gamePanel.setVisible(true);
		gamePanel.requestFocus();
	}
	
	public void closeGamePanel() {
		gamePanel.setVisible(true);
	}
	
	public void openGameMenuPanel() {
		gameMenuPanel = new GameMenuPanel(this);
		getContentPane().add(gameMenuPanel, BorderLayout.CENTER);
		gameMenuPanel.setVisible(true);
	}
	
	public void closeGameMenuPanel() {
		getContentPane().remove(gameMenuPanel);
	}
	
	public GameFrame() {
		setTitle("»Ñ¿ä»Ñ¿ä");
		setSize(1280, 870);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		openGameMenuPanel();
		
		setVisible(true);
	}	
}
