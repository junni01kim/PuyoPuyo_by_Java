import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class GamePanel extends JPanel {
	GameGround gameGround1P = new GameGround();
	GameGround gameGround2P = new GameGround();
	ScorePanel scorePanel = new ScorePanel(); 
	
	private JSplitPane splitPanel1P = new JSplitPane();
	private JSplitPane splitPanel2P = new JSplitPane();
	
	GamePanel() {
		setBackground(Color.YELLOW);
		setLayout(new BorderLayout());
		// 1P 게임 보드
		
		// 2P 게임 보드
		
		// 다음 뿌요위치
		// 넘길 방해뿌요 수 그림
		// 남은 라운드 현재 라운드 수
		// 자신의 점수
		splitPanel();
	}
	
	private void splitPanel() {
		splitPanel1P.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPanel1P.setDividerLocation(500);
		add(splitPanel1P);
		
		splitPanel2P.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPanel1P.setDividerLocation(280);
	}
}
