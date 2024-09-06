import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GameMenuPanel extends JPanel{
	private GameFrame gameFrame;
	
	private ImageIcon gameMenuPanelIcon = new ImageIcon("res/GameMenuPanel.jpg");
	
	private ImageIcon startGameButtonImage = new ImageIcon("res/StartGameButton.png");
	private JButton startGameButton = new JButton(startGameButtonImage);
	
	private JButton explainGameButton = new JButton("res/ExplainGame");
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(gameMenuPanelIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
	public GameMenuPanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		setBackground(Color.ORANGE);
		setLayout(null);
		
		startGameButton.addActionListener(new StartGameButtonActionListener());
		startGameButton.setLocation(550, 540);
		startGameButton.setSize(150, 60);
		add(startGameButton);
		
		explainGameButton.addActionListener(null);
		explainGameButton.setLocation(550, 620);
		explainGameButton.setSize(150, 60);
		add(explainGameButton);
	}
	
	private class StartGameButtonActionListener implements ActionListener {
		JButton myButton;
		
		public void actionPerformed(ActionEvent e) {
			myButton = (JButton)e.getSource();
			myButton.getParent().setVisible(false);
			gameFrame.openGamePanel();
		}
	}
}
