import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class GamePanel extends JPanel {
	// 1P 게임 보드
	private GameGround gameGround1P = new GameGround();

	// 2P 게임 보드
	private GameGround gameGround2P = new GameGround();
	
	// 점수 패널
	private ScorePanel scorePanel = new ScorePanel(); 
	
	// 화면 분할 패널
	private JSplitPane splitPanel1P = new JSplitPane();
	private JSplitPane splitPanel2P = new JSplitPane();
	
	GamePanel() {
		setBackground(Color.RED);
		//setLayout(new BorderLayout());
		setLayout(null);
		add(gameGround1P);
		gameGround1P.setLocation(50,60);
		
		add(gameGround2P);
		gameGround2P.setLocation(830,60);
		
		add(scorePanel);
		scorePanel.setLocation(490,60);
		
		
		//splitPanel();
	}
	
	// 화면을 분할해준다.
	private void splitPanel() {
		// 1차 1P 패널 분리
		splitPanel1P.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPanel1P.setDividerLocation(450);
		splitPanel1P.setDividerSize(0);
		add(splitPanel1P);
		
		// 2차 점수 패널과 2P 분리
		splitPanel2P.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPanel2P.setDividerLocation(380);
		splitPanel2P.setDividerSize(0);
		
		splitPanel1P.setLeftComponent(gameGround1P);
		splitPanel2P.setLeftComponent(scorePanel);
		splitPanel2P.setRightComponent(gameGround2P);
		
		splitPanel1P.setRightComponent(splitPanel2P);
	}
}
