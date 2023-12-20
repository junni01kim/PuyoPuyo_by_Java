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
		// 1P ���� ����
		
		// 2P ���� ����
		
		// ���� �ѿ���ġ
		// �ѱ� ���ػѿ� �� �׸�
		// ���� ���� ���� ���� ��
		// �ڽ��� ����
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
